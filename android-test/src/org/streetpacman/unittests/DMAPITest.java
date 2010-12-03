package org.streetpacman.unittests;

import org.json.JSONObject;
import org.streetpacman.core.DMCore;
import org.streetpacman.core.DMConstants;

import android.util.Log;

import junit.framework.TestCase;

public class DMConstantsTest extends TestCase {
	JSONObject json;
	DMCore dmApp;
    
	protected void setUp() {
		dmApp = new DMCore("test_dmApp");
		
	}
   
	public void test_update(){
		// new_game
		try {
			dmApp.myPhone.phone = 5;
			dmApp.myPhone.map = 3;
			dmApp.net(DMConstants.new_game);			
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertTrue(dmApp.dmMap.dotPoints.size() > 0);
		// join_game
		try {
			Log.i("DM", "update dmApp.dmPhone.game" + Integer.toString(dmApp.myPhone.game));
			dmApp.net(DMConstants.join_game);			
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertTrue(dmApp.dmMap.dotPoints.size() > 0);
		// update
		try {
			dmApp.myPhone.id__gte = 0;
			dmApp.myPhone.acc = 10;
			dmApp.myPhone.lat = 40.11111;
			dmApp.myPhone.lng = 15.11111;
			dmApp.net(DMConstants.update);			
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertTrue(dmApp.dmPhoneStates.size() > 0);
	}
	
	public void test_join_game(){
		// new_game
		try {
			dmApp.myPhone.phone = 5;
			dmApp.myPhone.map = 3;
			dmApp.net(DMConstants.new_game);			
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertTrue(dmApp.dmMap.dotPoints.size() > 0);
		// join_game
		try {
			Log.i("DM", "dmApp.dmPhone.game" + Integer.toString(dmApp.myPhone.game));
			dmApp.net(DMConstants.join_game);			
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertTrue(dmApp.dmMap.dotPoints.size() > 0);
	}
	
	public void test_new_game(){
		try {
			dmApp.myPhone.phone = 5;
			dmApp.myPhone.map = 3;
			dmApp.net(DMConstants.new_game);			
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertTrue(dmApp.dmMap.dotPoints.size() > 0);
	}
	
	public void test_find_games(){
		try {
			dmApp.net(DMConstants.find_games);			
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertTrue(dmApp.alGames.size() > 0);
	}
	
	public void test_find_maps(){
		try {
			dmApp.net(DMConstants.find_maps);			
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertTrue(dmApp.alMaps.size() > 0);
	}
	
	public void test_getJSONFor() {
		try {
			for(int api = 0; api < 7; api ++){
				dmApp.myPhone.getJSONFor(api);
			}
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void test_update_phone_settings(){
		try {
			dmApp.net(DMConstants.update_phone_settings);			
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertTrue(dmApp.myPhone.phone > 0); // -1
	}
}
