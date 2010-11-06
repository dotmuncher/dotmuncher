
from a_app.decorators import view

from dotmuncher.models import Event


@view('dotmuncher/dev/dev_events.html')
def dev_events(r):
    
    events = (Event.objects
                    .order_by('-id'))[:20]
    
    return {
        'events': events,
    }



@view('dotmuncher/dev/all_mocks.html')
def all_mocks(r):
    return {
        
    }


@view('dotmuncher/dev/mock.html')
def mock(r):
    return {
        
    }


