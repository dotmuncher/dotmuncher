
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

urlpatterns += patterns('dotmuncher.views_api',
    
    url(r'^api/v0/debug\.json$', 'api_debug', name='api_debug'),
    url(r'^api/v0/temp\.json$', 'api_temp', name='api_temp'),
    
    
    url(r'^api/v0/find_games\.json$', 'api_find_games', name='api_find_games'),
    url(r'^api/v0/find_maps\.json$', 'api_find_maps', name='api_find_maps'),
    url(r'^api/v0/new_game\.json$', 'api_new_game', name='api_new_game'),
    url(r'^api/v0/submit_and_get_events\.json$', 'api_submit_and_get_events', name='api_submit_and_get_events'),
    
    
    url(r'^api/v0/api_map_create\.json$', 'api_map_create', name='api_map_create'),
    url(r'^api/v0/api_map_add_points\.json$', 'api_map_add_points', name='api_map_add_points'),
)




#### Dev views
#if settings.DEVMODE:
if True:#TEMP
    urlpatterns += patterns('dotmuncher.views_dev',
        
        url(r'^dev/events/$', 'dev_events', name='dev_events'),
        
        url(r'^all-mocks/$', 'all_mocks', name='all_mocks'),
        url(r'^mock/(.+)$', 'mock', name='mock'),
    )


#### JavaScript (for dev mode)
if settings.DEVMODE:
    urlpatterns += patterns('pj.django',
        url(r'^static/js/dotmuncher\.js', 'jsView',
            {
              'main': 'dotmuncher.main',
              'jsPrefix': '''
                            var G = {};
                            var EXTERNAL_MOUNT = "dotmuncher";'''},
            name='dotmuncher_js'),
    )


#### /static/ (for dev mode)
if settings.DEVMODE:
    from a_app.common_urls import patternsForUrlconfPath
    urlpatterns += patternsForUrlconfPath(__file__)


