package org.streetpacman.unittests;

import junit.framework.TestCase;

public class DMJSONTest extends TestCase {
    String res_new_game = "{\"game\":1,\"mapInfo\": {\"dotPoints\": [[\"11.11\",\"22.22\"],[\"33.33\",\"44.44\"]],\"basePoints\": [],\"powerPelletPoints\": []}}";
    protected double fValue2;

   protected void setUp() {
        fValue1= 2.0;
        fValue2= 3.0;
    }
   
   public void testAdd() {
       double result= fValue1 + fValue2;
       assertTrue(result == 5.0);
   }
}
