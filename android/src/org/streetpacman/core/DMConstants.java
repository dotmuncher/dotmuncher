package org.streetpacman.core;

import org.streetpacman.R;

public class DMConstants {
	public static final String TAG = "DMStreetPacman";

	// Events
	public static final int OHHAI_EVENT = 2;
	public static final int PHONE_EATEN_EVENT = 6;
	public static final int ITEM_EATEN_EVENT = 7;
	public static final int GAME_OVER = 8;

	// Reasons
	public static final int GAMEOVER_PACMAN_WINS = 1;
	public static final int GAMEOVER_PACMAN_LOSES = 2;

	// API
	public static final int update_phone_settings = 0;
	public static final int find_maps = 1;
	public static final int find_games = 2;
	public static final int new_game = 3;
	public static final int join_game = 4;
	public static final int update = 5;

	public static final String[] API = { "update_phone_settings", "find_maps",
			"find_games", "new_game", "join_game", "update" };

	// requestCode
	public static final int SHOW_LOADING = 1;
	public static final int SHOW_BOARD = 2;
	public static final int SHOW_GAMESLIST = 3;

	// resultCode
	public static final int LOADING_TIMEOUT = 1;
	public static final int GAMESLIST_TIMEOUT = 2;

	// resId of Sprites
	public static final int[] SPRITES = { R.drawable.pacman_chomp, // 0
			R.drawable.ghost_red, // 1
			R.drawable.ghost_pink, // 2
			R.drawable.ghost_orange, // 3
			R.drawable.ghost_green, // 4
			R.drawable.ghost_blue, // 5
			R.drawable.fruit, // 6
			R.drawable.ghost_eye, // 7
			R.drawable.ghost_angry_blue, // 8
			R.drawable.ghost_angry_white, // 9
			R.drawable.pacman_dead // 10
	};

	public static final int[] POWERMODE = { 0, 8, 8, 8, 8, 8 };
	public static final int[] POWERMODE_END = { 0, 9, 9, 9, 9, 9 };
	public static final int[] DEAD = { 10, 7, 7, 7, 7, 7 };

	// DMGeoPoint status
	public static final int POINT_INIT = 0;
	public static final int POINT_KILLED = 1;

	// DMPhone status
	public static final int PHONE_INIT = 0;
	public static final int PHONE_KILLED = 1;

	// role
	// public static final int ROLE_INIT = -1;
	// public static final int ROLE_PACMAN = 0;
	// public static final int ROLE_GHOST_RED = 1;
	// public static final int ROLE_GHOST_PINK = 2;
	// public static final int ROLE_GHOST_ORANGE = 3;
	// public static final int ROLE_GHOST_GREEN = 4;
	// public static final int ROLE_GHOST_BLUE = 5;

	public static final String[] NOTES = {
		"YOU WIN!",
		"YOU LOSE!",
		"GAME Washing Square Park",
		"POWER MODE!",
		"+ 999",
		"+ 10",
		"DANGER!",
		"+ 50"
	};
}
