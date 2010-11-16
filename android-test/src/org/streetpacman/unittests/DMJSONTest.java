package org.streetpacman.unittests;

import junit.framework.TestCase;

public class DMJSONTest extends TestCase {
    String res_new_game = "{\"game\":1,\"mapInfo\": {\"dotPoints\": [[\"11.11\",\"22.22\"],[\"33.33\",\"44.44\"]],\"basePoints\": [],\"powerPelletPoints\": []}}";
    
    String res_find_maps = "{\"items\": [{\"id\": 3}, {\"id\": 2}, {\"id\": 1}], \"phoneId\": 11}";

   protected void setUp() {

    }
   
   public void testAdd() {
       
       //assertTrue(result == 5.0);
   }
}
