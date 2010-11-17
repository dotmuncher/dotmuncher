package org.streetpacman.controler;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.streetpacman.states.DMGame;
import org.streetpacman.states.DMPhone;

import android.util.Log;

public class DMApp {
	public DMPhone dmPhone;
	public DMGame dmGame;
	public ArrayList<Integer> al_games = new ArrayList<Integer>();
	public ArrayList<Integer> al_maps = new ArrayList<Integer>();
	
	public DMApp(String deviceId) {
		dmPhone = new DMPhone();
		dmGame = new DMGame();
		dmPhone.phoneToken = "a_" + deviceId;
	}	

	public void update_phone_settings() throws JSONException{
		JSONObject json = dmPhone.getJSONFor_update_phone_settings();
		json = DMNet.api("update_phone_settings",json); 
		dmPhone.phoneId = json.getInt("phoneId");
	}
	
	public void find_games() throws JSONException{
		JSONObject json = dmPhone.getJSONFor_find();
		json = DMNet.api("find_games",json);
		if(dmPhone.phoneId == json.getInt("phoneId")){
			JSONArray json_array = json.getJSONArray("items");
			for(int i=0;i<json_array.length();i++){
				al_games.add(json_array.getJSONObject(i).getInt("id"));
			}
		}else{
			Log.i("find_games","phoneId not match");
			throw new JSONException("find_games");
		}
	}
	
	public void find_maps() throws JSONException{
		JSONObject json = dmPhone.getJSONFor_find();
		json = DMNet.api("find_maps",json);
		if(dmPhone.phoneId == json.getInt("phoneId")){
			JSONArray json_array = json.getJSONArray("items");
			for(int i=0;i<json_array.length();i++){
				al_maps.add(json_array.getJSONObject(i).getInt("id"));
			}
		}else{
			Log.i("find_maps","phoneId not match");
			throw new JSONException("find_maps");
		}
	}
	
	public void new_game() throws JSONException{
		JSONObject json = dmPhone.getJSONFor_new_game();
		json = DMNet.api("new_game",json);
		dmGame.game = json.getInt("game");
		
	}
	
	public void join_game() throws JSONException{
		JSONObject json = dmPhone.getJSONFor_join_game();
		json = DMNet.api("join_game",json);
		dmGame.game = json.getInt("game");
	}
	
	public void update() throws JSONException{
		JSONObject json = dmPhone.getJSONFor_update();
		json = DMNet.api("update",json);
		dmPhone.powerMode = json.getBoolean("powerMode");
		
	}
}
