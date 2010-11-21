package org.streetpacman;

import org.streetpacman.controler.DMApp;
import org.streetpacman.states.DMGame;
import org.streetpacman.states.DMMap;
import org.streetpacman.states.DMPhone;
import org.streetpacman.states.DMPhoneState;
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

class DMOverlay extends Overlay{
	private Location myLocation;
	private final Context context;
	private final DMMap dmMap;
	private final DMGame dmGame;
	private final DMPhone dmPhone;
	private final Paint errorCirclePaint;
	private final Paint dotPaint;
	private final Paint basePaint;
	private final Paint powerPelletPaint;
	private final Paint phonePaint;
	
	public DMOverlay(DMApp dmApp, Context context) {
	    dmMap = dmApp.dmMap;
	    dmGame = dmApp.dmGame;
	    dmPhone = dmApp.dmPhone;
	    this.context = context;
	    
	    errorCirclePaint = new Paint();
	    errorCirclePaint.setColor(Color.BLUE);
	    errorCirclePaint.setStyle(Paint.Style.STROKE);
	    errorCirclePaint.setStrokeWidth(3);
	    errorCirclePaint.setAlpha(127);
	    errorCirclePaint.setAntiAlias(true);
	    
	    phonePaint = powerPelletPaint = basePaint = dotPaint = new Paint();
	    dotPaint.setColor(Color.WHITE);
	}
	
	  @Override
	  public void draw(Canvas canvas, MapView mapView, boolean shadow) {
	    if (shadow) {
	      return;
	    }
        super.draw(canvas, mapView, shadow);        
        final Projection projection = getMapProjection(mapView);
        Point screenPts = new Point();

        synchronized(dmGame.dmPhoneStates){
	        for(DMPhoneState dmPhoneState : dmGame.dmPhoneStates){
	        	// Only draw others
	        	if(dmPhoneState.phone != dmPhone.phone){
	        		GeoPoint p = new GeoPoint(
		                    (int) (dmPhoneState.lat * 1E6), 
		                    (int) (dmPhoneState.lng * 1E6));
		        	projection.toPixels(p, screenPts);
		            canvas.drawCircle(screenPts.x, screenPts.y, 30, phonePaint);	
	        	}	        	
	        }
        }
                
        synchronized(dmMap.dotPoints){
	        for(GeoPoint p : dmMap.dotPoints){
	        	projection.toPixels(p, screenPts);
	            canvas.drawCircle(screenPts.x, screenPts.y, 10, dotPaint);
	        }
        }
        synchronized(dmMap.basePoints){
	        for(GeoPoint p : dmMap.basePoints){
	        	projection.toPixels(p, screenPts);
	            canvas.drawCircle(screenPts.x, screenPts.y, 20, basePaint);
	        }
        }
        synchronized(dmMap.powerPelletPoints){
	        for(GeoPoint p : dmMap.powerPelletPoints){
	        	projection.toPixels(p, screenPts);
	            canvas.drawCircle(screenPts.x, screenPts.y, 25, powerPelletPaint);
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
	    
		  Point pt = new Point();
		  projection.toPixels(DMUtils.getGeoPoint(myLocation), pt);
		/*
	    Point pt = drawElement(canvas, projection,
	        DMUtils.getGeoPoint(myLocation),
	        context.getResources().getDrawable(R.drawable.arrow_0),
	        -(32 / 2) + 3, -(32 / 2));
	    */
	    // Draw the error circle.
	    float radius = projection.metersToEquatorPixels(myLocation.getAccuracy());
	    canvas.drawCircle(pt.x, pt.y, radius, errorCirclePaint);
	    canvas.drawCircle(pt.x, pt.y, 30, powerPelletPaint);

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
