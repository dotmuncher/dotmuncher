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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
   
	public void test_update_phone_settings() {
		try {
			dmApp.update_phone_settings();
			Log.i("update_phone_settings", ""+dmApp.dmPhone.phoneId);
			assertTrue(dmApp.dmPhone.phoneId == 15);
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void test_find_games() {
		try {
			dmApp.find_games();
			Log.i("test_find_games", ""+dmApp.al_games.size());
			assertTrue(dmApp.al_games.size()>0);
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}		
	}
	
	public void test_find_maps() {
		try {
			dmApp.find_maps();
			Log.i("test_find_maps", ""+dmApp.al_maps.size());
			assertTrue(dmApp.al_maps.size()>0);
		} catch ( Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}	
	}
	
	public void test_new_game() {
		try {
			dmApp.new_game();
			Log.i("test_new_game", "");
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void test_join_game() {
		try {
			dmApp.join_game();
			Log.i("test_join_game", "");
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
}
