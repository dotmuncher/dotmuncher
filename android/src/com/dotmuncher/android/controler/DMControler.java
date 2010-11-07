package com.dotmuncher.android.controler;

import java.util.UUID;

import android.content.Context;
import android.telephony.TelephonyManager;

public class DMControler {
	private String phoneToken;
	
	public DMControler(String deviceId) {
		phoneToken = "a_" + deviceId;
	}	

}
