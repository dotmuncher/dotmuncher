package com.dotmuncher.unittests.tests;

import com.dotmuncher.unittests.SimpleFooClass;

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
