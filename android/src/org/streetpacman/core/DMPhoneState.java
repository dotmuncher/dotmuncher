package org.streetpacman.core;

public class DMPhoneState {
    public int phone = -1;
    public double lat = -1;
	public double lng = -1;
    public int idle = -1;
    
    public volatile boolean powerMode = false;
	public volatile int status = DMConstants.PHONE_INIT;
	public volatile int role = DMConstants.ROLE_INIT;
	public boolean alive = true;
}
