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

import org.streetpacman.R;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;

public class DMMapView extends MapActivity {
    MapView mapView; 
    MapController mc;
    GeoPoint p;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mapView = new MapView(this, "MapViewCompassDemo_DummyAPIKey");
        //setContentView(R.layout.mapview);
        setContentView(mapView);
        

        
        String coordinates[] = {"1.352566007", "103.78921587"};
        double lat = Double.parseDouble(coordinates[0]);
        double lng = Double.parseDouble(coordinates[1]);
 
        p = new GeoPoint(
            (int) (lat * 1E6), 
            (int) (lng * 1E6));

        //*/...
        mc = mapView.getController();

        mc.animateTo(p);
        mc.setZoom(17); 
 
        //---Add a location marker---
        MapOverlay mapOverlay = new MapOverlay();
        List<Overlay> listOfOverlays = mapView.getOverlays();
        listOfOverlays.clear();
        listOfOverlays.add(mapOverlay);        
 
        mapView.invalidate();
        //*/
    }

    @Override
    protected boolean isRouteDisplayed() { return false; }
    
    class MapOverlay extends com.google.android.maps.Overlay
    {
    	
        @Override
        public boolean draw(Canvas canvas, MapView mapView, 
        boolean shadow, long when) 
        {
            super.draw(canvas, mapView, shadow);                   
 
            //---translate the GeoPoint to screen pixels---
            Point screenPts = new Point();
            
            mapView.getProjection().toPixels(p, screenPts);
 
            //---add the marker---
            Bitmap bmp = BitmapFactory.decodeResource(
                getResources(), R.drawable.pacman_chomp0);            
            //canvas.drawBitmap(bmp, screenPts.x, screenPts.y-50, null);
            
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            canvas.drawCircle(screenPts.x, screenPts.y, 10, paint);
            return true;
        }
    } 

}
