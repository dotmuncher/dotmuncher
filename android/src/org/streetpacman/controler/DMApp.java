package org.streetpacman.controler;

import org.json.JSONException;
import org.json.JSONObject;
import org.streetpacman.states.DMPhone;

public class DMApp {
	public DMPhone dmPhone;
	
	public DMApp(String deviceId) {
		dmPhone = new DMPhone();
		dmPhone.phoneToken = "a_" + deviceId;
	}	

	public void update_phone_settings() throws JSONException{
		JSONObject json = dmPhone.getJSONFor_update_phone_settings();
		json = DMNet.api("update_phone_settings",json); 
		dmPhone.phone = json.getInt("phone");
	}
	
	public void find_games() throws JSONException{
		JSONObject json = dmPhone.getJSONFor_find();
		json = DMNet.api("find_games",json);
	}
	
	public void find_maps() throws JSONException{
		JSONObject json = dmPhone.getJSONFor_find();
		json = DMNet.api("find_maps",json);
	}
	
	public void new_game() throws JSONException{
		JSONObject json = dmPhone.getJSONFor_new_game();
		json = DMNet.api("find_maps",json);
	}
	
	public void join_game() throws JSONException{
		JSONObject json = dmPhone.getJSONFor_join_game();
		json = DMNet.api("join_game",json);
	}
	
	public void update() throws JSONException{
		JSONObject json = dmPhone.getJSONFor_update();
		json = DMNet.api("update",json);
		dmPhone.powerMode = json.getBoolean("powerMode");
		
	}
}
