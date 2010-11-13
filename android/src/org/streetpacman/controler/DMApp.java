package org.streetpacman.controler;

import org.json.JSONException;
import org.json.JSONObject;
import org.streetpacman.states.DMPlayer;

public class DMApp {
	private DMPlayer dmPlayer;
	
	public DMApp(String deviceId) {
		dmPlayer = new DMPlayer();
		dmPlayer.phoneToken = "a_" + deviceId;
	}	

	public void update_phone_settings() throws JSONException{
		JSONObject json = dmPlayer.getJSONFor_update_phone_settings();
		json = DMNet.api("update_phone_settings",json); 
		dmPlayer.phone = json.getInt("phone");
	}
	
	public void find_games() throws JSONException{
		JSONObject json = dmPlayer.getJSONFor_find();
		json = DMNet.api("find_games",json);
	}
	
	public void find_maps() throws JSONException{
		JSONObject json = dmPlayer.getJSONFor_find();
		json = DMNet.api("find_maps",json);
	}
	
	public void new_game() throws JSONException{
		JSONObject json = dmPlayer.getJSONFor_new_game();
		json = DMNet.api("find_maps",json);
	}
	
	public void join_game() throws JSONException{
		JSONObject json = dmPlayer.getJSONFor_join_game();
		json = DMNet.api("join_game",json);
	}
	
	public void update() throws JSONException{
		JSONObject json = dmPlayer.getJSONFor_update();
		json = DMNet.api("update",json);
		dmPlayer.powerMode = json.getBoolean("powerMode");
		
	}
}
