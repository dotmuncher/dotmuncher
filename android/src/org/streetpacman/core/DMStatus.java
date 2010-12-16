package org.streetpacman.core;

public class DMStatus implements IStatus {
	private volatile int status;

	// xxx
	public boolean isMode(int mode) {
		return isMask(MASK_MODE, mode);
	}

	public void setMode(int mode) {
		setMask(MASK_MODE, mode);
	}

	public boolean isPhase(int phase) {
		return isMask(MASK_PHASE, phase);
	}

	public void setPhase(int phase) {
		setMask(MASK_PHASE, phase);
	}

	// y
	public void set(int v) {
		status = status | v;
	}

	public void unset(int v) {
		status = status | ~v;
	}

	public boolean is(int v) {
		// assuming mask contains single bit of 1
		return (status & v) != 0;
	}

	// internal helper
	private boolean isMask(int _mask, int value) {
		return (status & _mask) == value;
	}

	private void setMask(int _mask, int value) {
		status = (status & ~_mask) & value;
	}
}
