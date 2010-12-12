package org.streetpacman.core;

public class DMStatus {
	private volatile int status;

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

	// xxx - dependent group
	// y - independent bit
	// R - reserved
	//
	// mode group
	// 0000 0000 0000 0000 0000 0000 0000 Rxxx
	public static final int MODE_SOLO = 0x00000000; // 0
	public static final int MODE_GAME = 0x00000001; // 1
	public static final int MODE_MACRO = 0x00000002; // 2
	public static final int MODE_OBSERVER = 0x00000003; // 3
	public static final int MODE_ERROR = 0x00000007; // 7

	public boolean isMode(int mode) {
		return getMode() == mode;
	}

	public int getMode() {
		return status & 0x0000000F;
	}

	public void setMode(int mode) {
		status = status & 0xFFFFFFF0 & mode;
	}

	// visibility
	// 0000 0000 0000 0000 0000 0000 yyyy 0000
	public static final int VISIBLE_TO_PEER = 0x00000010; // 16
	public static final int VISIBLE_TO_ADVERSARY = 0x00000020; // 32
	public static final int VISIBLE_TO_OBSERVER = 0x00000040;// 64
	public static final int HIDE_GLOBAL = 0x00000080;// 128

	// game progression related
	// 0000 0000 0000 0000 0000 yyyy 0000 0000
	public static final int ZONE_SAFE = 0x00000100;// 256
	public static final int STAGE_POWER = 0x00000200;// 512
	public static final int ALIVE = 0x00000400;// 1024
	public static final int SENSIBLE_TO_ITEM = 0x00000800;// 2048

	// phase
	// 0000 0000 0000 0000 00xx 0000 0000 0000
	public static final int READY = 0x00000000; // 0
	public static final int PLAYING = 0x00001000;// 4096
	public static final int END = 0x00002000; // 8192

	public boolean isPhase(int phase) {
		return getPhase() == phase;
	}

	public int getPhase() {
		return status & 0xFFFF0FFF;
	}

	public void setPhase(int phase) {
		status = status & 0xFFFF0FFF & phase;
	}
}
