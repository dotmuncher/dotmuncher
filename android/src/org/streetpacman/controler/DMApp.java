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
	
	public void net(DMAPI api) throws JSONException{
		JSONObject json = DMNet.callapi(api,dmPhone.getJSONFor(api));
		if(json!=null){
			api.run(json,this);
		}else{
			Log.i("net","json == null, network problem");
		}
	}

	public void update_phone_settings(JSONObject json) throws JSONException{
		dmPhone.phoneId = json.getInt("phoneId");
	}
	
	public void find_games(JSONObject json) throws JSONException{
		JSONArray json_array = json.getJSONArray("items");
		for(int i=0;i<json_array.length();i++){
			al_games.add(json_array.getJSONObject(i).getInt("id"));
		}
	}
	
	public void find_maps(JSONObject json) throws JSONException{
		JSONArray json_array = json.getJSONArray("items");
		for(int i=0;i<json_array.length();i++){
			al_maps.add(json_array.getJSONObject(i).getInt("id"));
		}
	}
	
	public void new_game(JSONObject json) throws JSONException{
		dmGame.game = json.getInt("game");
		
	}
	
	public void join_game(JSONObject json) throws JSONException{
		dmGame.game = json.getInt("game");
	}
	
	public void update(JSONObject json) throws JSONException{
		dmPhone.powerMode = json.getBoolean("powerMode");
	}
}
