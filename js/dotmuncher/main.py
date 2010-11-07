
from core.window import addCssToHead

from dotmuncher.css import CSS

from dotmuncher.widgets.MapDefiner import *
from dotmuncher.widgets.GameViewer import *



def init():
    addCssToHead(CSS)
    print('init')



G.init = init
G.MapDefiner = MapDefiner
G.GameViewer = GameViewer

window[EXTERNAL_MOUNT] = G
