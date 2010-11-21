
import time

from dotmuncher.models import redisConn, loadGameInfoAnd
from dotmuncher.constants import COLLISION_COORD_PLUSORMINUS


def main():
    
    while True:
        time.sleep(0.1)
        
        # See which games we need to handle
        try:
            games = list(map(int, redisConn.smembers('gamesWithNewInfo')))
        except Exception:
            #TODO log
            continue
        
        # Handle 'em
        for game in games:
            print '--- handling game %d ---' % game
            try:
                redisConn.srem('gamesWithNewInfo', str(game))
                
                phoneStates, powerMode, daemonState = loadStuff(game)
                
                # Check for (protagonist, other-phone) collisions
                protagonist = phoneStates[0]
                plat, plng = protagonist['lat'], protagonist['lng']
                for phone in phoneStates[1:]:
                    if 'lat' in phone:
                        if collision(plat, plng, phone['lat'], phone['lng']):
                            if powerMode:
                                eater, eatee = protagonist['phone'], phone['phone']
                            else
                                eatee, eater = protagonist['phone'], phone['phone']
                            print('Ka-Blamo!', eater, eatee)
                
            except Exception:
                #TODO log and don't raise
                raise


def collision(lat1, lng1, lat2, lng2):
    return (
                (abs(lat1 - lat2) < COLLISION_COORD_PLUSORMINUS) and
                (abs(lng1 - lng2) < COLLISION_COORD_PLUSORMINUS))


def loadStuff(game):
    
    stateKey = 'g_daemonState:%d' % game
    info, values = loadGameInfoAnd(game, [stateKey])
    if values[0]:
        state = json.loads(values[0])
    else:
        v = redisConn.get('g_mapInfo:%d' % game)
        assert v
        mapInfo = json.loads(v)
        
        state = {
            'items': [],# TODO dotPoints, powerPelletPoints
        }
        redisConn.set(stateKey, json.dumps(state))
    
    return info['phoneStates'], info['powerMode'], state


if __name__ == '__main__':
    main()
