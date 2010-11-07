package com.dotmuncher.android.events;

import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.client.methods.HttpGet;
///HttpGet;
import org.apache.http.HttpResponse;

import com.google.gson.Gson;

import android.util.Log;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;

public class DMEvent {

	public InputStream getJSONData(String url){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        java.net.URI uri;
        InputStream data = null;
        try {
            uri = new URI(url);
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
        Reader r = new InputStreamReader(getJSONData("http://search.twitter.com/trends.json"));
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
