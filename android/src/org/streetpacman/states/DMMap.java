package org.streetpacman.states;

import java.util.List;

import org.streetpacman.events.DMMapEvent;



public class DMMap {
	private List<DMMapEvent> maps;

	public List<DMMapEvent> getMaps() {
		return maps;
	}

	public void setMaps(List<DMMapEvent> maps) {
		this.maps = maps;
	}
}
