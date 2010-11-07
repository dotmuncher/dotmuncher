
from dotmuncher.models import *
from dotmuncher.dm_util import view


@view('dotmuncher/dev/dev_events.html')
def dev_events(r):
    
    events = (Event.objects
                    .order_by('-id'))[:100]
    
    return {
        'events': events,
    }


@view('dotmuncher/dev/dev_requests.html')
def dev_requests(r):
    
    qs = APIRequest.objects.order_by('-id')
    
    if 'phone' in r.REQUEST:
        qs = qs.filter(phoneId=int(r.REQUEST['phone']))
    
    requests = qs[:30]
    
    return {
        'requests': requests,
    }


@view('dotmuncher/dev/all_mocks.html')
def all_mocks(r):
    return {
        
    }


@view('dotmuncher/dev/mock.html')
def mock(r):
    return {
        
    }


