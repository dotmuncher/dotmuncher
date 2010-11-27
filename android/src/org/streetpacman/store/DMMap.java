package org.streetpacman.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.streetpacman.stats.ExtremityMonitor;

import android.graphics.Color;
import android.graphics.Paint;

public class DMMap {
	public int map = -1;

	public List<DMGeoPoint> dotPoints;
	public List<DMGeoPoint> basePoints;
	public List<DMGeoPoint> powerPelletPoints;
	public List<DMGeoPoint> allPoints = new ArrayList<DMGeoPoint>();

	private Map<String, DMGeoPoint> dotPointsMap;
	private Map<String, DMGeoPoint> basePointsMap;
	private Map<String, DMGeoPoint> powerPelletPointsMap;

	public ExtremityMonitor latitudeExtremities = new ExtremityMonitor();
	public ExtremityMonitor longitudeExtremities = new ExtremityMonitor();

	public void buildPoints() {
		// dot
		dotPointsMap = new HashMap<String, DMGeoPoint>();
		latitudeExtremities.reset();
		longitudeExtremities.reset();
		for (DMGeoPoint p : dotPoints) {
			dotPointsMap.put(p.getLatitudeE6() + "," + p.getLongitudeE6(), p);
			latitudeExtremities.update(p.getLatitudeE6());
			longitudeExtremities.update(p.getLongitudeE6());
			p.radius = 10;
		}
		// base
		basePointsMap = new HashMap<String, DMGeoPoint>();
		for (DMGeoPoint p : basePoints) {
			basePointsMap.put(p.getLatitudeE6() + "," + p.getLongitudeE6(), p);
			p.radius = 15;
		}
		// powerPellet
		powerPelletPointsMap = new HashMap<String, DMGeoPoint>();
		for (DMGeoPoint p : powerPelletPoints) {
			powerPelletPointsMap.put(
					p.getLatitudeE6() + "," + p.getLongitudeE6(), p);
			p.radius = 20;
		}
		// construct allPoints
		allPoints.addAll(dotPoints);
		allPoints.addAll(powerPelletPoints);
		allPoints.addAll(basePoints);
	}

	public void killPowerPellet(int x, int y) {
		powerPelletPointsMap.get(x + "," + y).visible = false;
	}

	public void killDot(int x, int y) {
		dotPointsMap.get(x + "," + y).visible = false;
	}

	// position helper
	public int getLeft() {
		return (int) (longitudeExtremities.getMin() * 1E6);
	}

	public int getRight() {
		return (int) (longitudeExtremities.getMax() * 1E6);
	}

	public int getBottom() {
		return (int) (latitudeExtremities.getMin() * 1E6);
	}

	public int getTop() {
		return (int) (latitudeExtremities.getMax() * 1E6);
	}

}
