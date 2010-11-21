/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.streetpacman;

import java.util.List;
import java.util.UUID;

import org.json.JSONException;
import org.streetpacman.R;
import org.streetpacman.controler.DMAPI;
import org.streetpacman.controler.DMApp;
import org.streetpacman.states.DMConstants;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

public class DMStreetPacman extends MapActivity {
	private DMApp dmApp;
	private DMOverlay dmOverlay;
	private Location currentLocation;
	private LocationManager locationManager;
    MapView mapView; 
    MapController mc;
    GeoPoint p;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.d(DMConstants.TAG, "DMStreetPacman.onCreate");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
		// http://stackoverflow.com/questions/2785485/is-there-a-unique-android-device-id		
	    final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
	
	    final String tmDevice, tmSerial, tmPhone, androidId;
	    tmDevice = "" + tm.getDeviceId();
	    tmSerial = "" + tm.getSimSerialNumber();
	    androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
	
	    UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
	    String deviceId = deviceUuid.toString();
	    
	    this.dmApp = new DMApp(deviceId);
	    
		try {
			dmApp.net(DMAPI.update_phone_settings);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        this.dmOverlay = new DMOverlay(dmApp);
        
        mapView = new MapView(this, "MapViewCompassDemo_DummyAPIKey");
        //setContentView(R.layout.mapview);
        setContentView(mapView);
        

        
        String coordinates[] = {"1.352566007", "103.78921587"};
        double lat = Double.parseDouble(coordinates[0]);
        double lng = Double.parseDouble(coordinates[1]);
 
        p = new GeoPoint(
            (int) (lat * 1E6), 
            (int) (lng * 1E6));

        mc = mapView.getController();

        mc.animateTo(p);
        mc.setZoom(17); 
 

        List<Overlay> listOfOverlays = mapView.getOverlays();
        listOfOverlays.clear();
        listOfOverlays.add(dmOverlay);        
 
        mapView.invalidate();

    }

    @Override
    protected boolean isRouteDisplayed() { return false; }
    @Override
    protected boolean isLocationDisplayed() { return true; }
    
    private final LocationListener locationListener = new LocationListener(){
        @Override
        public void onLocationChanged(Location loc) {        	
            if (loc != null) {
                Toast.makeText(getBaseContext(), 
                    "Location changed : Lat: " + loc.getLatitude() + 
                    " Lng: " + loc.getLongitude(), 
                    Toast.LENGTH_SHORT).show();
                try {
					dmApp.net(DMAPI.update);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
    };
    

    /**
     * Unregisters all location and sensor listeners
     */
    void unregisterLocationAndSensorListeners() {
      if (locationManager != null) {
        Log.d(DMConstants.TAG,
            "DM: Now unregistering location listeners.");
        locationManager.removeUpdates(locationListener);
      }
      /*
      if (sensorManager != null) {
        Log.d(DMConstants.TAG,
        sensorManager.unregisterListener(sensorListener);
      }
      */
    }
}
