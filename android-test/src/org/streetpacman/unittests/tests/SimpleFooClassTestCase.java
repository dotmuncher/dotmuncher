package org.streetpacman.unittests.tests;

import org.streetpacman.unittests.SimpleFooClass;

import junit.framework.Assert;
import junit.framework.TestCase;

public class SimpleFooClassTestCase extends TestCase {
	public void setUp() {
		//do whatever setup you need here for all the tests
	}
	
	public void testGetBar() {
		Assert.assertEquals("bar", SimpleFooClass.getBar());
	}
}
