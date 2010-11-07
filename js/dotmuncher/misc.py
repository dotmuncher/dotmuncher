

def GMOverlayViewSubclass(map):
    self.setMap(map)
    self.set('visible', False)

if window.google and window.google.maps and google.maps.OverlayView:
    GMOverlayViewSubclass.prototype = google.maps.OverlayView()
    GMOverlayViewSubclass.prototype.onAdd = lambda: None
    GMOverlayViewSubclass.prototype.draw = lambda: None
    GMOverlayViewSubclass.prototype.onRemove = lambda: None



