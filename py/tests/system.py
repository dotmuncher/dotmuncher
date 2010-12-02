#coding=utf-8

import unittest, json, time, socket
from unittest import TestCase
from urllib2 import HTTPError

from dotmuncher.py.dm_util import simpleGet, randomToken
from dotmuncher.py.constants import COLLISION_COORD_DISTANCE
from dotmuncher.py.stubdata import A, B, X, Y, coordStr


udp_host, udp_port = 'localhost', 41234


class SampleGame(TestCase):
    
    def api(self, name, info, host='localhost:8000', udp=False):
        if udp:
            print(['API (UDP)', name, info])
            self.udp_socket.sendto(json.dumps(info), (udp_host, udp_port))
        else:
            print(['API' if udp else '', name, info])
            url = 'http://%s/api/v0/%s.json' % (host, name)
            j = simpleGet(url, GET={'json': json.dumps(info)}, verbose=True)
            return json.loads(j)
    
    def setUp(self):
        self.udp_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    
    def test_game(self):
        
        print('===== SampleGame =====')
        
        # Create an empty map
        d = self.api('map_create', {})
        mapToken = d['token']
        self.api('map_add_points', {'token': mapToken, 'done': True})
        
        n1 = 'Dirk Gently'
        n2 = u'Treaty of Nöteborg'
        n3 = u'まるさん猫'
        
        # Generate phone tokens
        pt1 = 'test_' + randomToken(10)
        pt2 = 'test_' + randomToken(10)
        pt3 = 'test_' + randomToken(10)
        
        # 1, 2, 3: update_phone_settings
        self.api('update_phone_settings', {'phoneToken': pt1, 'name': n1})
        self.api('update_phone_settings', {'phoneToken': pt2, 'name': n2})
        self.api('update_phone_settings', {'phoneToken': pt3, 'name': n3})
        
        # 1: find_maps
        d = self.api('find_maps', {'lat': '1', 'lng': '1', 'phoneToken': pt1})
        p1 = d['phone']
        map = d['items'][0]['id']
        
        # 1: new_game
        d = self.api('new_game', {'map': map, 'phone': p1})
        game = d['game']
        mapInfo = d['mapInfo']
        
        # 2: find_games
        d = self.api('find_games', {'lat': '1', 'lng': '1', 'phoneToken': pt2})
        p2 = d['phone']
        
        # 3: find_games
        d = self.api('find_games', {'lat': '1', 'lng': '1', 'phoneToken': pt3})
        p3 = d['phone']
        
        assert p3 == p2 + 1 == p1 + 2
        
        # 2: join_game
        d = self.api('join_game', {'game': game, 'phone': p2})
        d = self.api('join_game', {'game': game, 'phone': p2})
        d = self.api('join_game', {'game': game, 'phone': p2})
        d = self.api('join_game', {'game': game, 'phone': p2})
        #TODO: confirm this (only adds you once) and (only sends one event)
        
        # 3: join_game
        d = self.api('join_game', {'game': game, 'phone': p3})
        
        # 1: update(s)
        for lng in ['-2.2', '-2.3', '-2.4']:
            d = self.api('update', {
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
        
        
        def movePhone(game, phone, x, y, duration, sleep=0.05, udp=False):
            startTime = time.time()
            while (time.time() - startTime) < duration:
                t = (time.time() - startTime) / duration
                d = self.api('update', {
                    'lat': coordStr(x[0] + (y[0] - x[0]) * t),
                    'lng': coordStr(x[1] + (y[1] - x[1]) * t),
                    'hacc': coordStr(COLLISION_COORD_DISTANCE * 0.1),
                    'vacc': coordStr(COLLISION_COORD_DISTANCE * 0.1),
                    'game': game,
                    'phone': phone,
                    'id__gte': 10000,
                }, udp=udp)
                time.sleep(sleep)
        
        '''
            A           
             
               GGG       X
        B     GGG    

                    Y
        '''
        
        movePhone(game, p1, A, X, 3.0)
        
        P = [X[0], X[1] - (1.5 * COLLISION_COORD_DISTANCE)]
        movePhone(game, p2, Y, P, 3.0)
        #TODO assert no collision
        movePhone(game, p2, P, X, 2.0, udp=True)
        #TODO assert death
        movePhone(game, p2, X, P, 2.0)
        
        # TODO: collisions, ...
        
        print 'Game:', game


if __name__ == '__main__':
    unittest.main()
