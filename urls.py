
import os

from django.conf.urls.defaults import *
from django.conf import settings


#### Pages

handler404 = "dotmuncher.views.handler404"
handler500 = "dotmuncher.views.handler500"

urlpatterns = patterns('dotmuncher.views',
    
    url(r'^$', 'index', name='index'),
    
    url(r'^games/$', 'games', name='games'),
    url(r'^maps/$', 'maps', name='maps'),
    
    url(r'^define-map/$', 'define_map', name='define_map'),
    
    url(r'^map/$', 'map', name='map'),
    url(r'^watch-game/$', 'game', name='game'),
)

#### API views

urlpatterns += patterns('dotmuncher.views_api',
    
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

urlpatterns += patterns('dotmuncher.views_dev',
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

#### /static/ (for dev mode)
# Assumes your deployment system will never let /static/* reach Django

def parentOf(path):
    return '/'.join(path.rstrip('/').split('/')[:-1])

if settings.DEVMODE:
    staticRoot = os.path.join(parentOf(__file__), 'static')
    if os.path.isdir(staticRoot):
        urlpatterns += patterns('',
            url(r'static(?P<path>.+)', 'django.views.static.serve', {'document_root': staticRoot}))


