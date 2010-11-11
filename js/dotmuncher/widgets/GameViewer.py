
from goog import bind

from core.element import *
from core.event import *
from core.geom import *
from core.ajax import jsonp_request
from core.json import json_encode

from dotmuncher.widgets.Button import Button

from dotmuncher.misc import GMOverlayViewSubclass


class EventsFollower:
    
    def __init__(self, gameId, callback):
        
        self._gameId = gameId
        self._callback = callback
        
        self._i__gte = 0
        self._request()
    
    def _requestCallback(self, info):
        print('_requestCallback', self._i__gte)
        
        timeout = 800
        
        if info:
            for e in info.events:
                self._callback(e)
            self._i__gte = info.max_i + 1
            if len(info.events) > 0:
                timeout = 0
        
        setTimeout(
                bind(self._request, self),
                timeout)
    
    def _request(self):
        print('_request', self._i__gte)
        jsonp_request({
            '_url': '{% url api_submit_and_get_events %}',
            '_GET': {
                'json': json_encode({
                            'game': self._gameId,
                            'i__gte': self._i__gte,
                            'events': [],
                        }),
            },
            '_success': bind(self._requestCallback, self),
        })


class GameViewer:
    
    def __init__(self, map, gameId, toolbarContainer, overlayContainer, mapInfo):
        
        self._map = map
        self._gameId = gameId
        self._mapInfo = mapInfo
        
        self._eventsFollower = EventsFollower(gameId, bind(self._event, self))
        
        self._toolbar = Element('div', 'GameViewer_toolbar_CSS')
        
        lightbox = Element('div', 'GameViewer_overlay_lightbox_CSS')
        overlay = Element('div', 'GameViewer_overlay_CSS', [lightbox])
        overlay._on('mousedown', ev_stop)
        self._overlay = overlay
        
        toolbarContainer.appendChild(self._toolbar._)
        overlayContainer.appendChild(self._overlay._)
        
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
    
    def _event(self, e):
        print('event', e)


