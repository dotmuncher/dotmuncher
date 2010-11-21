package org.streetpacman.unittests;

import org.json.JSONObject;
import org.streetpacman.controler.DMAPI;
import org.streetpacman.controler.DMApp;

import android.util.Log;

import junit.framework.TestCase;

public class DMAPITest extends TestCase {
	JSONObject json;
	DMApp dmApp;
    
	protected void setUp() {
		dmApp = new DMApp("test_dmApp");
		
	}
   
	public void test_update(){
		// new_game
		try {
			dmApp.dmPhone.phone = 5;
			dmApp.dmPhone.map = 3;
			dmApp.net(DMAPI.new_game);			
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertTrue(dmApp.dmMap.dotPoints.size() > 0);
		// join_game
		try {
			Log.i("DM", "update dmApp.dmPhone.game" + Integer.toString(dmApp.dmPhone.game));
			dmApp.net(DMAPI.join_game);			
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertTrue(dmApp.dmMap.dotPoints.size() > 0);
		// update
		try {
			dmApp.dmPhone.id__gte = 0;
			dmApp.dmPhone.acc = 10;
			dmApp.dmPhone.lat = 40.11111;
			dmApp.dmPhone.lng = 15.11111;
			dmApp.net(DMAPI.update);			
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertTrue(dmApp.dmPhoneStates.size() > 0);
	}
	
	public void test_join_game(){
		// new_game
		try {
			dmApp.dmPhone.phone = 5;
			dmApp.dmPhone.map = 3;
			dmApp.net(DMAPI.new_game);			
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertTrue(dmApp.dmMap.dotPoints.size() > 0);
		// join_game
		try {
			Log.i("DM", "dmApp.dmPhone.game" + Integer.toString(dmApp.dmPhone.game));
			dmApp.net(DMAPI.join_game);			
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertTrue(dmApp.dmMap.dotPoints.size() > 0);
	}
	
	public void test_new_game(){
		try {
			dmApp.dmPhone.phone = 5;
			dmApp.dmPhone.map = 3;
			dmApp.net(DMAPI.new_game);			
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertTrue(dmApp.dmMap.dotPoints.size() > 0);
	}
	
	public void test_find_games(){
		try {
			dmApp.net(DMAPI.find_games);			
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertTrue(dmApp.alGames.size() > 0);
	}
	
	public void test_find_maps(){
		try {
			dmApp.net(DMAPI.find_maps);			
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertTrue(dmApp.alMaps.size() > 0);
	}
	
	public void test_getJSONFor() {
		try {
			for(DMAPI api : DMAPI.values()){
				dmApp.dmPhone.getJSONFor(api);
			}
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void test_update_phone_settings(){
		try {
			dmApp.net(DMAPI.update_phone_settings);			
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertTrue(dmApp.dmPhone.phone > 0); // -1
	}
}
