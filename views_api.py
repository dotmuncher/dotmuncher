
import json

from django.http import HttpResponseRedirect

from a_app.decorators import jsonView

from dotmuncher.models import Event, Map, Phone, Game



@jsonView()
def api_find_games(r):
    info = json.loads(r.REQUEST['json'])
    
    lat = info['lat']
    lng = info['lng']
    phoneToken = info['phoneToken']
    
    phone = Phone.forToken(phoneToken)
    
    items = []#TODO
    
    return {
        'phoneId': phone.id,
        'items': items,
    }


@jsonView()
def api_find_maps(r):
    info = json.loads(r.REQUEST['json'])
    
    lat = info['lat']
    lng = info['lng']
    phoneToken = info['phoneToken']
    
    phone = Phone.forToken(phoneToken)
    
    items = []#TODO
    
    return {
        'phoneId': phone.id,
        'items': items,
    }


@jsonView()
def api_new_game(r):
    
    info = json.loads(r.REQUEST['json'])
    
    map = Map.objects.get(id=info['map'])
    game = Game.create(map)
    
    if info.get('redirect'):
        return HttpResponseRedirect('/watch-game/?id=' + game.token)#DRY
    
    return {
        'game': game.id,
        'gameToken': game.token,
    }


@jsonView()
def api_submit_and_get_events(r):
    
    #### Save events
    
    if r.method != 'GET':
        return {'message': 'Request needs to be a GET'}
    
    try:
        info = json.loads(r.REQUEST['json'])
    except KeyError:
        return {'message': 'Need REQUEST["json"]'}
    except ValueError:
        return {'message': 'JSON invalid: '}
    
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
def api_map_create(r):
    
    map = Map.create()
    
    return {
        'token': map.token,
    }


@jsonView()
def api_map_add_points(r):
    
    newInfo = json.loads(r.REQUEST['json'])
    
    # Validate points:
    for ll in newInfo['points']:
        assert isinstance(ll, basestring)
        (lat, lng) = ll.split(',')
        float(lat)
        float(lng)
    
    map = Map.objects.get(token=newInfo['token'])
    
    mapInfo = map.info
    mapInfo['points'] += [ll.split(',') for ll in newInfo['points']]
    map.infoJson = json.dumps(mapInfo)
    
    if newInfo.get('done'):
        map.completed = True
    
    map.save()
    
    return {
        'token': map.token,
    }




@jsonView()
def api_debug(r):
    
    if 'raise' in r.REQUEST:
        raise Exception
    
    return {
        'method': r.method,
        'POST': r.POST,
        'GET': r.GET,
    }


@jsonView()
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

