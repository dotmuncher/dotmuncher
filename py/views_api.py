
import json, struct, time

from django.http import HttpResponseRedirect

from dotmuncher.py.models import *
from dotmuncher.py.constants import *
from dotmuncher.py.dm_util import exceptionStr, jsonView, jsonReponse

#TODO: un-mock mapInfo

#### update

@apiRequest('update')
def api_update(r, info):
    
    now = int(time.time() * 1000)
    
    # Kinda validate the input
    lat = str(info['lat'])
    lng = str(info['lng'])
    hacc = float(info['hacc'])
    vacc = float(info['vacc'])
    game = int(info['game'])
    phone = int(info['phone'])
    id__gte = int(info['id__gte'])
    
    # Update phone status
    redisConn.set(
                'g_p_pos:%d:%d' % (game, phone),
                json.dumps({
                    't': now,
                    'lat': lat,
                    'lng': lng,
                }))
    redisConn.sadd('gamesWithNewInfo', str(game))
    
    # Get events
    events = [e.eventInfo for e in Event.getEvents(game, id__gte)]
    
    # Get {phoneStates:, powerMode:}
    info = loadGameInfo(game)
    
    return {
        'phoneStates': info['phoneStates'],
        'powerMode': info['powerMode'],
        'events': events,
    }
    
    return info


@apiRequest('find_games')
def api_find_games(r, info):
    
    lat = info['lat']
    lng = info['lng']
    phoneToken = info['phoneToken']
    
    phone = Phone.forToken(phoneToken)
    
    #TODO find by lastEventAt, lat, lng
    
    items = []
    for game in (Game.objects
                        .order_by('-id'))[:25]:
        items.append({
            'id': game.id,
        })
    
    return {
        'phone': phone.id,
        'items': items,
    }


@apiRequest('find_maps')
def api_find_maps(r, info):
    
    lat = info['lat']
    lng = info['lng']
    phoneToken = info['phoneToken']
    
    phone = Phone.forToken(phoneToken)
    
    #TODO find by lastEventAt, lat, lng
    
    items = []
    for game in (Map.objects
                        .order_by('-id'))[:25]:
        items.append({
            'id': game.id,
        })
    
    return {
        'phone': phone.id,
        'items': items,
    }


@apiRequest('new_game')
def api_new_game(r, info):
    
    phone = str(info['phone'])
    
    mapModel = Map.objects.get(id=info['map'])
    mapInfo = mapModel.info
    
    gameModel = Game.create(mapModel)
    game = gameModel.id
    
    redisConn.set('g_mapInfo:%d' % game, json.dumps(mapInfo))
    
    
    redisConn.rpush('g_phones:%d' % game, str(phone))
    Event.appendEvent(game, {
        'type': OHHAI_EVENT,
        'phone': phone,
        'name': Phone.objects.get(id=phone).name,
    })
    
    if info.get('redirect'):
        return HttpResponseRedirect('/watch-game/?id=' + str(game))#DRY
    
    return {
        'game': game,
        'gameToken': gameModel.token,
        'mapInfo': mapInfo,
    }


@apiRequest('join_game')
def api_join_game(r, info):
    
    phone = int(info['phone'])
    game = int(info['game'])
    
    j = redisConn.get('g_mapInfo:%d' % game)
    mapInfo = json.loads(j)
    
    if str(phone) not in set(redisConn.lrange('g_phones:%d' % game, 0, -1)):
        redisConn.rpush('g_phones:%d' % game, str(phone))
        Event.appendEvent(game, {
            'type': OHHAI_EVENT,
            'phone': phone,
            'name': Phone.objects.get(id=phone).name,
        })
    
    return {
        'mapInfo': mapInfo,
    }



@apiRequest('update_phone_settings')
def api_update_phone_settings(r, info):
    
    name = info['name']
    phoneToken = info['phoneToken']
    
    phone = Phone.forToken(phoneToken)
    phone.setName(name)
    
    return {
        'phone': phone.id,
    }


@apiRequest('map_create')
def api_map_create(r, info):
    
    map = Map.create()
    
    return {
        'token': map.token,
    }


@apiRequest('map_add_points')
def api_map_add_points(r, newInfo):
    
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


#### Temp API calls:

@apiRequest('debug')
def api_debug(r, info):
    
    if 'raise' in r.REQUEST:
        raise Exception
    
    return {
        'method': r.method,
        'POST': r.POST,
        'GET': r.GET,
    }


