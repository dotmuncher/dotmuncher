
from a_app.decorators import view


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



@view('dotmuncher/game.html')
def game(r):
    pass



