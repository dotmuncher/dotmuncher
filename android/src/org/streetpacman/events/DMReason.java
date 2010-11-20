package org.streetpacman.events;

public enum DMReason {
	GAMEOVER_PACMAN_WINS(1),
	GAMEOVER_PACMAN_LOSES(2);
	private final int id;
	DMReason(int id){
		this.id = id;
	}
}
