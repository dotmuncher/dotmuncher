package com.dotmuncher.android.controler;

import java.util.UUID;

import android.content.Context;
import android.telephony.TelephonyManager;

public class DMAppControler {
	private String phoneToken;
	
	public DMAppControler(String deviceId) {
		phoneToken = "a_" + deviceId;
	}	

}
