
from core.window import addCssToHead

from dotmuncher.css import CSS

from dotmuncher.stuff.BoardWidget import *
from dotmuncher.stuff.GameWidget import *



def init():
    addCssToHead(CSS)


G.init = init
G.BoardWidget = BoardWidget
G.GameWidget = GameWidget

window[EXTERNAL_MOUNT] = G
