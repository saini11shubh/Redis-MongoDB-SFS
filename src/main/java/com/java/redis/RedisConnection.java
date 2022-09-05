package com.java.redis;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bson.Document;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.java.mongodbconnection.Connection;
import com.java.playerdetail.PlayerData;
import com.mongodb.client.FindIterable;

import redis.clients.jedis.Jedis;

public class RedisConnection {
	private Jedis jedis = new Jedis("redis://localhost:6379");
	private String player_name;
	private Gson gson;
	private List<PlayerData> playerList;
	private Connection connect;
	private PlayerData playerData;

	public boolean redisConnect(PlayerData playerData) throws JsonMappingException, JsonProcessingException {		
		boolean flag = false;
		this.playerData = playerData;
		//get data from Redis if exist
		if (jedis.sismember("player_name",
				playerData.getPlayer_name() + playerData.getEmail() + playerData.getMobile_no())) {
			System.out.println("Come data from Redis");
			flag = true;
		} else {
			connect = new Connection();
			// get playerData list
			playerList = connect.readData();
			gson = new Gson();
			flag = saveOnRedis();
		}
		return flag;
	}

	// is here Login player data save on redis
	private boolean saveOnRedis() {
		int i = connect.getid(playerData);
		// check data
		if (i < playerList.size()) {
			PlayerData data = this.setData(i); //
			// save data on redis through set
			jedis.sadd("player_name", data.getPlayer_name() + data.getEmail() + data.getMobile_no());
			jedis.expire("player_name", 60);		//Delete data in 60second
			System.out.println("Stored string in redis:: " + jedis.smembers("player_name"));
			return true;
		} else {
			System.out.println("Data is not here");
			return false;
		}
	}

	// set data in Playerdata class according to us
	private PlayerData setData(int i) {
		// conver list data into json
		String jsonCartList = gson.toJson(playerList.get(i));
		JSONObject jsonObj = new JSONObject(jsonCartList);		
		// fetch data from jsonObect to set on our PlayerData POJO class
		PlayerData pd = new PlayerData();
		pd.setPlayer_name(jsonObj.getString("player_name"));
		pd.setEmail(jsonObj.getString("Email"));
		pd.setMobile_no(jsonObj.getInt("Mobile_no"));
		System.out.println(pd);
		return pd;
	}
}
