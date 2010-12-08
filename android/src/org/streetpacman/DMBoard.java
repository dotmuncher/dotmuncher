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
import org.streetpacman.core.DMCore;
import org.streetpacman.core.DMConstants;
import org.streetpacman.util.DMUtils;
import org.streetpacman.util.GeoRect;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup.LayoutParams;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

public class DMBoard extends MapActivity implements View.OnTouchListener,
		View.OnClickListener {
	private static DMBoard instance;
	private DMOverlay dmOverlay;
	private Location currentLocation;
	private LocationManager locationManager;
	private SensorManager sensorManager;
	private boolean keepMyLocationVisible;
	MapView mapView;
	TextView noteView, menuView;

	TextView v1, v2, v3, v4, v5, v6;

	private GLSurfaceView mGLSurfaceView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		DMBoard.instance = this;
		Log.d(DMConstants.TAG, "DMBoard.onCreate");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mapview);
		mapView = (MapView) findViewById(R.id.map);
		noteView = (TextView) findViewById(R.id.note);
		noteView.setOnClickListener(this);

		menuView = (TextView) findViewById(R.id.menu);
		menuView.setOnClickListener(this);

		v1 = (TextView) findViewById(R.id.v1);
		v1.setOnClickListener(this);
		v2 = (TextView) findViewById(R.id.v2);
		v2.setOnClickListener(this);
		v3 = (TextView) findViewById(R.id.v3);
		v3.setOnClickListener(this);
		v4 = (TextView) findViewById(R.id.v4);
		v4.setOnClickListener(this);
		v5 = (TextView) findViewById(R.id.v5);
		v5.setOnClickListener(this);
		v6 = (TextView) findViewById(R.id.v6);
		v6.setOnClickListener(this);
		toggle();

		this.dmOverlay = new DMOverlay(this);

		List<Overlay> listOfOverlays = mapView.getOverlays();
		listOfOverlays.clear();
		listOfOverlays.add(dmOverlay);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		mapView.setBuiltInZoomControls(true);

		// api update_phone_settings
		DMCore.self().net(DMConstants.update_phone_settings,
				r_update_phone_settings, rEmpty);

//		// Create our Preview view and set it as the content of our
//		// Activity
//		mGLSurfaceView = (GLSurfaceView) findViewById(R.id.glview);
//		// We want an 8888 pixel format because that's required for
//		// a translucent window.
//		// And we want a depth buffer.
//		mGLSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
//		// Tell the cube renderer that we want to render a translucent version
//		// of the cube:
//		mGLSurfaceView.setRenderer(new DMRenderer(true));
//		// Use a surface format with an Alpha channel:
//		mGLSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
//		mGLSurfaceView.setZOrderOnTop(true);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	protected boolean isLocationDisplayed() {
		return true;
	}

	@Override
	protected void onPause() {
		// Called when activity is going into the background, but has not (yet)
		// been
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

		// zoom and pan
		// showPoints();
	}

	public void addSprite(DMSprite dmSprite) {
		((AbsoluteLayout) findViewById(R.id.spriteOverlay)).addView(dmSprite);
	}

	/**
	 * Registers to receive location updates from the GPS location provider and
	 * sensor updated from the compass.
	 */
	void registerLocationAndSensorListeners() {
		if (locationManager != null) {
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 0, 0, locationListener);
			try {
				locationManager.requestLocationUpdates(
						LocationManager.NETWORK_PROVIDER,
						1000 * 60 * 5 /* minTime */, 0 /* minDist */,
						locationListener);
			} catch (RuntimeException e) {
				// If anything at all goes wrong with getting a cell location do
				// not
				// abort. Cell location is not essential to this app.
				Log.w(DMConstants.TAG,
						"Could not register network location listener.");
			}
		}
		if (sensorManager == null) {
			return;
		}
		Sensor compass = sensorManager
				.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		if (compass == null) {
			return;
		}
		Log.d(DMConstants.TAG, "DM: Now registering sensor listeners.");
		sensorManager.registerListener(sensorListener, compass,
				SensorManager.SENSOR_DELAY_UI);
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

		if (keepMyLocationVisible && !locationIsVisible(currentLocation)) {
			MapController controller = mapView.getController();
			GeoPoint geoPoint = DMUtils.getGeoPoint(currentLocation);
			controller.animateTo(geoPoint);
		}
	}

	private boolean locationIsVisible(Location location) {
		if (location == null || mapView == null) {
			return false;
		}
		GeoPoint center = mapView.getMapCenter();
		int latSpan = mapView.getLatitudeSpan();
		int lonSpan = mapView.getLongitudeSpan();

		// Bottom of map view is obscured by zoom controls/buttons.
		// Subtract a margin from the visible area:
		GeoPoint marginBottom = mapView.getProjection().fromPixels(0,
				mapView.getHeight());
		GeoPoint marginTop = mapView.getProjection().fromPixels(
				0,
				mapView.getHeight()
						- mapView.getZoomButtonsController().getZoomControls()
								.getHeight());
		int margin = Math.abs(marginTop.getLatitudeE6()
				- marginBottom.getLatitudeE6());
		GeoRect r = new GeoRect(center, latSpan, lonSpan);
		r.top += margin;

		GeoPoint geoPoint = DMUtils.getGeoPoint(location);
		return r.contains(geoPoint);
	}

	private Runnable r_update_phone_settings = new Runnable() {

		@Override
		public void run() {
			note("phone: " + DMCore.self().myPhone.phone);
		}

	};

	private Runnable r_new_game = new Runnable() {

		@Override
		public void run() {
			note("New Game " + DMCore.self().myPhone.game);
			// DMCore.self().net(DMConstants.find_games, r_find_games, rEmpty);
		}

	};

	private Runnable r_find_games = new Runnable() {

		@Override
		public void run() {
			DMCore.self().myPhone.game = DMCore.self().alGames.get(0);
			DMCore.self().net(DMConstants.join_game, r_join_game, rEmpty);
			note("Join Game " + DMCore.self().myPhone.game);
		}

	};
	private Runnable r_join_game = new Runnable() {

		@Override
		public void run() {
			// zoom and pan
			showPoints();

		}

	};
	private Runnable rEmpty = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub

		}

	};
	private final LocationListener locationListener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {
			currentLocation = location;

			showCurrentLocation();

			if (location != null) {
				// Toast.makeText(
				// getBaseContext(),
				// "Lat: " + location.getLatitude() + "\nLng: "
				// + location.getLongitude(), Toast.LENGTH_SHORT)
				// .show();
				// note("Lat: " + location.getLatitude() + "\nLng: "
				// + location.getLongitude());
				DMCore.self().myPhone.setLocation(location);
				DMCore.self().net(DMConstants.update, rEmpty, rEmpty);
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
			Log.d(DMConstants.TAG, "DM: Now unregistering location listeners.");
			locationManager.removeUpdates(locationListener);
		}

		if (sensorManager != null) {
			Log.d(DMConstants.TAG, "DM: Now unregistering sensor listeners.");
			sensorManager.unregisterListener(sensorListener);
		}

	}

	public void showPoints() {
		if (mapView == null) {
			return;
		}

		int bottom = DMCore.self().dmMap.getBottom();
		int left = DMCore.self().dmMap.getLeft();
		int latSpanE6 = DMCore.self().dmMap.getTop() - bottom;
		int lonSpanE6 = DMCore.self().dmMap.getRight() - left;
		if (latSpanE6 > 0 && latSpanE6 < 180E6 && lonSpanE6 > 0
				&& lonSpanE6 < 360E6) {
			keepMyLocationVisible = false;
			GeoPoint center = new GeoPoint(bottom + latSpanE6 / 2, left
					+ lonSpanE6 / 2);
			if (DMUtils.isValidGeoPoint(center)) {
				mapView.getController().setCenter(center);
				mapView.getController().setZoom(20);
				mapView.getController().zoomToSpan(latSpanE6, lonSpanE6);
			}
		}
	}

	private final SensorEventListener sensorListener = new SensorEventListener() {
		@Override
		public void onSensorChanged(SensorEvent se) {
			synchronized (this) {
				float magneticHeading = se.values[0];
				DMSprite.get(DMCore.self().myPhoneIndex).setHeading(
						magneticHeading);
			}
		}

		@Override
		public void onAccuracyChanged(Sensor s, int accuracy) {
			// do nothing
		}
	};

	public static DMBoard getInstance() {
		return instance;
	}

	private int noteID = 0;

	@Override
	public void onClick(View v) {
		if (v == noteView) {
			keepMyLocationVisible = true;
			showCurrentLocation();
			note(DMConstants.NOTES[noteID]);
			noteID = (noteID + 1) % DMConstants.NOTES.length;
		}
		if (v == menuView) {
			toggle();
		}
		if (v == v1) {
			DMCore.self().myPhoneIndex = (DMCore.self().myPhoneIndex + 1) % 7;
		}
		if (v == v2) {
			DMCore.self().powerMode = !DMCore.self().powerMode;
		}
		if (v == v3) {
			DMCore.self().allowUpdate = !DMCore.self().allowUpdate;
		}
		if (v == v4) {
			DMCore.self().myPhoneState.alive = !DMCore.self().myPhoneState.alive;
		}
		if (v == v5) {
			DMCore.self().net(DMConstants.find_games, r_find_games, rEmpty);
		}
		if (v == v6) {
			DMCore.self().net(DMConstants.new_game, r_new_game, rEmpty);
		}
		v1.setText("role\n" + DMCore.self().myPhoneIndex);
		v2.setText("powerMode\n" + DMCore.self().powerMode);
		v3.setText("update\n" + DMCore.self().allowUpdate);
		v4.setText("alive\n" + DMCore.self().myPhoneState.alive);
		v5.setText("join_game");
		v6.setText("new_game");

	}

	private void toggle() {
		if (v1.getVisibility() == View.GONE) {
			v1.setVisibility(View.VISIBLE);
			v2.setVisibility(View.VISIBLE);
			v3.setVisibility(View.VISIBLE);
			v4.setVisibility(View.VISIBLE);
			v5.setVisibility(View.VISIBLE);
			v6.setVisibility(View.VISIBLE);
			return;
		}
		if (v1.getVisibility() == View.VISIBLE) {
			v1.setVisibility(View.GONE);
			v2.setVisibility(View.GONE);
			v3.setVisibility(View.GONE);
			v4.setVisibility(View.GONE);
			v5.setVisibility(View.GONE);
			v6.setVisibility(View.GONE);
		}

	}

	public void note(String txt) {
		noteView.setText("Street Pacman\n\n" + txt);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// toggle();
		// TODO Auto-generated method stub
		return false;
	}
}
