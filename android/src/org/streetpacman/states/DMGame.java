package org.streetpacman.states;

import java.util.List;

import org.streetpacman.events.DMGameEvent;



public class DMGame {
	private List<DMGameEvent> games;

	public List<DMGameEvent> getGames() {
		return games;
	}

	public void setGames(List<DMGameEvent> games) {
		this.games = games;
	}
}
