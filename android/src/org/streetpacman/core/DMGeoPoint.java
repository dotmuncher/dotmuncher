package org.streetpacman.core;

import android.graphics.Color;
import android.graphics.Paint;

import com.google.android.maps.GeoPoint;

public class DMGeoPoint extends GeoPoint {
	public Boolean visible = true;
	public float radius;

	public DMGeoPoint(int latitudeE6, int longitudeE6) {
		super(latitudeE6, longitudeE6);
	}
}
