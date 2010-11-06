


## API
<pre>


POSITION_EVENT = 1


[POSITION_EVENT, {
    "lat": "12.3456789...",
    "lng": "12.3456789...",
}]


POST /api/v0/submit-and-get-events.json
    
    POST: json = {
        "i__gte": 0, // request events with id >= this
        "events": [
            [
                POSITION_EVENT,
                {...}
            ],
            ...
        ]
    }
    Output: {
        "events": [...]
    }
</pre>
