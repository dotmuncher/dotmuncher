
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
    
    url(r'^game/$', 'game', name='game'),
)


#### Mocks (for dev mode)
if settings.DEBUG:
    urlpatterns += patterns('dotmuncher.views_dev',
        url(r'^all-mocks/$', 'all_mocks', name='all_mocks'),
        url(r'^mock/(.+)$', 'mock', name='mock'),
    )


#### JavaScript (for dev mode)
if settings.DEBUG:
    urlpatterns += patterns('pj.django',
        url(r'^static/js/dotmuncher\.js', 'jsView',
            {
              'main': 'dotmuncher.main',
              'jsPrefix': '''
                            var G = window.dotmuncher;
                            var EXTERNAL_MOUNT = "dotmuncher";'''},
            name='dotmuncher_js'),
    )


#### /static/ (for dev mode)
if settings.DEBUG:
    from a_app.common_urls import patternsForUrlconfPath
    urlpatterns += patternsForUrlconfPath(__file__)


