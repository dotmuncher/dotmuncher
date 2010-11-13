#coding=utf-8

import unittest
from unittest import TestCase
import json
from urllib2 import HTTPError

from dotmuncher.dm_util import simpleGet, randomToken

from dotmuncher.constants import COLLISION_COORD_PLUSORMINUS


def api(name, info, host='localhost:8000'):
    print(['API', name, info])
    url = 'http://%s/api/v0/%s.json' % (host, name)
    j = simpleGet(url, GET={'json': json.dumps(info)}, verbose=True)
    return json.loads(j)


class SampleGame(TestCase):
    def runTest(self):
        
        print('===== SampleGame =====')
        
        # Create an empty map
        d = api('map_create', {})
        mapToken = d['token']
        api('map_add_points', {'token': mapToken, 'done': True})
        
        n1 = 'Dirk Gently'
        n2 = u'Treaty of Nöteborg'
        n3 = u'まるさん猫'
        
        # Generate phone tokens
        pt1 = 'test_' + randomToken(10)
        pt2 = 'test_' + randomToken(10)
        pt3 = 'test_' + randomToken(10)
        
        # 1, 2, 3: update_phone_settings
        api('update_phone_settings', {'phoneToken': pt1, 'name': n1})
        api('update_phone_settings', {'phoneToken': pt2, 'name': n2})
        api('update_phone_settings', {'phoneToken': pt3, 'name': n3})
        
        # 1: find_maps
        d = api('find_maps', {'lat': '1', 'lng': '1', 'phoneToken': pt1})
        p1 = d['phoneId']
        map = d['items'][0]['id']
        
        # 1: new_game
        d = api('new_game', {'map': map, 'phone': p1})
        game = d['game']
        mapInfo = d['mapInfo']
        
        # 2: find_games
        d = api('find_games', {'lat': '1', 'lng': '1', 'phoneToken': pt2})
        p2 = d['phoneId']
        
        # 3: find_games
        d = api('find_games', {'lat': '1', 'lng': '1', 'phoneToken': pt3})
        p3 = d['phoneId']
        
        assert p3 == p2 + 1 == p1 + 2
        
        # 2: join_game
        d = api('join_game', {'game': game, 'phone': p2})
        
        # 3: join_game
        d = api('join_game', {'game': game, 'phone': p3})
        
        # 3: update(s)
        for lng in ['-2.2', '-2.3', '-2.4']:
            d = api('update', {
                'lat': '-1.1',
                'lng': lng,
                'hacc': '1',
                'vacc': '1',
                'game': game,
                'phone': p1,
                'id__gte': 0,
            })
            assert d['phoneStates'][0]['lng'] == lng
            assert 'lng' not in d['phoneStates'][1]
            assert 'lng' not in d['phoneStates'][2]
        
        # TODO: collisions, ...
        
        print 'Game:', game


if __name__ == '__main__':
    unittest.main()
