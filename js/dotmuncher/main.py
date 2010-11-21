
from core.window import addCssToHead

from dotmuncher.css import CSS
from dotmuncher.thumbnails import *
from dotmuncher.stuff.BoardWidget import *
from dotmuncher.stuff.GameWidget import *



def init():
    addCssToHead(CSS)


G.init = init
G.drawMapThumbnails = drawMapThumbnails
G.BoardWidget = BoardWidget
G.GameWidget = GameWidget

window[EXTERNAL_MOUNT] = G
