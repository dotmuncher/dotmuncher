
from goog import bind

from core.element import *
from core.event import *
from core.geom import *
from core.ajax import jsonp_request
from core.json import json_encode

from dotmuncher.widgets.Button import Button

from dotmuncher.misc import GMOverlayViewSubclass


MAPDENIFER_SAMPLE_MINDIST = 5
MD_MAX_BATCH_SIZE = 120


class MapSaver:
    
    def __init__(self, points, completionCallback):
        
        self._points = points
        self._completionCallback = completionCallback
        
        self._pos = 0
        self._numPoints = len(points)
        
        jsonp_request({
            '_url': '{% url api_map_create %}',
            '_GET': {
                'json': json_encode({
                    
                }),
            },
            '_success': bind(self._batchCallback, self),
        })
    
    def _batchCallback(self, info):
        
        mapToken = info.token
        
        if self._pos < self._numPoints:
            
            batch = []
            batchSize = 0
            while (self._pos < self._numPoints) and batchSize < MD_MAX_BATCH_SIZE:
                point = self._points[self._pos]
                batch.push(point)
                self._pos += 1
                batchSize += len(point[0]) + len(point[1]) + 3
            
            info = {
                'token': mapToken,
                'pathPoints': batch,
            }
            
            if self._pos == self._numPoints:
                info.done = True
            
            jsonp_request({
                '_url': '{% url api_map_add_points %}',
                '_GET': {
                    'json': json_encode(info),
                },
                '_success': bind(self._batchCallback, self),
            })
        
        else:
            self._completionCallback(mapToken)


class MapDefinerOverlay(Element):
    
    def __init__(self, controller, map):
        
        self._map = map
        self._overlayView = GMOverlayViewSubclass(map)
        
        self._points = []
        
        lightbox = Element('div', 'MapDefiner_overlay_lightbox_CSS')
        
        super('div', 'MapDefiner_overlay_CSS', [lightbox])
        
        self._on('mousedown', bind(self._mousedown, self))
        self._on('mousemove', bind(self._mousemove, self))
        self._on('mouseup', bind(self._mouseup, self))
        
        self._lastSamplePos = Pos(-1000, -1000)
        self._dragging = 0
    
    def _setEnabled(self, enabled):
        e_setStyle(self, 'display',
                    'block' if enabled else 'none')
    
    def _sample(self, e, ratelimit):
        
        pos = pos_minus(ev_pos(e), self._overlayRect)
        point = google.maps.Point(pos.x, pos.y)
        
        if pos_distance(pos, self._lastSamplePos) > MAPDENIFER_SAMPLE_MINDIST:
            
            # Log ["lat","lng"]
            ll = self._canvasProjection.fromContainerPixelToLatLng(point)
            self._points.push(ll.toUrlValue(8))
            
            # Show the point
            e = Element('div', 'overlay_mapDot_CSS')
            e_setPos(e, pos_minus(pos, Pos(1, 1)))
            e_appendChild(self, e)
            
            self._lastSamplePos = pos
    
    def _mousedown(self, e):
        
        self._overlayRect = e_getRect(self)
        self._canvasProjection = self._overlayView.getProjection()
        
        if not ev_isRightButton(e):
            self._dragging = 1
            self._sample(e)
            ev_stop(e)
    
    def _mousemove(self, e):
        if self._dragging:
            self._sample(e)
    
    def _mouseup(self, e):
        if self._dragging:
            if not ev_isRightButton(e):
                self._sample(e)
                self._dragging = 0


# AJDUST MAP --> ADD POINTS --> SAVING
MD_STATE_ADJUST_MAP = 1
MD_STATE_ADD_POINTS = 2
MD_STATE_SAVING = 3

class MapDefiner:
    
    def __init__(self, toolbarContainer, overlayContainer):
        
        self._state = MD_STATE_ADJUST_MAP
        
        editButton = Button('MapDefiner_toolbar_editButton_CSS',
                                bind(self._editClicked, self))
        saveButton = Button('MapDefiner_toolbar_saveButton_CSS',
                                bind(self._saveClicked, self))
        
        self._toolbar = Element('div', 'MapDefiner_toolbar_CSS', [
            editButton,
            saveButton,
        ])
        
        self._overlay = MapDefinerOverlay(self, dotmuncher.map)
        
        toolbarContainer.appendChild(self._toolbar._)
        overlayContainer.appendChild(self._overlay._)
    
    def _editClicked(self):
        if self._state == MD_STATE_ADJUST_MAP:
            self._state = MD_STATE_ADD_POINTS
            self._overlay._setEnabled(1)
    
    def _saveClicked(self):
        self._state = MD_STATE_SAVING
        MapSaver(
            self._overlay._points,
            bind(self._saved, self))
        #TODO saving animation
    
    def _saved(self, mapToken):
        goToUrl('{% url map %}?id=' + mapToken)

