
from __future__ import absolute_import

import datetime, time, copy, json, struct, random

from django.db import models
from django.core.cache import cache
from django.conf import settings
from base64 import b64encode, b64decode

from a.prettyprint import ppJsonDumps

from util.random import randomToken

from dotmuncher.dm_util import invertedDict


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


TYPENAME_TYPENUM_MAP = {
    'POSITION_EVENT': 1,
    'OHHAI_EVENT': 2,
    'POWER_PELLET_EVENT': 3,
    'COLLISION_EVENT': 4,
    'DOT_EATEN_EVENT': 5,
}
TYPENUM_TYPENAM_MAP = invertedDict(TYPENAME_TYPENUM_MAP)


class Phone(models.Model):
    
    class Meta:
        db_table = TABLE_PREFIX + 'phone'
    
    token = models.CharField(max_length=50, unique=True)
    name_utf8 = models.CharField(max_length=100)
    # utf8_64
    
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
        info = copy.deepcopy(BLANK_MAP_INFO)
        info.update(json.loads(self.infoJson) if self.infoJson else {})
        return info
    
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
    def info(self):
        return json.loads(self.infoJson) if self.infoJson else {}


class Event(models.Model):
    
    class Meta:
        db_table = TABLE_PREFIX + 'event'
    
    gameId = models.IntegerField(default=-1)
    eventType = models.IntegerField()
    eventJson = models.CharField(max_length=1000)
    
    @property
    def name(self):
        return TYPENUM_TYPENAM_MAP[self.eventType]


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


