

## API

<pre>GET /api/v0/..name....json?json=...input... ---> JSON response</pre>

<pre>phoneToken: ("i_" + UDID) if iOS, ("a_" +  Android phone id) if Android</pre>

### find_games

<pre>{
    "lat": string
    "lng": string
    "phoneToken": string
}</pre>

<pre>{
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
}</pre>

<pre>{
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
}</pre>

<pre>{
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
}</pre>

<pre>{
    "mapInfo": ...see new_game...
}</pre>

### submit\_and\_get\_events

<pre>{
    "i__gte": int, // request events with id >= this
    "events": [
        [
            POSITION_EVENT,
            {...}
        ],
        ...
    ]
}</pre>

<pre>{
    "events": [...],
    "max_i": int id of last event in events. (-1 if events == [])
}</pre>


## EVENTS


### OHHAI_EVENT

Each phone sends this when it joins a game.

<pre>[2, {
    "game": int id
    "phone": int id
}]</pre>

### POSITION_EVENT

Each phone sends this repeatedly.

<pre>[1, {
    "game": int id
    "phone": int id
    
    "lat": string
    "lng": string
    
    "hacc": number, in meters
    "vacc": number, in meters
}]</pre>

### POWER\_PELLET\_EVENT

<pre>[3, {
    "active": boolean
}]</pre>

### COLLISION_EVENT

<pre>[4, {
    "eater": int phone id,
    "eatee": int phone id,
}]</pre>

### DOT\_EATEN\_EVENT

<pre>[5, {
    "point": ["...lat...", "...lng..."]
}]</pre>
