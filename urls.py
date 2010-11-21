
import os

from django.conf.urls.defaults import *
from django.conf import settings
from django.shortcuts import render_to_response

from dotmuncher.py.dm_util import parentOf


REPO = parentOf(os.path.abspath(__file__))


#### Pages

handler404 = "dotmuncher.py.views.handler404"
handler500 = "dotmuncher.py.views.handler500"

urlpatterns = patterns('dotmuncher.py.views',
    
    url(r'^$', 'index', name='index'),
    url(r'^intro/$', 'intro', name='intro'),
    url(r'^iphone/$', 'iphone', name='iphone'),
    url(r'^android/$', 'android', name='android'),
    url(r'^about/$', 'about', name='about'),
    url(r'^privacy/$', 'privacy', name='privacy'),
    
    url(r'^new-board/$', 'new_map', name='new_map'),
    
    url(r'^games/$', 'games', name='games'),
    url(r'^boards/$', 'maps', name='maps'),
    
    url(r'^board/$', 'map', name='map'),
    url(r'^watch-game/$', 'game', name='game'),
    
)

#### API views

urlpatterns += patterns('dotmuncher.py.views_api',
    
    url(r'^api/v0/debug\.json$', 'api_debug', name='api_debug'),
    
    url(r'^api/v0/find_games\.json$', 'api_find_games', name='api_find_games'),
    url(r'^api/v0/find_maps\.json$', 'api_find_maps', name='api_find_maps'),
    url(r'^api/v0/new_game\.json$', 'api_new_game', name='api_new_game'),
    url(r'^api/v0/join_game\.json$', 'api_join_game', name='api_join_game'),
    url(r'^api/v0/update_phone_settings\.json$', 'api_update_phone_settings', name='api_update_phone_settings'),
    url(r'^api/v0/update\.json$', 'api_update', name='api_update'),
    
    url(r'^api/v0/map_create\.json$', 'api_map_create', name='api_map_create'),
    url(r'^api/v0/map_add_points\.json$', 'api_map_add_points', name='api_map_add_points'),
)

#### Dev views

urlpatterns += patterns('dotmuncher.py.views_dev',
    url(r'^dev/events/$', 'dev_events', name='dev_events'),
    url(r'^dev/requests/$', 'dev_requests', name='dev_requests'),
)

#### JavaScript (for dev mode)
# Assumes your deployment system will never let /static/* reach Django

urlpatterns += patterns('pj.django',
    url(r'^static/js/dotmuncher\.js$', 'jsView',
        {
          'main': 'dotmuncher.main',
          'jsPrefix': '''
                        var G = {};
                        var EXTERNAL_MOUNT = "dotmuncher";'''},
        name='dotmuncher_js'),
)

#### /static/css/ (for dev mode)
if settings.DEVMODE:
    urlpatterns += patterns('',
        url(r'static/css/dotmuncher\.css',
            lambda r: render_to_response('dotmuncher/css/dotmuncher.css',
                                         {},
                                         mimetype='text/css'),
            name='dotmuncher_css'),
    )


#### /static/ (for dev mode)
# Assumes your deployment system will never let /static/* reach Django
if settings.DEVMODE:
    staticRoot = os.path.join(REPO, 'static')
    if os.path.isdir(staticRoot):
        urlpatterns += patterns('',
            url(r'static(?P<path>.+)',
                'django.views.static.serve',
                {'document_root': staticRoot}))


