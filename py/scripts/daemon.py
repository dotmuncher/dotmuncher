
import time, math, json

from dotmuncher.py.models import *
from dotmuncher.py.constants import COLLISION_COORD_DISTANCE


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
            try:
                redisConn.srem('gamesWithNewInfo', str(game))
                
                phoneStates, powerMode, deadPlayers, daemonState = loadStuff(game)
                
                # Check for (protagonist, other-phone) collisions
                protagonist = phoneStates[0]
                plat, plng = protagonist['lat'], protagonist['lng']
                print '--- handling game %d. protagonist @ %s, %s. dead: %s ---' % (
                                game, plat, plng, json.dumps(deadPlayers))
                if protagonist.get('alive'):
                    for state in phoneStates[1:]:
                        if state.get('alive') and 'lat' in state:
                            if collision(plat, plng, state['lat'], state['lng']):
                                
                                if powerMode:
                                    eater, eatee = protagonist['phone'], state['phone']
                                else:
                                    eatee, eater = protagonist['phone'], state['phone']
                                
                                deadPlayers[str(eatee)] = int(time.time() * 1000)
                                redisConn.set(
                                    'g_deadPlayers:%d' % game,
                                    json.dumps(deadPlayers))
                                Event.appendEvent(game, {
                                    'type': PHONE_EATEN_EVENT,
                                    'eater': eater,
                                    'eatee': eatee,
                                })
                                print(eater, 'EATED', eatee)
                
            except Exception:
                #TODO log and don't raise
                raise


def collision(lat1, lng1, lat2, lng2):
    lat1, lng1, lat2, lng2 = map(float, (lat1, lng1, lat2, lng2))
    return math.sqrt((lat1 - lat2)**2 +
                     (lng1 - lng2)**2) < COLLISION_COORD_DISTANCE



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
    
    return info['phoneStates'], info['powerMode'], info['deadPlayers'], state


if __name__ == '__main__':
    main()
