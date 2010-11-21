
from goog import bind
from core.element import *
from core.event import *

from dotmuncher.stuff.Button import *


BWR_ACTIVE_CLASSES = [
    'bounds_button_active_CSS',
    'path_button_active_CSS',
    'base_button_active_CSS',
    'pellets_button_active_CSS',
]
BWR_HOVERBLE_CLASSES = [
    'bounds_button_hoverable_CSS',
    'path_button_hoverable_CSS',
    'base_button_hoverable_CSS',
    'pellets_button_hoverable_CSS',
]

class BoardWidgetRadio(Element):
    
    def __init__(self, callback):
        
        self._callback = callback
        
        super('div', 'BoardWidgetRadio_CSS')
        
        self._buttons = [
            Button(
                    'bounds_button_CSS bounds_button_hoverable_CSS',
                    bind(lambda e: self._mousedown(e, 0), self)),
            Button(
                    'path_button_CSS path_button_hoverable_CSS',
                    bind(lambda e: self._mousedown(e, 1), self)),
            Button(
                    'base_button_CSS base_button_hoverable_CSS',
                    bind(lambda e: self._mousedown(e, 2), self)),
            Button(
                    'pellets_button_CSS pellets_button_hoverable_CSS',
                    bind(lambda e: self._mousedown(e, 3), self)),
        ]
        self._selectedIndex = -1
        self._mousedown(0, 0)
        for e in self._buttons:
            e_appendChild(self, e)
    
    def _mousedown(self, e, i):
        
        if e:
            ev_stop(e)
        
        i0 = self._selectedIndex
        if i0 != -1:
            e_removeClass(self._buttons[i0], BWR_ACTIVE_CLASSES[i0])
            e_addClass(self._buttons[i0], BWR_HOVERBLE_CLASSES[i0])
        
        e_addClass(self._buttons[i], BWR_ACTIVE_CLASSES[i])
        e_removeClass(self._buttons[i], BWR_HOVERBLE_CLASSES[i])
        self._selectedIndex = i
        
        self._callback(i)
