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
	public static final int[] SPRITE_FRAME_ANIMS = { R.drawable.pacman_chomp,
			R.drawable.ghost_red, R.drawable.ghost_pink,
			R.drawable.ghost_orange, R.drawable.ghost_green,
			R.drawable.ghost_blue, R.drawable.ghost_eye,
			R.drawable.ghost_angry_white, R.drawable.ghost_angry_blue,
			R.drawable.pacman_dead, };

	// DMGeoPoint status
	public static final int POINT_INIT = 0;
	public static final int POINT_KILLED = 1;
}
