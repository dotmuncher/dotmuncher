package org.streetpacman.store;

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

	// results
	public static final int SHOW_GAME = 1;

	// API
	public static final int update_phone_settings = 0;
	public static final int find_maps = 1;
	public static final int find_games = 2;
	public static final int new_game = 3;
	public static final int join_game = 4;
	public static final int update = 5;

	public static final String[] API = { "update_phone_settings", "find_maps",
			"find_games", "new_game", "join_game", "update" };
}
