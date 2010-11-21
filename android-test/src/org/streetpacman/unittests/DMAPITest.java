package org.streetpacman.unittests;

import org.json.JSONObject;
import org.streetpacman.controler.DMAPI;
import org.streetpacman.controler.DMApp;

import junit.framework.TestCase;

public class DMAPITest extends TestCase {
	JSONObject json;
	DMApp dmApp;
    
	protected void setUp() {
		dmApp = new DMApp("test_dmApp");
		
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
	
	public void test_run(){
		try {
			dmApp.net(DMAPI.find_maps);
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}		
	}
}
