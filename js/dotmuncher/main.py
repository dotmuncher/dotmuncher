
from core.window import addCssToHead

from dotmuncher.css import CSS

from dotmuncher.widgets.MapDefiner import *



def init():
    addCssToHead(CSS)
    print('init')



G.init = init
G.MapDefiner = MapDefiner

window[EXTERNAL_MOUNT] = G
