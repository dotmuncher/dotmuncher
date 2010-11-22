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
import org.streetpacman.controler.DMAPI;
import org.streetpacman.controler.DMApp;
import org.streetpacman.states.DMConstants;
import org.streetpacman.util.DMUtils;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.content.Context;
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
	private boolean keepMyLocationVisible;
    MapView mapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.d(DMConstants.TAG, "DMStreetPacman.onCreate");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mapview);
        mapView = (MapView) findViewById(R.id.map);
        
		// http://stackoverflow.com/questions/2785485/is-there-a-unique-android-device-id		
	    final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
	
	    final String tmDevice, tmSerial, tmPhone, androidId;
	    tmDevice = "" + tm.getDeviceId();
	    tmSerial = "" + tm.getSimSerialNumber();
	    androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
	
	    UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
	    String deviceId = deviceUuid.toString();
	    
	    this.dmApp = new DMApp(deviceId);
        
        this.dmOverlay = new DMOverlay(dmApp,this);        

        List<Overlay> listOfOverlays = mapView.getOverlays();
        listOfOverlays.clear();
        listOfOverlays.add(dmOverlay);        
 
        locationManager =
            (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mapView.invalidate();

    }

    @Override
    protected boolean isRouteDisplayed() { return false; }
    @Override
    protected boolean isLocationDisplayed() { return true; }
    
    @Override
    protected void onPause() {
      // Called when activity is going into the background, but has not (yet) been
      // killed. Shouldn't block longer than approx. 2 seconds.
      Log.d(DMConstants.TAG, "DM.onPause");
      unregisterLocationAndSensorListeners();
      super.onPause();
    }
    
    @Override
    protected void onResume() {
      // Called when the current activity is being displayed or re-displayed
      // to the user.
      Log.d(DMConstants.TAG, "DM.onResume");
      super.onResume();

      registerLocationAndSensorListeners();

		try {
			dmApp.net(DMAPI.update_phone_settings);
			dmApp.net(DMAPI.find_games);
			dmApp.dmPhone.game = dmApp.alGames.get(0);
			dmApp.net(DMAPI.join_game);			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		// zoom and pan
		showPoints();
    }

    /**
     * Registers to receive location updates from the GPS location provider and
     * sensor updated from the compass.
     */
    void registerLocationAndSensorListeners() {
      if (locationManager != null) {
          locationManager.requestLocationUpdates(
                  LocationManager.GPS_PROVIDER, 
                  0, 
                  0, 
                  locationListener);
        try {
          locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
              1000 * 60 * 5 /*minTime*/, 0 /*minDist*/, locationListener);
        } catch (RuntimeException e) {
          // If anything at all goes wrong with getting a cell location do not
          // abort. Cell location is not essential to this app.
          Log.w(DMConstants.TAG,
              "Could not register network location listener.");
        }
      }
    }
    
    public void alert(String txt) {
	    Toast.makeText(this, txt, Toast.LENGTH_LONG).show();
	  }

    /**
     * Moves the location pointer to the current location and center the map if
     * the current location is outside the visible area.
     */
    private void showCurrentLocation() {
	  if (currentLocation == null || dmOverlay == null || mapView == null) {
	    return;
	  }
	  dmOverlay.setMyLocation(currentLocation);
	  mapView.invalidate();
	  /*
		MapController controller = mapView.getController();
		GeoPoint geoPoint = DMUtils.getGeoPoint(currentLocation);
		controller.animateTo(geoPoint);
		*/
    }
    
    private final LocationListener locationListener = new LocationListener(){
        @Override
        public void onLocationChanged(Location location) {
            currentLocation = location;

            showCurrentLocation();
            
            if (location != null) {
                Toast.makeText(getBaseContext(), 
                    "Location changed : Lat: " + location.getLatitude() + 
                    " Lng: " + location.getLongitude(), 
                    Toast.LENGTH_SHORT).show();
                try {
                	dmApp.dmPhone.setLocation(location);
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
    
    public void showPoints() {
        if (mapView == null) {
          return;
        }

        int bottom = dmApp.dmMap.getBottom();
        int left = dmApp.dmMap.getLeft();
        int latSpanE6 = dmApp.dmMap.getTop() - bottom;
        int lonSpanE6 = dmApp.dmMap.getRight() - left;
        if (latSpanE6 > 0
            && latSpanE6 < 180E6
            && lonSpanE6 > 0
            && lonSpanE6 < 360E6) {
          keepMyLocationVisible = false;
          GeoPoint center = new GeoPoint(
              bottom + latSpanE6 / 2,
              left + lonSpanE6 / 2);
          if (DMUtils.isValidGeoPoint(center)) {
            mapView.getController().setCenter(center);
            mapView.getController().zoomToSpan(latSpanE6, lonSpanE6);
          }
        }
    }
}
