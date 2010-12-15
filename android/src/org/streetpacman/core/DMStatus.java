package org.streetpacman.core;

public class DMStatus implements IStatus {
	private volatile int status;
	
	// xxx
	public boolean isMode(int mode) {
		return (status & 0x0000000F) == mode;
	}
	
	public void setMode(int mode) {
		status = (status & 0xFFFFFFF0) & mode;
	}
	
	public boolean isPhase(int phase) {
		return (status & 0x000000F0) == phase;
	}

	public void setPhase(int phase) {
		status = (status & 0xFFFFF0F) & phase;
	}
	
	// y
	public void set(int mask) {
		status = status | mask;
	}

	public void unset(int mask) {
		status = status | ~mask;
	}

	public boolean is(int mask) {
		// assuming mask contains single bit of 1
		return (status & mask) != 0;
	}

}
