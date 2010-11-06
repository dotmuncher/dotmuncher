
from goog import bind

from core.element import *

from dotmuncher.widgets.Button import Button




class MapDefinerOverlay(Element):
    
    def __init__(self, controller, map):
        
        self._map = map
        
        super('div', 'MapDefiner_overlay_CSS')
    
    def _setEnabled(self, enabled):
        e_setStyle(self, 'display',
                    'block' if enabled else 'none')



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
        self._editMode = not self._editMode
        self._overlay._setEnabled(self._editMode)
    
    def _saveClicked(self):
        print('TODO save')

