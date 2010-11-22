
from __future__ import absolute_import

import datetime, time, copy, json, struct, random
from base64 import b64encode, b64decode

from django.db import models
from django.core.cache import cache
from django.conf import settings
from django.http import HttpResponseRedirect

from dotmuncher.py.constants import *
from dotmuncher.py.dm_util import exceptionStr, jsonView, jsonReponse
from dotmuncher.py.dm_util import invertedDict, ppJsonDumps, randomToken
from dotmuncher.py.stubdata import STUB_MAP_INFO


redisConn = None
try:
    import redis
    redisConn = redis.Redis(
                        host=settings.REDIS_HOST,
                        port=settings.REDIS_PORT,
                        db=6)
except Exception:
    pass



TABLE_PREFIX = 'dotmuncher_'

POSITION_EVENT = 1
OHHAI_EVENT = 2
PHONE_EATEN_EVENT = 6
ITEM_EATEN_EVENT = 7
GAME_OVER = 8

TYPENUM_TYPENAM_MAP = {
    POSITION_EVENT: 'POSITION_EVENT',
    OHHAI_EVENT: 'OHHAI_EVENT',
}


class Phone(models.Model):
    
    class Meta:
        db_table = TABLE_PREFIX + 'phone'
    
    token = models.CharField(max_length=50, unique=True)
    
    # utf8_64
    name_utf8 = models.CharField(max_length=100)
    
    @classmethod
    def forToken(cls, token):
        
        try:
            m = cls.objects.get(token=token)
        except cls.DoesNotExist:
            m = cls(
                    token=token,
                    name_utf8=b64encode(u''.encode('utf-8')))
            m.save()
        
        return m
    
    @property
    def name(self):
        return unicode(b64decode(self.name_utf8), 'utf-8')
    
    def setName(self, name):
        self.name_utf8 = b64encode(unicode(name).encode('utf-8'))
        self.save()


BLANK_MAP_INFO = {
    "pathPoints": [],
    "basePoints": [],
    "dotPoints": [],
    "powerPelletPoints": [],
}


class Map(models.Model):
    
    class Meta:
        db_table = TABLE_PREFIX + 'map'
    
    token = models.CharField(max_length=50, unique=True)
    createdAtUtc = models.DateTimeField()
    
    deleted = models.BooleanField(default=False)
    completed = models.BooleanField(default=False)
    
    lat = models.FloatField(null=True)
    lng = models.FloatField(null=True)
    
    infoJson = models.TextField(null=True)
    
    @property
    def info(self):
        return STUB_MAP_INFO
        #info = copy.deepcopy(BLANK_MAP_INFO)
        #info.update(json.loads(self.infoJson) if self.infoJson else {})
        #return info
    
    @classmethod
    def create(cls):
        m = cls(
                token=randomToken(8),
                createdAtUtc=datetime.datetime.utcnow(),
                infoJson=json.dumps(BLANK_MAP_INFO))
        m.save()
        return m


class Game(models.Model):
    
    class Meta:
        db_table = TABLE_PREFIX + 'game'
    
    token = models.CharField(max_length=50, unique=True)
    createdAtUtc = models.DateTimeField()
    
    map = models.ForeignKey(Map)
    
    infoJson = models.TextField(null=True)
    
    @classmethod
    def create(cls, map):
        m = cls(
                token=randomToken(8),
                createdAtUtc=datetime.datetime.utcnow(),
                map=map)
        m.save()
        return m
    
    @property
    def numEvents(self):
        return int(redisConn.get('g_numEvents:%d' % self.id) or 0)
    
    @property
    def info(self):
        return json.loads(self.infoJson) if self.infoJson else {}


class Event:
    
    def __init__(self, gameId, eventInfo):
        self.gameId = gameId
        self.eventInfo = eventInfo
    
    @property
    def name(self):
        return TYPENUM_TYPENAM_MAP.get(self.eventInfo.get('type', '?'), '?')
    
    @property
    def eventJson(self):
        return json.dumps(self.eventInfo)
    
    @classmethod
    def appendEvent(cls, gameId, eventInfo):
        redisConn.incr('numEvents', 1)
        i = redisConn.incr('g_numEvents:%d' % gameId, 1)
        eventInfo['t'] = int(time.time() * 1000)
        eventInfo['i'] = i
        redisConn.set(
            'g_i_event:%d:%d' % (gameId, i),
            json.dumps(eventInfo))
    
    @classmethod
    def getEvents(cls, gameId, id__gte):
        numEvents = int(redisConn.get('g_numEvents:%d' % gameId) or 0)
        for i in range(id__gte, numEvents + 1):
            j = redisConn.get('g_i_event:%d:%d' % (gameId, i))
            if j:
                eventInfo = json.loads(j)
                yield cls(gameId, eventInfo)


# for debugging
class APIRequest(models.Model):
    
    class Meta:
        db_table = TABLE_PREFIX + 'apirequest'
    
    createdAtUtc = models.DateTimeField()
    phoneId = models.IntegerField()
    infoJson = models.TextField()
    
    @classmethod
    def log(cls, info):
        m = cls(
                createdAtUtc=datetime.datetime.utcnow(),
                phoneId=int(
                            info.get('phone', -1)),
                infoJson=json.dumps(info))
        m.save()
        return m
    
    @property
    def datecode(self):
        return self.createdAtUtc.strftime('%Y-%m-%D %H:%M:%S UTC')
    
    @property
    def prettyJsonHtml(self):
        info = json.loads(self.infoJson or '{}')
        return ppJsonDumps(info).replace('\n', '<br>')



def apiRequest(callName):
    def outer(f):
        def f2(r):
            
            callJson = r.REQUEST.get('json', '')
            
            info = {
                'callName': callName,
                'callJson': callJson,
            }
            
            if 'phone' in r.REQUEST:
                try:
                    info['phone'] = int(r.REQUEST['phone'])
                except Exception:
                    pass
            
            try:
                t1 = time.time()
                info = json.loads(callJson)
                x = f(r, info)
                duration = time.time() - t1
                ms = int(duration * 1000)
            except Exception:
                info['exception'] = exceptionStr()
                APIRequest.log(info)
                raise
            
            info['ms'] = ms
            
            if isinstance(x, dict):
                info['responseInfo'] = x
            
            #APIRequest.log(info)
            
            return jsonReponse(r, x)
            
        return f2
    return outer


def loadGameInfoAnd(game, extraKeys):
    
    now = int(time.time() * 1000)
    
    # Woot, only two blocking redis requests! #prematureoptimization
    
    phones = [int(s) for s in redisConn.lrange('g_phones:%d' % game, 0, -1)]
    
    keys = (
                [
                    'g_p_pos:%d:%d' % (game, p)
                    for p in phones] +
                ['g_deadPlayers:%d' % (game)] +
                ['g_powerModeUntil:%d' % game] + 
                extraKeys)
    values = redisConn.mget(keys)
    
    deadPlayersValue = values[-(2 + len(extraKeys))]
    powerModeValue = values[-(1 + len(extraKeys))]
    extraValues = values[-len(extraKeys):]
    
    # Dead players
    if deadPlayersValue:
        deadPlayers = json.loads(deadPlayersValue)
    else:
        deadPlayers = {}
    
    # Power mode
    if powerModeValue:
        powerMode = int(time.time() * 1000) < int(powerModeValue)
    else:
        powerMode = False
    
    # States
    states = []
    for i in range(len(phones)):
        phone = phones[i]
        state = {
            'phone': phone,
        }
        v = values[i]
        if v:
            info = json.loads(v)
            state['idle'] = now - info['t']
            state['lat'] = info['lat']
            state['lng'] = info['lng']
            state['alive'] = bool(str(phone) not in deadPlayers)
        states.append(state)
    
    return [
        {
            'phoneStates': states,
            'powerMode': powerMode,
            'deadPlayers': deadPlayers,
        },
        extraValues]


def loadGameInfo(game):
    info, extraValues = loadGameInfoAnd(game, [])
    return info


