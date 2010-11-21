package org.streetpacman.controler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.streetpacman.states.DMGame;
import org.streetpacman.states.DMMap;
import org.streetpacman.states.DMPhone;
import org.streetpacman.util.DMUtils;

import android.util.Log;

public class DMApp {
	public DMPhone dmPhone;
	public DMGame dmGame;
	public DMMap dmMap;
	public List<Integer> alGames;
	public List<Integer> alMaps;
	
	public DMApp(String deviceId) {
		dmPhone = new DMPhone();
		dmGame = new DMGame();
		dmMap = new DMMap();
		dmGame.dmMap = dmMap;
		
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
		JSONArray jsonArray = json.getJSONArray("items");
		alGames = new ArrayList<Integer>();
		for(int i=0;i<jsonArray.length();i++){
			alGames.add(jsonArray.getJSONObject(i).getInt("id"));
		}
	}
	
	public void find_maps(JSONObject json) throws JSONException{
		JSONArray jsonArray = json.getJSONArray("items");
		alMaps = new ArrayList<Integer>();
		for(int i=0;i<jsonArray.length();i++){
			alMaps.add(jsonArray.getJSONObject(i).getInt("id"));
		}
	}
	
	private void initGame(JSONObject json) throws JSONException{
		dmGame.game = json.getInt("game");
		JSONObject mapInfo = json.getJSONObject("mapInfo");
		dmMap.dotPoints = DMUtils.JSONArray2GeoPoints(mapInfo.getJSONArray("dotPoints"));
		dmMap.basePoints = DMUtils.JSONArray2GeoPoints(mapInfo.getJSONArray("basePoints"));
		dmMap.powerPelletPoints = DMUtils.JSONArray2GeoPoints(mapInfo.getJSONArray("powerPelletPoints"));
		dmMap.buildPointsMap();
	}
	
	public void new_game(JSONObject json) throws JSONException{
		initGame(json);		
	}
	
	public void join_game(JSONObject json) throws JSONException{
		initGame(json);
	}
	
	public void update(JSONObject json) throws JSONException{
		dmPhone.powerMode = json.getBoolean("powerMode");
		dmGame.updatePhoneStates(json.getJSONArray("phoneStates"));
		dmGame.updateEvents(json.getJSONArray("events"));
	}
}
