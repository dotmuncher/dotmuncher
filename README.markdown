
## Quickstart: run the server on your laptop

#### Prereqs:

* [Django 1.2](#TODO), [Redis](#TODO), [redis-py](http://github.com/andymccurdy/redis-py)

#### Folders:

* Create a folder somewhere. By "ROOT", we'll be referring to that folder's full path.
* Put dotmuncher in ROOT
* Run <code>python dotmuncher/scripts/get-repos.py</code>, which will git clone these repos to ROOT:
    * [pyxc-pj](http://github.com/andrewschaaf/pyxc-pj), [dev_deployment](http://github.com/andrewschaaf/dev_deployment), [pj-core](http://github.com/andrewschaaf/pj-core), [pj-closure](http://github.com/andrewschaaf/pj-closure),

#### Prep stuff

* <code>cd ROOT/dev_deployment; mkdir -p tmpdir
* <code>cd ROOT/dev_deployment; env APP=dotmuncher python manage.py syncdb

#### Start the servers

* <code>cd ROOT/dev_deployment; redis-server conf/redis.conf</code>
* <code>cd ROOT/dev_deployment; env APP=dotmuncher python manage.py runserver</code>

#### Run the tests

* <code>env DJANGO\_SETTINGS\_MODULE=dev\_deployment.settings python tests/all.py</code>

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
    phone: phone integer
}</pre>

### find_games

<pre>{
    "lat": string
    "lng": string
    "phoneToken": string
}
{
    "phone": int
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
    "phone": int
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
    "phone": int
}
{
    "game": int id,
    "mapInfo": {
        "dotPoints": [...],
        "basePoints": [...],
        "powerPelletPoints": [...],
    },
}</pre>

### join_game
<pre>{
    "game": int id
    "phone": int
}
{
    "mapInfo": ...see new_game...
}</pre>

### update

<pre>{
    lat: ""
    lng: ""
    
    hacc: ""
    vacc: ""
    
    game: int
    phone: int
    
    id__gte: int
}
{
    phoneStates: [
        // ordered by the official order in which the phones joined the game
        {
            phone: int,
            // These will be included iff we've received a sample:
            lat: "...lat...",
            lng: "...lng...",
            idle: ms since update,
        },
        ...
    ],
    powerMode: bool,
    events: [
        {...}, // (i: event id) and (t: ms since epoch) have been added to each event's dictionary
        {...},
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
    k: '["p","lat","lng"]' for power pellets, '["d","lat","lng"]' for path dots
}

{
    type: GAME_OVER = 8
    reason: int
}
GAMEOVER_PACMAN_WINS = 1
GAMEOVER_PACMAN_LOSES = 2</pre>
