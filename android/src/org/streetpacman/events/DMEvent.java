package org.streetpacman.events;

public enum DMEvent {
	OHHAI_EVENT(2),
	PHONE_EATEN_EVENT(6),
	ITEM_EATEN_EVENT(7),
	GAME_OVER(8);
	private final int id;
	DMEvent(int id){
		this.id = id;
	}
}
