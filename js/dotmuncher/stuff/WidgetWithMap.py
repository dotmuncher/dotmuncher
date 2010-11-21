
from core.element import *


class WidgetWithMap(Element):
    
    def __init__(self, map, widgetContainer, overlayContainer):
        
        self._map = map
        self._widgetContainer = widgetContainer
        self._overlayContainer = overlayContainer
        
        super('div', 'WidgetWithMap_CSS')
        
        widgetContainer.appendChild(self._)


