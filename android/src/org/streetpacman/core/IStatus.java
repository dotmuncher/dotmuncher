package org.streetpacman.core;

public interface IStatus {

	// xxx - dependent group
	// R - reserved
	// mode group
	// 0000 0000 0000 0000 0000 0000 0000 Rxxx
	public static final int MODE_SOLO = 0x00000000;
	public static final int MODE_GAME = 0x00000001;
	public static final int MODE_MACRO = 0x00000002;
	public static final int MODE_OBSERVER = 0x00000003;
	public static final int MODE_SOLO_GAME = 0x00000004;
	public static final int MODE_ERROR = 0x00000005;
	public static final int MODE_DEBUG = 0x00000006;

	// phase, game progression
	// 0000 0000 0000 0000 0000 0000 RRxx 0000
	public static final int GAME_READY = 0x00000000;
	public static final int GAME_PLAYING = 0x00000010;
	public static final int GAME_END = 0x00000020;


	
	// y - independent bit, composable, only 1,2,4,8 in 0x
	// visibility
	// RRRR yyyy 0000 0000 0000 0000 0000 0000
	public static final int VISIBLE_TO_PEER = 0x01000000;
	public static final int VISIBLE_TO_ADVERSARY = 0x02000000;
	public static final int VISIBLE_TO_OBSERVER = 0x04000000;
	public static final int VISIBLE_HIDE_GLOBAL = 0x08000000;
	
	// sensibility
	// 0000 0000 yyyy 0000 0000 0000 0000 0000	
	public static final int SENSIBLE_TO_ITEM = 0x00100000;
	public static final int SENSIBLE_TO_STAGE = 0x00200000;
	public static final int SENSIBLE_TO_ZONE = 0x00400000;
	public static final int SENSIBLE_TO_COLLISION = 0x00800000;
	
	// stage
	// 0000 0000 0000 yyyy 0000 0000 0000 0000
	public static final int STAGE_POWER_SELF = 0x00010000;
	public static final int STAGE_POWER_PEER = 0x00020000;
	public static final int STAGE_POWER_ADVERSARY = 0x00030000;
	public static final int STAGE_POWER_ENVIRONMENT = 0x00040000;
	
	// zone
	// 0000 0000 0000 0000 RRyy 0000 0000 0000
	public static final int ZONE_COLLISION_TO_ADV = 0x00001000;
	public static final int ZONE_VISIBLE_TO_ADV = 0x00002000;

	// player individual state related
	// 0000 0000 0000 0000 0000 RRyy 0000 0000
	public static final int PLAYER_ALIVE = 0x00000100;
	public static final int PLAYER_FROZEN = 0x00000200;

}
