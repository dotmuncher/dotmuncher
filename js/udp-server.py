
dgram = require('dgram')

rc = require('redis').createClient()
rc.on('error',
    lambda err: console.log(
        "Redis error. Server: " + rc.host + 
        ":" + rc.port + ". Error: " + err))


server = dgram.createSocket('udp4')


def on_message(msg, rinfo):
    
    try:
        info = JSON.parse(msg.toString())
    except Exception as e:
        info = 0
    
    if not (
            info and
            info.game and
            info.phone and
            info.lat and
            info.lng):
        print('Invalid message: ' + msg.toString())
    else:
        rc.set(
            'g_p_pos:' + info.game + ':' + info.phone,
            JSON.stringify({
                't': Date().getTime(),
                'lat': info.lat,
                'lng': info.lng,
            }))
        rc.sadd('gamesWithNewInfo', '' + info.game)


def on_listening():
    address = server.address()
    print('Server listening on ' + address.address + ':' + address.port + '...')


def startServer():
    server.on('message', on_message)
    server.on('listening', on_listening)
    server.bind(41234)


rc.select(
        6,
        startServer)
