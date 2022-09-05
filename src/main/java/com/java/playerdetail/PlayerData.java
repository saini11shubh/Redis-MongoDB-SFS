package com.java.playerdetail;

public class PlayerData {
	private String player_name;
	private String Email;
	private int Mobile_no;
	private String _id;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public PlayerData(String player_name, String email, int mobile_no, String _id) {
		super();
		this.player_name = player_name;
		Email = email;
		Mobile_no = mobile_no;
		this._id = _id;
	}
	public PlayerData(String player_name, String email, int mobile_no) {
		super();
		this.player_name = player_name;
		Email = email;
		Mobile_no = mobile_no;
	}
	public PlayerData() {
		
	}
	public String getPlayer_name() {
		return player_name;
	}
	public void setPlayer_name(String player_name) {
		this.player_name = player_name;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public int getMobile_no() {
		return Mobile_no;
	}
	public void setMobile_no(int mobile_no) {
		Mobile_no = mobile_no;
	}
	@Override
	public String toString() {
		return "PlayerData [player_name=" + player_name + ", Email=" + Email + ", Mobile_no=" + Mobile_no + "]";
	}
	
}
