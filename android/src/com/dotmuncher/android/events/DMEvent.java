package com.dotmuncher.android.events;


public class DMEvent {	
	private String lat = "-lat";
	private String lgn = "-lgn";
	private String phone = "-phone";
	private String game = "-game";
	
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getGame() {
		return game;
	}
	public void setGame(String game) {
		this.game = game;
	}
}
