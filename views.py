
import json

from django.http import HttpResponse

from dotmuncher.models import Phone, Map, Game, Event
from dotmuncher.dm_util import view



@view('dotmuncher/index.html')
def index(r):
    pass


@view('dotmuncher/maps.html')
def maps(r):
    
    maps = (Map.objects
                    .filter(
                        deleted=False,
                        completed=True)
                    .order_by('-id'))
    
    return {
        'maps': maps,
    }


@view('dotmuncher/games.html')
def games(r):
    
    games = (Map.objects
                    .order_by('-id'))
    
    return {
        'games': games,
    }


@view('dotmuncher/define_map.html')
def define_map(r):
    #LATER: starting bounds from geo-location
    return {
        'zoom': 16,
        'centerLat': '40.730958',
        'centerLng': '-73.997008',
    }


@view('dotmuncher/map.html')
def map(r):
    
    if 'token' not in r.GET:
        return HttpResponse('Invalid URL')#HANDLE
    token = r.GET['token']
    
    map = Map.objects.get(token=token)
    
    return {
        
        'map': map,
        'newGameJson': json.dumps({'map': map.id, 'redirect': True}),
        
        #TODO from map
        'zoom': 16,
        'centerLat': '40.730958',
        'centerLng': '-73.997008',
    }


@view('dotmuncher/game.html')
def game(r):
    
    if 'id' not in r.GET:
        return HttpResponse('Invalid URL')#HANDLE
    id = r.GET['id']
    
    game = Game.objects.get(id=id)
    
    return {
        'game': game,
        'map': game.map,
    }


@view('dotmuncher/404.html')
def handler404(r):
    pass


@view('dotmuncher/500.html')
def handler500(r):
    pass


