
import json

from django.http import HttpResponseRedirect

from a_app.decorators import jsonView
from a.py import exceptionStr

from dotmuncher.models import Event, Map, Phone, Game, APIRequest


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
                x = f(r)
            except Exception:
                info['exception'] = exceptionStr()
                APIRequest.log(info)
                raise
            
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
    
    if info.get('redirect'):
        return HttpResponseRedirect('/watch-game/?id=' + game.token)#DRY
    
    return {
        'game': game.id,
        'gameToken': game.token,
        'mapInfo': map.info,
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
    
    # Save events
    for eventType, eventInfo in info['events']:
        e = Event(
            eventType=eventType,
            eventJson=json.dumps(eventInfo))
        e.save()
    
    #### Get events
    
    #TODO
    
    return {
        'events': [],
        'max_i': -1,
    }



@jsonView()
@logRequest('map_create')
def api_map_create(r):
    
    map = Map.create()
    
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

