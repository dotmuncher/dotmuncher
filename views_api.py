
import json

from a_app.decorators import jsonView

from dotmuncher.models import Event


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
def api_events(r):
    
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
    
    
    return {
        'events': [],
    }

