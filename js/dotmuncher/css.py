

CSS = '''

body {
    background: #666;
}

#widget {
    position: absolute;
}
#overlay_container {
    position: relative;
    width: 800px;
    height: 450px;
    
    margin-bottom: 25px;
}
#mapcanvas {
    background: red;
    position: relative;
    top: 76px;
    left: 5px;
    
    width: 100%;
    height: 100%;
    
    z-index: 10;
}
.WidgetWithMap_CSS {
    position: absolute;
    top: 0;
    left: 0;
    /* SPRITE js */
    background: url("/static/images/widget/background.png");
    width: 818px;
    height: 539px;
}

{##########/* Button */##########}
.Button_CSS {
    
    display: block;
    cursor: pointer;
    
    outline: 0;
    border: 0;
}

{##########/* BoardWidget */##########}
.BoardWidgetRadio_CSS {
    position: absolute;
    top: 23px;
    left: 187px;
    z-index: 5;
}
.bounds_button_CSS {
    position: absolute;
    top: 0;
    left: 0;
    /* SPRITE js */
    background: url("/static/images/board-edit/bounds.png");
    width: 111px;
    height: 32px;
}
.bounds_button_hoverable_CSS:hover {
    /* SPRITE js */
    background: url("/static/images/board-edit/bounds_hover.png");
}
.bounds_button_active_CSS {
    /* SPRITE js */
    background: url("/static/images/board-edit/bounds_active.png");
}
.path_button_CSS {
    position: absolute;
    top: 0;
    left: 112px;
    /* SPRITE js */
    background: url("/static/images/board-edit/path.png");
    width: 111px;
    height: 32px;
}
.path_button_hoverable_CSS:hover {
    /* SPRITE js */
    background: url("/static/images/board-edit/path_hover.png");
}
.path_button_active_CSS {
    /* SPRITE js */
    background: url("/static/images/board-edit/path_active.png");
}
.base_button_CSS {
    position: absolute;
    top: 0;
    left: 183px;
    /* SPRITE js */
    background: url("/static/images/board-edit/base.png");
    width: 111px;
    height: 32px;
}
.base_button_hoverable_CSS:hover {
    /* SPRITE js */
    background: url("/static/images/board-edit/base_hover.png");
}
.base_button_active_CSS {
    /* SPRITE js */
    background: url("/static/images/board-edit/base_active.png")
}
.pellets_button_CSS {
    position: absolute;
    top: 0;
    left: 294px;
    /* SPRITE js */
    background: url("/static/images/board-edit/pellets.png");
    width: 121px;
    height: 32px;
}
.pellets_button_hoverable_CSS:hover {
    /* SPRITE js */
    background: url("/static/images/board-edit/pellets_hover.png");
}
.pellets_button_active_CSS {
    /* SPRITE js */
    background: url("/static/images/board-edit/pellets_active.png");
}
.save_button_CSS {
    position: absolute;
    top: 23px;
    left: 700px;
    /* SPRITE js */
    background: url("/static/images/board-edit/save.png");
    width: 82px;
    height: 32px;
}
.save_button_CSS:hover {
    /* SPRITE js */
    background: url("/static/images/board-edit/save_hover.png");
}
.save_button_CSS:active {
    /* SPRITE js */
    background: url("/static/images/board-edit/save_active.png");
}




{##########/* GameViewer */##########}
.GameViewer_toolbar_CSS {
    width: 100%;
    height: 100%;
    
    background: #444;
    border-left: 1px solid #916E2E;
    border-top: 1px solid #916E2E;
    border-right: 1px solid #916E2E;
    border-radius: 5px 5px 0 0;
    -moz-border-radius: 5px 5px 0 0;
    -webkit-border-radius: 5px 5px 0 0;
}
.GameViewer_overlay_CSS {
    position: absolute;
    top: 0px;
    left: 0px;
    width: 100%;
    height: 100%;
    z-index: 20;
}
.GameViewer_overlay_lightbox_CSS {
    
    width: 100%;
    height: 100%;
    
    background: black;
    opacity: 0.2;
    filter: alpha(opacity=20);
    -ms-filter: progid:DXImageTransform.Microsoft.Alpha(opacity=20);
}
.GameViewer_overlay_mapDot_CSS {
    position: absolute;
    width: 3px;
    height: 3px;
    background: red;
    z-index: 30;
}

{##########/* MapDefiner */##########}
.MapDefiner_toolbar_CSS {
    
    width: 100%;
    height: 100%;
    
    border-left: 1px solid black;
    border-top: 1px solid black;
    border-right: 1px solid black;
}
.MapDefiner_overlay_CSS {
    position: absolute;
    top: 0px;
    left: 0px;
    width: 100%;
    height: 100%;
    display: none;
    z-index: 20;
}
.MapDefiner_overlay_lightbox_CSS {
    width: 100%;
    height: 100%;
    
    background: black;
    opacity: 0.2;
    filter: alpha(opacity=20);
    -ms-filter: progid:DXImageTransform.Microsoft.Alpha(opacity=20);
}
.overlay_mapDot_CSS {
    position: absolute;
    width: 3px;
    height: 3px;
    background: red;
    z-index: 30;
}

'''