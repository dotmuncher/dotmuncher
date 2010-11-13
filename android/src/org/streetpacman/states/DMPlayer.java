package org.streetpacman.states;

import org.json.JSONException;
import org.json.JSONObject;

public class DMPlayer {
	public int player;
	public int game;
    public int map;
    public int phone;
    
    public JSONObject getJSONFor_new_game() throws JSONException{
    	JSONObject json = new JSONObject();
    	json.put("map", map);
    	json.put("phone", phone);
		return json;    	
    }
    
    public JSONObject getJSONFor_join_game() throws JSONException{
    	JSONObject json = new JSONObject();
    	json.put("game", game);
    	json.put("phone", phone);
		return json;    	
    }
}            