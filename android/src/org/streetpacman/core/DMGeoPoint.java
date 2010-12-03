package org.streetpacman.core;

import com.google.android.maps.GeoPoint;

public class DMGeoPoint extends GeoPoint {
	public Boolean visible;
	public float radius;
	public int status;
	
	public DMGeoPoint(int latitudeE6, int longitudeE6) {
		super(latitudeE6, longitudeE6);
		reset();
	}
	
	public void reset(){
		visible = true;
		radius = 10;
		status = DMConstants.POINT_INIT;
	}
}
