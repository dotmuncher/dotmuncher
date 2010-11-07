
import json, struct, time

from django.http import HttpResponseRedirect

import keyjson

from a_app.decorators import jsonView
from a.py import exceptionStr

from dotmuncher.models import *
from dotmuncher.constants import *
from dotmuncher.dm_util import coordScore

# 40008000 / 360 = 111133.333 m / lng deg
# (6 / 111133.333) = 0.000054

COLLISION_COORD_PLUSORMINUS = 0.000054


def logRequest(callName):
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
                x = f(r)
                duration = time.time() - t1
                ms = int(duration * 1000)
            except Exception:
                info['exception'] = exceptionStr()
                APIRequest.log(info)
                raise
            
            info['ms'] = ms
            
            if isinstance(x, dict):
                info['responseInfo'] = x
            
            APIRequest.log(info)
            
            return x
            
        return f2
    return outer
    
    
    callName = models.CharField(max_length=100, null=True)
    callJson = models.CharField(max_length=15000, null=True)
    exception = models.TextField(null=True)
    responseJson = models.TextField(null=True)



@jsonView()
@logRequest('find_games')
def api_demo_magic(r):
    info = json.loads(r.REQUEST['json'])
    
    if info.get('reset'):
        redisConn.delete('demomagic_gameId')
        return {}
    
    else:
        v = redisConn.get('demomagic_gameId')
        if v:
            return {"join": int(v)}
        else:
            return {"join": 0, "map": int(redisConn.get('demomagic_mapId'))}


@jsonView()
@logRequest('find_games')
def api_find_games(r):
    info = json.loads(r.REQUEST['json'])
    
    lat = info['lat']
    lng = info['lng']
    phoneToken = info['phoneToken']
    
    phone = Phone.forToken(phoneToken)
    
    items = []
    for game in (Game.objects
                        .order_by('-id'))[:25]:
        items.append({
            'id': game.id,
        })
    
    return {
        'phoneId': phone.id,
        'items': items,
    }


@jsonView()
@logRequest('find_maps')
def api_find_maps(r):
    info = json.loads(r.REQUEST['json'])
    
    lat = info['lat']
    lng = info['lng']
    phoneToken = info['phoneToken']
    
    phone = Phone.forToken(phoneToken)
    
    items = []
    for game in (Map.objects
                        .order_by('-id'))[:25]:
        items.append({
            'id': game.id,
        })
    
    return {
        'phoneId': phone.id,
        'items': items,
    }


@jsonView()
@logRequest('new_game')
def api_new_game(r):
    
    info = json.loads(r.REQUEST['json'])
    
    map = Map.objects.get(id=info['map'])
    game = Game.create(map)
    
    redisConn.set('demomagic_gameId', str(game.id))
    
    mapInfo = map.info
    
    for prefix, lls in (
                    ('d', mapInfo['dotPoints']),
                    ('p', mapInfo['powerPelletPoints'])):
        for ll in lls:
            lat, lng = ll
            redisConn.zadd(
                            'gp-lat:%d' % game.id,
                            coordScore(lat),
                            prefix + json.dumps(ll))
            redisConn.zadd(
                            'gp-lng:%d' % game.id,
                            coordScore(lng),
                            prefix + json.dumps(ll))
    
    if info.get('redirect'):
        return HttpResponseRedirect('/watch-game/?id=' + game.token)#DRY
    
    return {
        'game': game.id,
        'gameToken': game.token,
        'mapInfo': mapInfo,
    }


@jsonView()
@logRequest('new_game')
def api_join_game(r):
    
    info = json.loads(r.REQUEST['json'])
    
    game = Game.objects.get(id=info['game'])
    
    return {
        'game': game.id,
        'gameToken': game.token,
        'mapInfo': game.map.info,
    }


@jsonView()
@logRequest('submit_and_get_events')
def api_submit_and_get_events(r):
    
    info = json.loads(r.REQUEST['json'])
    i__gte = int(info['i__gte'])
    gameId = int(info['game'])
    
    # See if power mode has expired
    _handlePossiblePowerModeExpiration(gameId)
    
    # Save events
    for eventType, eventInfo in info['events']:
        
        gameId = int(eventInfo['game'])
        phoneId = int(eventInfo['phone'])
        
        e = Event(
            gameId=gameId,
            eventType=eventType,
            eventJson=json.dumps(eventInfo))
        e.save()
        
        
        if eventType == TYPENAME_TYPENUM_MAP['OHHAI_EVENT']:
            # The first kitteh to say hai can be teh protagonist
            redisConn.setnx('g-protagonistPhone:%d' % gameId, str(phoneId))
        
        elif eventType == TYPENAME_TYPENUM_MAP['POSITION_EVENT']:
            
            latKey = 'gp-lat:%d' % eventInfo['game']
            lngKey = 'gp-lng:%d' % eventInfo['game']
            
            # Any collisions?
            
            latMatches = set(redisConn.zrangebyscore(
                        latKey,
                        coordScore(float(eventInfo['lat']) - COLLISION_COORD_PLUSORMINUS),
                        coordScore(float(eventInfo['lat']) + COLLISION_COORD_PLUSORMINUS)))
            
            lngMatches = set(redisConn.zrangebyscore(
                        lngKey,
                        coordScore(float(eventInfo['lng']) - COLLISION_COORD_PLUSORMINUS),
                        coordScore(float(eventInfo['lng']) + COLLISION_COORD_PLUSORMINUS)))
            
            matches = latMatches & lngMatches
            
            if len(matches) > 0:
                v = redisConn.get('g-protagonistPhone:%d' % gameId)
                # Has an OHHAI_EVENT been processed yet?
                if v:
                    protagonistPhone = int(v)
                    for member in matches:
                        f = {
                            'u': _handleCollisionWithPhone,
                            'd': _handleCollisionWithDot,
                            'p': _handleCollisionWithPowerPellet,
                        }.get(member[0])
                        if f:
                            f(gameId, phoneId, member[1:])
            
            # Update this phone's position
            redisConn.zadd(
                            latKey,
                            coordScore(eventInfo['lat']),
                            'u' + eventInfo['phone'])
            redisConn.zadd(
                            lngKey,
                            coordScore(eventInfo['lng']),
                            prefix + eventInfo['phone'])
    
    #### Get events
    
    events = []
    min_i = -1
    max_i = -1
    
    ids = []
    
    for e in (Event.objects
                        .filter(
                            gameId=int(info['game']),
                            id__gte=i__gte)):
        events.append([
            e.eventType,
            json.loads(e.eventJson),
        ])
        ids.append(e.id)
    
    #TODO: remove extra position_events
    
    return {
        'events': events,
        'min_i': min(ids) if len(ids) > 0 else -1,
        'max_i': max(ids) if len(ids) > 0 else -1,
    }


@jsonView()
@logRequest('map_create')
def api_map_create(r):
    
    map = Map.create()
    
    redisConn.set('demomagic_mapId', str(map.id))
    
    return {
        'token': map.token,
    }


@jsonView()
@logRequest('map_add_points')
def api_map_add_points(r):
    
    newInfo = json.loads(r.REQUEST['json'])
    
    # Validate points:
    for k in newInfo.keys():
        if k.endswith('Points'):
            for ll in newInfo[k]:
                assert isinstance(ll, basestring)
                (lat, lng) = ll.split(',')
                float(lat)
                float(lng)
    
    map = Map.objects.get(token=newInfo['token'])
    
    mapInfo = map.info
    
    for k in newInfo.keys():
        if k.endswith('Points'):
            mapInfo[k] += [ll.split(',') for ll in newInfo[k]]
    map.infoJson = json.dumps(mapInfo)
    
    if newInfo.get('done'):
        map.completed = True
    
    map.save()
    
    return {
        'token': map.token,
    }




@jsonView()
@logRequest('debug')
def api_debug(r):
    
    if 'raise' in r.REQUEST:
        raise Exception
    
    return {
        'method': r.method,
        'POST': r.POST,
        'GET': r.GET,
    }


@jsonView()
@logRequest('temp')
def api_temp(r):
    
    points = []
    
    for e in Event.objects.all():
        if e.eventType == 1:
            info = json.loads(e.eventJson)
            points.append([
                info['lat'].strip(),
                info['lng'].strip(),
            ])
    
    return {
        'points': points,
    }




def _handleCollisionWithPhone(gameId, phoneId, data, protagonistPhone):
    phoneIdOfMatch = int(data)
    # Is one of the phones the protagonist?
    if protagonistPhone in set([phoneId, phoneIdOfMatch]):
        
        nonProtagonistPhone = (
                                    phoneId
                                    if phoneId != protagonistPhone else
                                    phoneIdOfMatch)
        
        v = redisConn.get('g-powerModeUntil:%d' % gameId)
        if v:
            powerMode = int(time.time() * 1000) < int(v)
        else:
            powerMode = False
        
        if powerMode:
            eater, eatee = protagonistPhone, nonProtagonistPhone
        else:
            eater, eatee = nonProtagonistPhone, protagonistPhone
        
        e = Event(
                gameId=gameId,
                eventType=TYPENAME_TYPENUM_MAP['COLLISION_EVENT'],
                eventJson=json.dumps({
                    'eater': eater,
                    'eatee': eatee,
                }))
        e.save()


def _handleCollisionWithDot(gameId, phoneId, data, protagonistPhone):
    if phoneId == protagonistPhone:
        e = Event(
                gameId=gameId,
                eventType=TYPENAME_TYPENUM_MAP['POWER_PELLET_EVENT'],
                eventJson=json.dumps({
                    'point': json.loads(data),
                }))
        e.save()


def _handleCollisionWithPowerPellet(gameId, phoneId, data, protagonistPhone):
    if phoneId == protagonistPhone:
        
        until = int(time.time() * 1000) + POWER_MODE_DURATION_MS
        redisConn.set('g-powerModeUntil:%d' % game.id, str(until))
        
        e = Event(
                gameId=gameId,
                eventType=TYPENAME_TYPENUM_MAP['COLLISION_EVENT'],
                eventJson=json.dumps({
                    'active': True,
                }))
        e.save()


def _handlePossiblePowerModeExpiration(gameId):
    v = redisConn.get('g-powerModeUntil:%d' % gameId)
    if v:
        if int(time.time() * 1000) >= int(v):
            e = Event(
                    gameId=gameId,
                    eventType=TYPENAME_TYPENUM_MAP['COLLISION_EVENT'],
                    eventJson=json.dumps({
                        'active': False,
                    }))
            e.save()


