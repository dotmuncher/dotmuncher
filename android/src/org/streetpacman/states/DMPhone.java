package org.streetpacman.states;

import org.json.JSONException;
import org.json.JSONObject;

public class DMPhone {
	public int game = -1;
    public int map = -1;
    public int phoneId = -1;
    public String name = "";
	public String phoneToken = "";
	public double lat = -1;
	public double lng = -1;
	public float acc = -1;
	public int id__gte = -1;
	public Boolean powerMode = false;
	
	public JSONObject getJSONFor_update_phone_settings() throws JSONException{
		JSONObject json = new JSONObject();
		json.put("phoneToken", phoneToken);
		json.put("name", name);
		return json;
	}
	
	public JSONObject getJSONFor_find() throws JSONException{
		JSONObject json = new JSONObject();
		json.put("lat", Double.toString(lat));
		json.put("lng", Double.toString(lng));
		json.put("phoneToken", phoneToken);
		return json;
	}

	public JSONObject getJSONFor_update() throws JSONException{
		JSONObject json = new JSONObject();
		json.put("lat", Double.toString(lat));
		json.put("lng", Double.toString(lng));
		json.put("hacc", Double.toString(acc));
		json.put("vacc", Double.toString(acc));
		json.put("game", game);
		json.put("phoneId", phoneId);
		json.put("id__gte", id__gte);
		return json;
	}
    public JSONObject getJSONFor_new_game() throws JSONException{
    	JSONObject json = new JSONObject();
    	json.put("map", map);
    	json.put("phone", phoneId);
		return json;    	
    }
    
    public JSONObject getJSONFor_join_game() throws JSONException{
    	JSONObject json = new JSONObject();
    	json.put("game", game);
    	json.put("phone", phoneId);
		return json;    	
    }
}            