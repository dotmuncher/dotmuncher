
from goog import bind

from core.element import *
from core.event import *
from core.geom import *

from dotmuncher.widgets.Button import Button


MAPDENIFER_SAMPLE_MINDIST = 5

'''
State machine, for now:
    
    INITIAL --> ADD POINTS --> SAVING

'''


def GMOverlayViewSubclass(map):
    self.setMap(map)
    self.set('visible', False)

GMOverlayViewSubclass.prototype = google.maps.OverlayView()
GMOverlayViewSubclass.prototype.onAdd = lambda: None
GMOverlayViewSubclass.prototype.draw = lambda: None
GMOverlayViewSubclass.prototype.onRemove = lambda: None

class MapDefinerOverlay(Element):
    
    def __init__(self, controller, map):
        
        self._map = map
        self._overlayView = GMOverlayViewSubclass(map)
        
        self._lls = []
        
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
            
            # Log the "lat,lng"
            ll = self._canvasProjection.fromContainerPixelToLatLng(point)
            self._lls.push(ll.toUrlValue(8))
            
            # Show the point
            e = Element('div', 'MapDefiner_overlay_dot_CSS')
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



class MapDefiner:
    
    def __init__(self, toolbarContainer, overlayContainer):
        
        self._editMode = 0
        
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
        # Cannot be undone, for now
        if not self._editMode:
            self._editMode = not self._editMode
            self._overlay._setEnabled(self._editMode)
    
    def _saveClicked(self):
        print('TODO save')

