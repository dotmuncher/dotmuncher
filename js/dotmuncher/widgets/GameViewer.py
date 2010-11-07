
from goog import bind

from core.element import *
from core.event import *
from core.geom import *
from core.ajax import jsonp_request
from core.json import json_encode

from dotmuncher.widgets.Button import Button

from dotmuncher.misc import GMOverlayViewSubclass



class GameViewer:
    
    def __init__(self, toolbarContainer, overlayContainer, mapInfo):
        
        self._mapInfo = mapInfo
        
        self._toolbar = Element('div', 'GameViewer_toolbar_CSS')
        
        lightbox = Element('div', 'GameViewer_overlay_lightbox_CSS')
        overlay = Element('div', 'GameViewer_overlay_CSS', [lightbox])
        overlay._on('mousedown', ev_stop)
        self._overlay = overlay
        
        toolbarContainer.appendChild(self._toolbar._)
        overlayContainer.appendChild(self._overlay._)
        
        self._map = dotmuncher.map
        self._overlayView = GMOverlayViewSubclass(self._map)
        
        setTimeout(
            bind(self._initWhenMapHasCanvasProjection, self),
            1000)#TODO find callback for WhenMapHasCanvasProjection
    
    def _initWhenMapHasCanvasProjection(self):
        
        self._overlayRect = e_getRect(self._overlay)
        self._canvasProjection = self._overlayView.getProjection()
        
        for p in self._mapInfo.pathPoints:
            
            ll = google.maps.LatLng(
                    1 * p[0],
                    1 * p[1])
            point = self._canvasProjection.fromLatLngToContainerPixel(ll)
            
            e = Element('div', 'overlay_mapDot_CSS')
            e_setPos(e, pos_minus(point, Pos(1, 1)))
            e_appendChild(self._overlay, e)


