

## API

<pre>GET /api/v0/..name....json?json=...input... ---> JSON response</pre>

<pre>phoneToken: ("i_" + UDID) if iOS, ("a_" +  Android phone id) if Android
phone: an integer assigned by the server</pre>


### update\_phone\_settings

<pre>{
    phoneToken: 
    name: 
}
{
    phoneId: phone integer
}</pre>

### find_games

<pre>{
    "lat": string
    "lng": string
    "phoneToken": string
}
{
    "phoneId": int
    "items": [
        {
            "id": int
        },
        ...
    ]
}</pre>

### find_maps

<pre>{
    "lat": string
    "lng": string
    "phoneToken": string
}
{
    "phoneId": int
    "items": [
        {
            "id": int
        },
        ...
    ]
}</pre>


### new_game
<pre>{
    "map": int id
}
{
    "game": int id,
    "mapInfo": {
        "pathPoints": [["...lat...", "...lng..."], ...],
        "basePoints": [...],
        "dotPoints": [...],
        "powerPelletPoints": [...],
    },
}</pre>

### join_game
<pre>{
    "game": int id
}
{
    "mapInfo": ...see new_game...
}</pre>

### update

<pre>{
    'lat': ""
    'lng': ""
    
    'hacc': ""
    'vacc': ""
    
    'game': int
    'phone': int
    
    'id__gte': int
}
{
    phones: [
        // ordered by the official order in which the phones joined the game
        [phone, "...lat...", "...lng..."],
    ],
    powerMode: bool,
    events: [
        [id, {...}],
        [id, {...}],
        ...
    ]
}</pre>

## Events

<pre>{// sent by server when handling {new_game,join_game}
    type: OHHAI_EVENT = 2
    phone: phone integer
    name: string
}

{
    type: PHONE_EATEN_EVENT = 6
    eater: phone integer
    eatee: phone integer
}

{
    type: ITEM_EATEN_EVENT = 7
    k: '["p",lat,lng]' for power pellets, '["d",lat,lng]' for path dots
}

{
    type: GAME_OVER = 8
    reason: int
}
GAMEOVER_PACMAN_WINS = 1
GAMEOVER_PACMAN_LOSES = 2</pre>
