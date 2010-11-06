
from goog import bind

from core.element import *


class Button(Element):
    
    def __init__(self, className, callback):
        
        suffix = (' ' + className) if className else ''
        super('a', 'Button_CSS' + suffix,
                        {'href': 'javascript:(function(){})();'})
        
        self._on('mousedown', callback)



