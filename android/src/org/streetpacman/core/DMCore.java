package org.streetpacman.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.streetpacman.DMStreetPacman;
import org.streetpacman.util.DMUtils;

import android.os.Handler;
import android.util.Log;

public final class DMCore {
	public  static  DMCore core;
	final Handler mHandler = new Handler();
	public DMMap dmMap;
	public List<Integer> alGames;
	public List<Integer> alMaps;
	public List<DMPhoneState> dmPhoneStates = new ArrayList<DMPhoneState>();
	public Map<Integer, DMPhoneState> dmPhoneStatesMap = new HashMap<Integer, DMPhoneState>();
	
	public final DMPhone myPhone;
	public DMPhoneState myPhoneState;
	public volatile int myPhoneIndex = 0; // in dmPhoneStates

	public DMCore(String deviceId, DMStreetPacman ui) {
		myPhone = new DMPhone();
		dmMap = new DMMap();
		myPhone.phoneToken = "a_" + deviceId;
		this.core = this;
	}
	
	public static DMCore self(){
		return core;
	}

	public void net(final int api, final Runnable rTrue, final Runnable rFalse) {
		Thread t = new Thread() {
			public void run() {
				final JSONObject json = DMNet
						.call(api, myPhone.getJSONFor(api));
				mHandler.post(new Runnable() {
					public void run() {
						try {
							if (json == null) {
								Log.i(DMConstants.TAG,
										"net json == null, network problem? api "
												+ api);
								rFalse.run();
							} else {
								switch (api) {
								case DMConstants.update:
									update(json);
									break;
								case DMConstants.find_games:
									find_games(json);
									break;
								case DMConstants.find_maps:
									find_maps(json);
									break;
								case DMConstants.new_game:
									new_game(json);
									break;
								case DMConstants.join_game:
									join_game(json);
									break;
								case DMConstants.update_phone_settings:
									update_phone_settings(json);
									break;
								}
								rTrue.run();
							}
						} catch (JSONException e) {
							Log.i(DMConstants.TAG,
									"net json!=null, JSONException api " + api);
							e.printStackTrace();
							rFalse.run();
						}
					}
				});
			}
		};
		t.start();
	}

	// post API invoke before rTrue
	public void update_phone_settings(JSONObject json) throws JSONException {
		myPhone.phone = json.getInt("phone");
	}

	public void find_games(JSONObject json) throws JSONException {
		JSONArray jsonArray = json.getJSONArray("items");
		alGames = new ArrayList<Integer>();
		for (int i = 0; i < jsonArray.length(); i++) {
			alGames.add(jsonArray.getJSONObject(i).getInt("id"));
		}
	}

	public void find_maps(JSONObject json) throws JSONException {
		JSONArray jsonArray = json.getJSONArray("items");
		alMaps = new ArrayList<Integer>();
		for (int i = 0; i < jsonArray.length(); i++) {
			alMaps.add(jsonArray.getJSONObject(i).getInt("id"));
		}
	}

	private void initGame(JSONObject json) throws JSONException {
		JSONObject mapInfo = json.getJSONObject("mapInfo");
		dmMap.dotPoints = DMUtils.JSONArray2GeoPoints(mapInfo
				.getJSONArray("dotPoints"));
		dmMap.basePoints = DMUtils.JSONArray2GeoPoints(mapInfo
				.getJSONArray("basePoints"));
		dmMap.powerPelletPoints = DMUtils.JSONArray2GeoPoints(mapInfo
				.getJSONArray("powerPelletPoints"));
		dmMap.buildPoints();
	}

	public void new_game(JSONObject json) throws JSONException {
		myPhone.game = json.getInt("game");
		myPhone.gameToken = json.getString("gameToken");
		initGame(json);
	}

	public void join_game(JSONObject json) throws JSONException {
		initGame(json);
	}

	public void update(JSONObject json) throws JSONException {
		myPhoneState.powerMode = json.getBoolean("powerMode");
		updatePhoneStates(json.getJSONArray("phoneStates"));
		updateEvents(json.getJSONArray("events"));
	}

	// Events
	public void updatePhoneStates(JSONArray jsonArray) throws JSONException {
		synchronized (dmPhoneStates) {
			dmPhoneStates.clear();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject json = jsonArray.getJSONObject(i);
				DMPhoneState dmPhoneState = new DMPhoneState();
				dmPhoneState.phone = json.getInt("phone");
				dmPhoneState.lat = new Double(json.getString("lat"));
				dmPhoneState.lng = new Double(json.getString("lng"));
				dmPhoneState.idle = json.getInt("idle");
				dmPhoneState.alive = json.getBoolean("alive");
				dmPhoneStates.add(dmPhoneState);
				if(dmPhoneState.phone == myPhone.phone){
					myPhoneIndex = i;
				}
				// dmPhoneStatesMap
				dmPhoneStatesMap.put(dmPhoneState.phone, dmPhoneState);
			}
		}
	}

	public void updateEvents(JSONArray jsonArray) throws JSONException {
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject json = jsonArray.getJSONObject(i);
			switch (json.getInt("type")) {
			case DMConstants.OHHAI_EVENT:
				int phone = json.getInt("phone");
				String name = json.getString("name");
				break;
			case DMConstants.PHONE_EATEN_EVENT:
				int eater = json.getInt("eater");
				int eatee = json.getInt("eatee");
				killPhone(eatee);
				break;
			case DMConstants.ITEM_EATEN_EVENT:
				JSONArray kArray = json.getJSONArray("k");
				String kType = kArray.getString(0);
				int x = (int) (new Double(kArray.getString(1)) * 1E6);
				int y = (int) (new Double(kArray.getString(2)) * 1E6);
				if (kType == "p") {
					dmMap.killPowerPellet(x, y);
					myPhoneState.powerMode = true;
				}
				if (kType == "d") {
					dmMap.killDot(x, y);
				}
				break;
			case DMConstants.GAME_OVER:
				int reason = json.getInt("reason");
				if (reason == DMConstants.GAMEOVER_PACMAN_WINS) {

				}
				if (reason == DMConstants.GAMEOVER_PACMAN_LOSES) {

				}
			}
			myPhone.id__gte = json.getInt("i");
			int t = json.getInt("t");
		}

	}

	private void killPhone(int phone) {
		dmPhoneStatesMap.get(phone).status = DMConstants.PHONE_KILLED;
		
	}

}
