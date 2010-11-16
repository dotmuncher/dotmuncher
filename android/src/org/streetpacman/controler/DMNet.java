package org.streetpacman.controler;

import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

import android.util.Log;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;

public class DMNet {
	private static String host = "s0.dotmuncher.com";
	//private static String host = "10.0.2.2";
	//private String host = "http://search.twitter.com";
	
	// http://senior.ceng.metu.edu.tr/2009/praeda/2009/01/11/a-simple-restful-client-at-android/
    private static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

	// http://www.softwarepassion.com/android-series-parsing-json-data-with-gson/
	public static InputStream getJSONData(String path, String query){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        java.net.URI uri;
        InputStream data = null;
        try {
            uri = new URI("http", null , host, 8000, path, query, null);
            HttpGet method = new HttpGet(uri);
            HttpResponse response = httpClient.execute(method);
            data = response.getEntity().getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
	/*
	public void runJSONParser(){
        try{
        Log.i("MY INFO", "Json Parser started..");
        Gson gson = new Gson();
        Reader r = new InputStreamReader(getJSONData("/trends.json"));
        Log.i("MY INFO", r.toString());
        TwitterTrends objs = gson.fromJson(r, TwitterTrends.class);
        Log.i("MY INFO", ""+objs.getTrends().size());
        for(TwitterTrend tr : objs.getTrends()){
            Log.i("TRENDS", tr.getName() + " - " + tr.getUrl());
        }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    */

	public static JSONObject api(String method, JSONObject json) {
        try{
            Log.i("MY INFO", "DMNet.api method: " + method);
            Reader r = new InputStreamReader(getJSONData("/api/v0/"+ method +".json","json=" + json.toString()));
            
            Log.i("MY INFO", r.toString());
            //DMGame objs = gson.fromJson(r, DMGame.class);

        }catch(Exception ex){
            ex.printStackTrace();
        }
		return json;
	}
}
