package org.streetpacman.states;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DMGame {
	public int game = -1;
	public List<DMPhoneState> dmPhoneStates = new ArrayList<DMPhoneState>();
	public DMMap dmMap;
	
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
				case DMConstants.ITEM_EATEN_EVENT:
				    int eater = json.getInt("eater");
				    int eatee = json.getInt("eatee");
					break;
				case DMConstants.PHONE_EATEN_EVENT:
					JSONArray kArray = json.getJSONArray("k");
					String kType = kArray.getString(0);
					int x = (int) (new Double(kArray.getString(1)) * 1E6);
					int y = (int) (new Double(kArray.getString(2)) * 1E6);
					if(kType == "p"){
						dmMap.killPowerPellet(x,y);
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
					break;
				default:
			}
		}
		
	}
	
	
}
