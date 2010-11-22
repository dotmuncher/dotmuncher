
from core.element import *


def drawMapThumbnails(mapInfos):
    print('drawMapThumbnails')
    
    KEYS = ['dotPoints', 'basePoints', 'powerPelletPoints']
    CLASSES = [
        'BoardThumbnail_path_CSS',
        'BoardThumbnail_base_CSS',
        'BoardThumbnail_pellet_CSS',
    ]
    SIZES = [2, 2, 4]
    
    for token in dict(mapInfos):
        mapInfo = mapInfos[token]
        container = e_byId(token)
        pixelDim = e_getRect(container)
        htmls = ['<div class="BoardThumbnail_CSS" style="width: ' + 
                    pixelDim.w + 'px; height: ' + pixelDim.h + 'px;">']
        
        # Find bounds
        xmin = ymin = 1000
        xmax = ymax = -1000
        for i in range(len(KEYS)):
            for p in mapInfo[KEYS[i]]:
                halfSize = (SIZES[i] / 2)
                cx = 1 * p[1]
                cy = 1 * p[0]
                if cx < xmin:
                    xmin = cx
                if cy < ymin:
                    ymin = cy
                if cx > xmax:
                    xmax = cx
                if cy > ymax:
                    ymax = cy
        boundsPadding = (1/6) * (((xmax - xmin) + (ymax - ymin)) / 2)
        bounds = Rect(
                        xmin - boundsPadding,
                        ymin - boundsPadding,
                        (xmax - xmin) + 2 * boundsPadding,
                        (ymax - ymin) + 2 * boundsPadding)
        
        # Create points
        for i in range(len(KEYS)):
            for p in mapInfo[KEYS[i]]:
                halfSize = (SIZES[i] / 2)
                
                cx = 1 * p[1]
                cy = 1 * p[0]
                
                x = ((cx - bounds.x) / bounds.w) * pixelDim.w
                y = pixelDim.h - (((cy - bounds.y) / bounds.h) * pixelDim.h)
                
                htmls.push(
                        '<div class="' + CLASSES[i] + '"' +
                        ' style="top: ' + y + 'px;' +
                        ' left: ' + x + 'px;"' +
                        '></div>')
        
        htmls.push('</div>')
        e_setHtml(container, htmls.join(''))


