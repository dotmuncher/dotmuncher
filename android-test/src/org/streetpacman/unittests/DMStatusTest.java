package org.streetpacman.unittests;

import org.streetpacman.core.DMStatus;
import org.streetpacman.core.IStatus;

import junit.framework.TestCase;

public class DMStatusTest extends TestCase {
	public void test() {
		IStatus s = new DMStatus(0);
		
		s.set(IStatus.VISIBLE_HIDE_GLOBAL);
		assertTrue(s.is(IStatus.VISIBLE_HIDE_GLOBAL));
		
		s.unset(IStatus.VISIBLE_HIDE_GLOBAL);
		assertFalse(s.is(IStatus.VISIBLE_HIDE_GLOBAL));
		
		s.setMode(IStatus.MODE_ERROR);
		assertTrue(s.isMode(IStatus.MODE_ERROR));
		
		s.setPhase(IStatus.PHASE_PLAYING);
		assertTrue(s.isPhase(IStatus.PHASE_PLAYING));
	}
}
