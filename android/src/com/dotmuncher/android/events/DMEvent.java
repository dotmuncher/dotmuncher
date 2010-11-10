package com.dotmuncher.android.events;


public class DMEvent {	
	private String lat;
	private String lgn;
	private int phone;
	private int game;
	
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLgn() {
		return lgn;
	}
	public void setLgn(String lgn) {
		this.lgn = lgn;
	}
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	public int getGame() {
		return game;
	}
	public void setGame(int game) {
		this.game = game;
	}
}
