package org.streetpacman.states;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.maps.GeoPoint;

public class DMPhone {
	public int phone;
	public String phoneToken;
	public double lat;
	public double lng;
	public float acc;
	public int game;
	public int id__gte;
	
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
		json.put("phone", phone);
		json.put("id__gte", id__gte);
		return json;
	}
}
