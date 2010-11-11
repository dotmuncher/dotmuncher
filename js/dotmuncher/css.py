

CSS = '''

#overlay_container {
    position: relative;
    width: 800px;
    height: 450px;
    
    margin-bottom: 25px;
}
#mapcanvas {
    
    position: relative;
    top: 0px;
    left: 0px;
    
    width: 100%;
    height: 100%;
    
    z-index: 10;
}
#toolbar_container {
    
    margin-top: 25px;
    
    width: 798px;
    height: 80px;
}

{##########/* Buttons */##########}
.Button_CSS {
    
    display: block;
    cursor: pointer;
    
    outline: 0;
    border: 0;
}
.MapDefiner_toolbar_editButton_CSS {
    
    position: absolute;
    left: 100px;
    top: 20px;
    
    background: #C3EFFA;
    width: 120px;
    height: 40px;
}
.MapDefiner_toolbar_editButton_CSS:hover {
    background: #94E4F9;
}
.MapDefiner_toolbar_editButton_CSS:active {
    background: #5C8593;
}
.MapDefiner_toolbar_saveButton_CSS {
    
    position: absolute;
    left: 400px;
    top: 20px;
    
    background: #C3EFFA;
    width: 120px;
    height: 40px;
}
.MapDefiner_toolbar_saveButton_CSS:hover {
    background: #94E4F9;
}
.MapDefiner_toolbar_saveButton_CSS:active {
    background: #5C8593;
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