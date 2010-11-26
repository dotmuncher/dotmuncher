package org.streetpacman;

import org.streetpacman.controler.DMApp;
import org.streetpacman.store.DMGeoPoint;
import org.streetpacman.store.DMMap;
import org.streetpacman.store.DMPhone;
import org.streetpacman.store.DMPhoneState;
import org.streetpacman.util.DMUtils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

class DMOverlay extends Overlay {
	private final int arrowWidth, arrowHeight;
	private Location myLocation;
	private final Context context;
	private final DMApp dmApp;
	private final DMMap dmMap;
	private final DMPhone dmPhone;
	private final Paint errorCirclePaint;
	private final Paint whiteFillPaint;
	private final Paint blackStrokePaint;
	private Drawable d;

	public DMOverlay(DMApp dmApp, Context context) {

		this.dmApp = dmApp;
		dmMap = dmApp.dmMap;
		dmPhone = dmApp.dmPhone;
		this.context = context;

		errorCirclePaint = new Paint();
		errorCirclePaint.setColor(Color.BLUE);
		errorCirclePaint.setStyle(Paint.Style.STROKE);
		errorCirclePaint.setStrokeWidth(3);
		errorCirclePaint.setAlpha(127);
		errorCirclePaint.setAntiAlias(true);

		whiteFillPaint = new Paint();
		whiteFillPaint.setColor(Color.WHITE);
		whiteFillPaint.setAlpha(127);

		blackStrokePaint = new Paint();
		blackStrokePaint.setColor(Color.BLACK);
		blackStrokePaint.setStyle(Paint.Style.STROKE);
		blackStrokePaint.setStrokeWidth(3);
		blackStrokePaint.setAlpha(127);
		blackStrokePaint.setAntiAlias(true);

		d = context.getResources().getDrawable(R.drawable.pacman_chomp);
		arrowWidth = d.getIntrinsicWidth();
		arrowHeight = d.getIntrinsicHeight();
		d.setBounds(0, 0, arrowWidth, arrowHeight);
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		if (shadow) {
			return;
		}
		super.draw(canvas, mapView, shadow);
		final Projection projection = getMapProjection(mapView);
		Point screenPts = new Point();

		synchronized (dmApp.dmPhoneStates) {
			for (DMPhoneState dmPhoneState : dmApp.dmPhoneStates) {
				// Only draw others
				if (dmPhoneState.phone != dmPhone.phone) {
					GeoPoint p = new GeoPoint((int) (dmPhoneState.lat * 1E6),
							(int) (dmPhoneState.lng * 1E6));
					projection.toPixels(p, screenPts);
					//
					if (dmPhoneState.beenEaten) {
						canvas.drawCircle(screenPts.x, screenPts.y, 20,
								blackStrokePaint);
					} else {
						drawElement(canvas, projection,
								DMUtils.getGeoPoint(myLocation), d,
								-(arrowWidth / 2) + 3, -(arrowHeight / 2));
					}
				}
			}
		}

		for (DMGeoPoint p : dmMap.allPoints) {
			if (p.visible) {
				projection.toPixels(p, screenPts);
				canvas.drawCircle(screenPts.x, screenPts.y, p.radius,
						whiteFillPaint);
				canvas.drawCircle(screenPts.x, screenPts.y, p.radius,
						blackStrokePaint);
			}
		}

		// Draw the current location
		drawMyLocation(canvas, projection);
	}

	Projection getMapProjection(MapView mapView) {
		return mapView.getProjection();
	}

	private void drawMyLocation(Canvas canvas, Projection projection) {
		if (myLocation == null) {
			return;
		}
		Point pt = drawElement(canvas, projection,
				DMUtils.getGeoPoint(myLocation), d, -(arrowWidth / 2) + 3,
				-(arrowHeight / 2));
		// Draw the error circle.
		float radius = projection.metersToEquatorPixels(myLocation
				.getAccuracy());
		canvas.drawCircle(pt.x, pt.y, radius, errorCirclePaint);

	}

	/**
	 * Sets the pointer location (will be drawn on next invalidate).
	 */
	public void setMyLocation(Location myLocation) {
		this.myLocation = myLocation;
	}

	Point drawElement(Canvas canvas, Projection projection, GeoPoint geoPoint,
			Drawable element, int offsetX, int offsetY) {
		Point pt = new Point();
		projection.toPixels(geoPoint, pt);
		canvas.save();
		canvas.translate(pt.x + offsetX, pt.y + offsetY);
		element.draw(canvas);
		canvas.restore();
		return pt;
	}
}
