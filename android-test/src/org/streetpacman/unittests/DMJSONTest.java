package org.streetpacman.unittests;

import org.json.JSONException;
import org.json.JSONObject;
import org.streetpacman.controler.DMApp;
import android.util.Log;

import junit.framework.TestCase;

public class DMJSONTest extends TestCase {
	JSONObject json;
	DMApp dmApp;
    
	protected void setUp() {
		dmApp = new DMApp("test_dmApp");
		try {
			dmApp.update_phone_settings();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
   
	public void test_update_phone_settings() {
		try {
			dmApp.update_phone_settings();
			Log.i("update_phone_settings", ""+dmApp.dmPhone.phoneId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(dmApp.dmPhone.phoneId == 15);
	}
	
	public void test_find_games() {
		try {
			dmApp.find_games();
			Log.i("test_find_games", ""+dmApp.al_games.size());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void test_find_maps() {
		try {
			dmApp.find_maps();
			Log.i("test_find_maps", ""+dmApp.al_maps.size());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
