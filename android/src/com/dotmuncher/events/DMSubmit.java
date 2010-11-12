package com.dotmuncher.events;

import java.util.ArrayList;
import java.util.List;

public class DMSubmit {
	private int game;
	private int i__gte;
	private List<DMEvent> events;
	
	public DMSubmit() {
		events = new ArrayList<DMEvent> ();
	}
	
	public int getGame() {
		return game;
	}
	public void setGame(int game) {
		this.game = game;
	}
	public int getI__gte() {
		return i__gte;
	}
	public void setI__gte(int i__gte) {
		this.i__gte = i__gte;
	}
	public List<DMEvent> getEvents() {
		return events;
	}
	public void setEvents(List<DMEvent> events) {
		this.events = events;
	}
}
