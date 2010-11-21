package org.streetpacman.controler;

import java.lang.reflect.Method;

import org.json.JSONObject;

public enum DMAPI {
	update_phone_settings,
	find_games,
	find_maps,
	new_game,
	join_game,
	update;

	public void run(JSONObject json, DMApp dmApp){
		try {
			Class[] argTypes = { JSONObject.class };		
			Method meth = DMApp.class.getMethod(this.name(), argTypes);
			meth.invoke(dmApp, json);
		}catch (Exception e) {
		      System.err.println("Invoke() failed: " + e);
		}
	}	
}