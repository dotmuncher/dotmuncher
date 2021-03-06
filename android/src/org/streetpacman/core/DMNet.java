package org.streetpacman.core;

import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

import android.util.Log;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

public class DMNet {
	//*
	private static String host = "urban.pyxc.org";
	//private static String host = "75.101.141.245";
	private static int port = 80;
	//*/
	
	/*
	//private static String host = "s0.dotmuncher.com";
	private static String host = "174.143.141.50";
	private static int port = 8000;
	//*/
	
	//private static String host = "10.0.2.2";
	
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
            uri = new URI("http", null , host, port, path, query, null);
            HttpGet method = new HttpGet(uri);
            HttpResponse response = httpClient.execute(method);
            data = response.getEntity().getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
	
	public static JSONObject call(final int api, JSONObject json) {
        try{
            Log.i(DMConstants.TAG, "api " + DMConstants.API[api]);
            Log.i(DMConstants.TAG, "json request =" + json.toString(4));
            InputStream instream = getJSONData("/api/v0/"+ DMConstants.API[api] +".json","json=" + json.toString());            
            json = new JSONObject(convertStreamToString(instream));
            Log.i(DMConstants.TAG, "json response =" + json.toString(4));
            return json;
        }catch(Exception ex){        	
            ex.printStackTrace();
            return null;
        }        		
	}
}
