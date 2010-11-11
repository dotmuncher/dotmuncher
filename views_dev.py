
from dotmuncher.models import *
from dotmuncher.dm_util import view


@view('dotmuncher/dev/dev_events.html')
def dev_events(r):
    
    gameId = int(r.REQUEST['game'])
    
    events = list(Event.getEvents(gameId, 0))
    
    return {
        'events': events,
    }


@view('dotmuncher/dev/dev_requests.html')
def dev_requests(r):
    
    if 'phone' in r.REQUEST:
        requests = (APIRequest.objects
                        .order_by('-id')
                        .filter(phoneId=int(r.REQUEST['phone'])))[:30]
    else:
        requests = (APIRequest.objects
                        .order_by('-id'))[:30]
    
    return {
        'requests': requests,
    }

