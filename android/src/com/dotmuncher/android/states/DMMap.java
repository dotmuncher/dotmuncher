package com.dotmuncher.android.states;

import java.util.List;

import com.dotmuncher.android.events.DMMapEvent;

public class DMMap {
	private List<DMMapEvent> maps;

	public List<DMMapEvent> getMaps() {
		return maps;
	}

	public void setMaps(List<DMMapEvent> maps) {
		this.maps = maps;
	}
}
