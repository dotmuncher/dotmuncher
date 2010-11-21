package org.streetpacman.states;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.android.maps.GeoPoint;

public class DMMap {
	public int map = -1;
	public List<GeoPoint> dotPoints = new ArrayList<GeoPoint>();
	public List<GeoPoint> basePoints = new ArrayList<GeoPoint>();
	public List<GeoPoint> powerPelletPoints = new ArrayList<GeoPoint>();
	
	private Map<String,GeoPoint> dotPointsMap;
	private Map<String,GeoPoint> basePointsMap;
	private Map<String,GeoPoint> powerPelletPointsMap;

	public void buildPointsMap() {
		dotPointsMap = new HashMap<String,GeoPoint>();
		for(GeoPoint p: dotPoints){
			dotPointsMap.put(p.getLatitudeE6() + "," + p.getLongitudeE6(), p);
		}
		basePointsMap = new HashMap<String,GeoPoint>();
		for(GeoPoint p: basePoints){
			basePointsMap.put(p.getLatitudeE6() + "," + p.getLongitudeE6(), p);
		}
		powerPelletPointsMap = new HashMap<String,GeoPoint>();
		for(GeoPoint p: powerPelletPoints){
			powerPelletPointsMap.put(p.getLatitudeE6() + "," + p.getLongitudeE6(), p);
		}		
	}
	
	public void killPowerPellet(int x, int y){
		synchronized (powerPelletPoints) {
			powerPelletPoints.remove(powerPelletPointsMap.get(x + "," + y));
		}		
	}
	
	public void killDot(int x, int y){
		synchronized (dotPoints) {
			dotPoints.remove(dotPointsMap.get(x + "," + y));
		}			
	}
}
