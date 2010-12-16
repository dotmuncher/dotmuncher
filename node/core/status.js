// xxx - dependent group
// R - reserved
// mode group
var MASK_MODE = 0x0000000F;
// 0000 0000 0000 0000 0000 0000 0000 xxxx
var MODE_SOLO = 0x00000000;
var MODE_GAME = 0x00000001;
var MODE_MACRO = 0x00000002;
var MODE_OBSERVER = 0x00000003;
var MODE_SOLO_GAME = 0x00000004;
var MODE_TEAM = 0x00000005;
var MODE_SOLO_TEAM = 0x00000006;
var MODE_ERROR = 0x0000000e;
var MODE_DEBUG = 0x0000000f;

// phase, game progression state
var MASK_PHASE = 0x000000F0;
// 0000 0000 0000 0000 0000 0000 RRxx 0000
var PHASE_READY = 0x00000000;
var PHASE_PLAYING = 0x00000010;
var PHASE_END = 0x00000020;

// y - independent bit, so only 1,2,4,8 in 0x
// visibility
// RRRR yyyy 0000 0000 0000 0000 0000 0000
var VISIBLE_TO_PEER = 0x01000000;
var VISIBLE_TO_ADVERSARY = 0x02000000;
var VISIBLE_TO_OBSERVER = 0x04000000;
var VISIBLE_HIDE_GLOBAL = 0x08000000;

// sensibility
// 0000 0000 yyyy 0000 0000 0000 0000 0000	
var SENSIBLE_TO_ITEM = 0x00100000;
var SENSIBLE_TO_STAGE = 0x00200000;
var SENSIBLE_TO_ZONE = 0x00400000;
var SENSIBLE_TO_COLLISION = 0x00800000;

// stage
// 0000 0000 0000 yyyy 0000 0000 0000 0000
var STAGE_POWER_SELF = 0x00010000;
var STAGE_POWER_PEER = 0x00020000;
var STAGE_POWER_ADVERSARY = 0x00030000;
var STAGE_POWER_ENVIRONMENT = 0x00040000;

// zone
// 0000 0000 0000 0000 RRyy 0000 0000 0000
var ZONE_COLLISION_TO_ADV = 0x00001000;
var ZONE_VISIBLE_TO_ADV = 0x00002000;

// player individual state related
// 0000 0000 0000 0000 0000 RRyy 0000 0000
var PLAYER_ALIVE = 0x00000100;
var PLAYER_FROZEN = 0x00000200;

function Status(value){
	this.status = value;
}

Status.prototype._isMask = function(_mask, value) {
	return (this.status & _mask) == value;
}
Status.prototype._setMask = function(_mask, value) {
	this.status = (this.status & ~_mask) | value;
}

Status.prototype.isMode = function(mode){
	return this._isMask(MASK_MODE, mode);
}
Status.prototype.setMode = function(mode){
	this._setMask(MASK_MODE, mode);
}
Status.prototype.isPhase = function(phase){
	return this._isMask(MASK_PHASE, phase);
}
Status.prototype.setPhase = function(phase){
	this._setMask(MASK_PHASE, phase);
}
Status.prototype.set = function(v){
	this.status = this.status | v;
}
Status.prototype.unset = function(v){
	this.status = this.status & ~v;
}
// assuming mask contains single bit of 1
Status.prototype.is = function(v){
	return (this.status & v) != 0;
}
var	s = new Status(0x0);

s.set(VISIBLE_HIDE_GLOBAL);
s.is(VISIBLE_HIDE_GLOBAL);

s.unset(VISIBLE_HIDE_GLOBAL);
s.is(VISIBLE_HIDE_GLOBAL);

s.setMode(MODE_ERROR);
s.isMode(MODE_ERROR);

s.setPhase(PHASE_PLAYING);
s.isPhase(PHASE_PLAYING);
