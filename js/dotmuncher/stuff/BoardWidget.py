
from goog import bind
from core.element import *
from core.event import *

from dotmuncher.stuff.Button import *
from dotmuncher.stuff.WidgetWithMap import *
from dotmuncher.stuff.BoardWidgetRadio import *


class BoardWidget(WidgetWithMap):
    
    def __init__(self, map, widgetContainer, overlayContainer):
        super(map, widgetContainer, overlayContainer)
        
        self._saveButton = Button('save_button_CSS', bind(self._save, self))
        e_appendChild(self, self._saveButton)
        
        e_appendChild(self, BoardWidgetRadio(bind(self._changeMode, self)))
        e_appendChild(self, self._saveButton)
        
        self._mode = -1
    
    def _changeMode(self, i):
        self._mode = i
        print('mode', i)
    
    def _save(self):
        print('save', i)

