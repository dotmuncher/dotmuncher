var TABLE_PREFIX = 'dotmuncher_';

var POSITION_EVENT = 1,
	OHHAI_EVENT = 2,
	PHONE_EATEN_EVENT = 6,
	ITEM_EATEN_EVENT = 7,
	GAME_OVER = 8;

var TYPENUM_TYPENAM_MAP = {
    POSITION_EVENT: 'POSITION_EVENT',
    OHHAI_EVENT: 'OHHAI_EVENT',
};

function Phone(){
    this.token = '';
    this.name_utf8 = '';
}
Phone.prototype = {
    db_table: TABBLE_PREFIX + 'phone',
    forToken: function(cls, token){
    },
    name: function(){
        return this.name_utf8;
    },
    setName: function(name){
        this.name_utf8 = name;
    }
};

var BLANK_MAP_INFO = {
    "pathPoints": [],
    "basePoints": [],
    "dotPoints": [],
    "powerPelletPoints": [],
};

function Map(token,createdAtUtc,infoJson){
    this.token = token;
    this.createdAtUtc = createdAtUtc;
    this.deleted = false;
    this.completed = false;
    this.lat = 0;
    this.lng = 0;
    this.infoJson = infoJson;
}
Map.prototype = {
    db_table: TABLE_PREFIX + 'map',
    info: function(){
        return STUB_MAP_INFO;
    },
    create: function(){
        var m = new Map(randomToken(8),new Date(),BLANK_MAP_INFO);
        //m.save();
        return m;
    }
};

function Game(){
    this.token = token;
    this.createdAtUtc = createdAtUtc;
    this.map = map;
    this.infoJson = {};
}
Game.prototype = {
    db_table: TABLE_PREFIX + 'game',
    create: function(){
        return new Game();
    },
    numEvents: function(){
        return 0;
    },
    info: function(){
        return infoJson;
    }
};

function Event(){
    this.gameId = gameId;
    this.eventInfo = eventInfo;
}
Event.prototype = {
    name: function(){
        return '';
    },
    eventJson: function(){
        return eventInfo;
    },
    appendEvent: function(gameId,eventInfo){
    },
    getEvents:function(gameId, id__gte){
    }
}


