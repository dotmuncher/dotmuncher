package org.streetpacman.controler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.streetpacman.store.DMConstants;
import org.streetpacman.store.DMMap;
import org.streetpacman.store.DMPhone;
import org.streetpacman.store.DMPhoneState;
import org.streetpacman.util.DMUtils;

import android.util.Log;

public class DMApp {	
	public DMPhone dmPhone;
	public DMMap dmMap;
	public List<Integer> alGames;
	public List<Integer> alMaps;
	public List<DMPhoneState> dmPhoneStates = new ArrayList<DMPhoneState>();
	
	public DMApp(String deviceId) {
		dmPhone = new DMPhone();
		dmMap = new DMMap();
		
		dmPhone.phoneToken = "a_" + deviceId;
		
	}
	
	public void net(DMAPI api) throws JSONException{
		JSONObject json = DMNet.callapi(api,dmPhone.getJSONFor(api));
		if(json!=null){
			switch(api){
			case update:
				update(json);
				break;
			case find_games:
				find_games(json);
				break;
			case find_maps:
				find_maps(json);
				break;
			case new_game:
				new_game(json);
				break;
			case join_game:
				join_game(json);
				break;
			case update_phone_settings:
				update_phone_settings(json);
				break;
			}
		}else{
			Log.i("net","json == null, network problem?");
		}
	}

	// API invoke
	public void update_phone_settings(JSONObject json) throws JSONException{
		dmPhone.phone = json.getInt("phone");
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
		JSONObject mapInfo = json.getJSONObject("mapInfo");
		dmMap.dotPoints = DMUtils.JSONArray2GeoPoints(mapInfo.getJSONArray("dotPoints"));
		dmMap.basePoints = DMUtils.JSONArray2GeoPoints(mapInfo.getJSONArray("basePoints"));
		dmMap.powerPelletPoints = DMUtils.JSONArray2GeoPoints(mapInfo.getJSONArray("powerPelletPoints"));
		dmMap.buildPoints();
	}
	
	public void new_game(JSONObject json) throws JSONException{
		dmPhone.game = json.getInt("game");
		initGame(json);
	}
	
	public void join_game(JSONObject json) throws JSONException{
		initGame(json);
	}
	
	public void update(JSONObject json) throws JSONException{
		dmPhone.powerMode = json.getBoolean("powerMode");
		updatePhoneStates(json.getJSONArray("phoneStates"));
		updateEvents(json.getJSONArray("events"));
	}
	
	// Events	
	public void updatePhoneStates(JSONArray jsonArray) throws JSONException {
		synchronized (dmPhoneStates) {
			dmPhoneStates.clear();		
			for(int i=0;i<jsonArray.length();i++){
				JSONObject json = jsonArray.getJSONObject(i);
				DMPhoneState dmPhoneState = new DMPhoneState();
				dmPhoneState.phone = json.getInt("phone");
				dmPhoneState.lat = new Double(json.getString("lat"));
				dmPhoneState.lng = new Double(json.getString("lng"));
				dmPhoneState.idle = json.getInt("idle");
				dmPhoneState.alive = json.getBoolean("alive");
				dmPhoneStates.add(dmPhoneState);
			}
		}
	}
	public void updateEvents(JSONArray jsonArray) throws JSONException {
		for(int i=0;i<jsonArray.length();i++){
			JSONObject json = jsonArray.getJSONObject(i);
			switch(json.getInt("type")){
				case DMConstants.OHHAI_EVENT:
					int phone = json.getInt("phone");
					String name = json.getString("name");
					break;
				case DMConstants.PHONE_EATEN_EVENT:
				    int eater = json.getInt("eater");
				    int eatee = json.getInt("eatee");
				    
					break;
				case DMConstants.ITEM_EATEN_EVENT:
					JSONArray kArray = json.getJSONArray("k");
					String kType = kArray.getString(0);
					int x = (int) (new Double(kArray.getString(1)) * 1E6);
					int y = (int) (new Double(kArray.getString(2)) * 1E6);
					if(kType == "p"){
						dmMap.killPowerPellet(x,y);
						dmPhone.powerMode = true;
					}
					if(kType == "d"){
						dmMap.killDot(x,y);
					}
					break;
				case DMConstants.GAME_OVER:
					int reason = json.getInt("reason");
					if(reason == DMConstants.GAMEOVER_PACMAN_WINS){
						
					}
					if(reason == DMConstants.GAMEOVER_PACMAN_LOSES){
						
					}
			}
			dmPhone.id__gte = json.getInt("i");
			int t = json.getInt("t");
		}
		
	}

}
