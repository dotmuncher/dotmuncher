

CSS = '''

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


{##########/* Containers */##########}
#overlay_container {
    position: absolute;
    width: 800px;
    height: 450px;
}
#mapcanvas {
    
    position: absolute;
    top: 0px;
    left: 0px;
    
    width: 100%;
    height: 100%;
    
    z-index: 10;
}
#toolbar_container {
    width: 798px;
    height: 80px;
}


{##########/* MapDefiner_toolbar */##########}
.MapDefiner_toolbar_CSS {
    
    width: 100%;
    height: 100%;
    
    border-left: 1px solid black;
    border-top: 1px solid black;
    border-right: 1px solid black;
}

{##########/* MapDefiner_overlay */##########}
.MapDefiner_overlay_CSS {
    position: absolute;
    top: 0px;
    left: 0px;
    width: 100%;
    height: 100%;
    
    background: black;
    opacity: 0.2;
    filter: alpha(opacity=20);
    -ms-filter: progid:DXImageTransform.Microsoft.Alpha(opacity=20);
    
    display: none;
    z-index: 20;
}


'''