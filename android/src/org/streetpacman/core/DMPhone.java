package org.streetpacman.core;

import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;
import android.util.Log;

public class DMPhone {
	public String gameToken = "";
	public int game = -1;
	public int map = -1;
	public int phone = -1;
	public String name = "e";
	public String phoneToken = "";
	public double lat = -1;
	public double lng = -1;
	public float acc = -1;
	public int id__gte = -1;
	public boolean powerMode = false;

	public JSONObject getJSONFor(int api) {
		JSONObject json = new JSONObject();
		try {
			switch (api) {
			case DMConstants.update_phone_settings:
				json.put("phoneToken", phoneToken);
				json.put("name", name);
				break;
			case DMConstants.find_games:
				json.put("lat", Double.toString(lat));
				json.put("lng", Double.toString(lng));
				json.put("phoneToken", phoneToken);
				break;
			case DMConstants.find_maps:
				json.put("lat", Double.toString(lat));
				json.put("lng", Double.toString(lng));
				json.put("phoneToken", phoneToken);
				break;
			case DMConstants.new_game:
				json.put("map", map);
				json.put("phone", phone);
				break;
			case DMConstants.join_game:
				json.put("game", game);
				json.put("phone", phone);
				break;
			case DMConstants.update:
				json.put("lat", Double.toString(lat));
				json.put("lng", Double.toString(lng));
				json.put("hacc", Double.toString(acc));
				json.put("vacc", Double.toString(acc));
				json.put("game", game);
				json.put("phone", phone);
				json.put("id__gte", id__gte);
				break;
			default:
				json = null;
			}
		} catch (JSONException e) {
			json = null;
		}
		return json;
	}

	public JSONObject getJSONFor_update_phone_settings() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("phoneToken", phoneToken);
		json.put("name", name);
		return json;
	}

	public JSONObject getJSONFor_find() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("lat", Double.toString(lat));
		json.put("lng", Double.toString(lng));
		json.put("phoneToken", phoneToken);
		return json;
	}

	public JSONObject getJSONFor_update() throws JSONException {
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

	public JSONObject getJSONFor_new_game() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("map", map);
		json.put("phone", phone);
		return json;
	}

	public JSONObject getJSONFor_join_game() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("game", game);
		json.put("phone", phone);
		return json;
	}

	public void setLocation(Location location) {
		lat = location.getLatitude();
		lng = location.getLongitude();
		acc = location.getAccuracy();
	}
}