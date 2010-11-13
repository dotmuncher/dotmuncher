package org.streetpacman.controler;

import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.client.methods.HttpGet;
///HttpGet;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.streetpacman.events.DMEvent;
import org.streetpacman.events.DMEvents;
import org.streetpacman.events.TwitterTrend;
import org.streetpacman.events.TwitterTrends;
import org.streetpacman.states.DMGame;
import org.streetpacman.states.DMMap;

import com.google.gson.Gson;

import android.location.Location;
import android.util.Log;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;

public class DMEventControler {
	private String host = "urban.pyxc.org";
	//private String host = "http://search.twitter.com";
	
	public void find_maps(){
        try{
        Log.i("MY INFO", "Json Parser started.. find_maps");
        Gson gson = new Gson();
        //gson.toJson(maps);
        Reader r = new InputStreamReader(getJSONData("/api/v0/find_maps.json",""));
        Log.i("MY INFO", r.toString());
        DMMap objs = gson.fromJson(r, DMMap.class);
        /*
        Log.i("MY INFO", ""+objs.getMaps().size());
        for(DMMapEvent m : objs.getMaps()){
            Log.i("MAP", m.getId());
        }
        */
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

	public void find_games(){
        try{
        Log.i("MY INFO", "Json Parser started.. find_games");
        Gson gson = new Gson();
        //gson.toJson(games);
        Reader r = new InputStreamReader(getJSONData("/api/v0/find_games.json",""));
        Log.i("MY INFO", r.toString());
        DMGame objs = gson.fromJson(r, DMGame.class);
        /*
        Log.i("MY INFO", ""+objs.getGames().size());
        for(DMGameEvent g : objs.getGames()){
            Log.i("GAME", g.getId());
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
	public InputStream getJSONData(String path, String query){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        java.net.URI uri;
        InputStream data = null;
        try {
            uri = new URI("http", "", host, 80, path, query, "");
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
}
