package com.dotmuncher.android.controler;

import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.client.methods.HttpGet;
///HttpGet;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import com.dotmuncher.android.events.DMEvent;
import com.dotmuncher.android.events.DMEvents;
import com.dotmuncher.android.events.DMGame;
import com.dotmuncher.android.events.DMGames;
import com.dotmuncher.android.events.DMMap;
import com.dotmuncher.android.events.DMMaps;
import com.dotmuncher.android.events.TwitterTrend;
import com.dotmuncher.android.events.TwitterTrends;
import com.google.gson.Gson;

import android.util.Log;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;

public class DMEventControler {
	private String server_root_url = "http://urban.pyxc.org/api/v0";
	//private String server_root_url = "http://search.twitter.com";
	
	public void find_maps(){
        try{
        Log.i("MY INFO", "Json Parser started.. find_maps");
        Gson gson = new Gson();
        //gson.toJson(maps);
        Reader r = new InputStreamReader(getJSONData("/find_maps.json"));
        Log.i("MY INFO", r.toString());
        DMMaps objs = gson.fromJson(r, DMMaps.class);
        Log.i("MY INFO", ""+objs.getMaps().size());
        for(DMMap m : objs.getMaps()){
            Log.i("MAP", m.getId());
        }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

	public void find_games(){
        try{
        Log.i("MY INFO", "Json Parser started.. find_games");
        Gson gson = new Gson();
        //gson.toJson(games);
        Reader r = new InputStreamReader(getJSONData("/find_games.json"));
        Log.i("MY INFO", r.toString());
        DMGames objs = gson.fromJson(r, DMGames.class);
        Log.i("MY INFO", ""+objs.getGames().size());
        for(DMGame g : objs.getGames()){
            Log.i("GAME", g.getId());
        }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
	
	public void submit_and_get_events(){
        try{
        Log.i("MY INFO", "Json Parser started.. submit_and_get_events");
        Gson gson = new Gson();
        
        InputStream instream = getJSONData("/submit_and_get_events.json");
        String result= convertStreamToString(instream);
        
        // A Simple JSONObject Creation
        JSONObject json=new JSONObject(result);
        JSONArray json_events_array = json.getJSONArray("events");
        
        /*
        Log.i("MY INFO", ""+objs.getEvents().size());
        for(DMEvent e : objs.getEvents()){
            Log.i("EVENT", e.getLat() + " - " + e.getLgn());
        }
        */
        
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
	
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
	public InputStream getJSONData(String url){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        java.net.URI uri;
        InputStream data = null;
        try {
            uri = new URI(server_root_url + url);
            HttpGet method = new HttpGet(uri);
            HttpResponse response = httpClient.execute(method);
            data = response.getEntity().getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return data;
    }
	
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
}
