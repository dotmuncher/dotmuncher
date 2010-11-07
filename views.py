
from a_app.decorators import view

from dotmuncher.models import Map


@view('dotmuncher/404.html')
def handler404(r):
  pass


@view('dotmuncher/500.html')
def handler500(r):
  pass


@view('dotmuncher/index.html')
def index(r):
    pass


@view('dotmuncher/maps.html')
def maps(r):
    pass


@view('dotmuncher/games.html')
def games(r):
    pass





@view('dotmuncher/define_map.html')
def define_map(r):
    pass


@view('dotmuncher/map.html')
def map(r):
    
    if 'id' not in r.GET:
        return HttpResponse('Invalid URL')#HANDLE
    token = r.GET['id']
    
    map = Map.objects.get(token=token)
    
    return {
        'map': map,
    }


@view('dotmuncher/game.html')
def game(r):
    pass



