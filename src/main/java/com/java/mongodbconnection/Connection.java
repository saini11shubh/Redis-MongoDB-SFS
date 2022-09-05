package com.java.mongodbconnection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.java.playerdetail.PlayerData;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Connection {

	private MongoCollection<Document> collection;
	private MongoDatabase database;
	private FindIterable<Document> iterDoc;
	private Iterator it;
	PlayerData playerData;
	public List<PlayerData> myList;

	public Connection() {
		MongoClient mongo = new MongoClient("localhost", 27017);
		database = mongo.getDatabase("GameData");
		collection = database.getCollection("PlayerData");
	}

	public List<PlayerData> readData() {
		System.out.println("-----------------------------------Read--------------------------------");
		iterDoc = collection.find();
		it = iterDoc.iterator();
		myList = getListFromIterator(it);
		System.out.println("Data is\n " + myList.size());
		return myList;
	}

	//here we want login player data index and player have a list and not
	public int getid(PlayerData playerData) {
		int i = 0;
		this.playerData = playerData;
		it = iterDoc.iterator();
		while (it.hasNext()) {
			Gson gson = new Gson();
			String jsonCartList = gson.toJson(it.next());
			JSONObject jobj = new JSONObject(jsonCartList);
			System.out.println("Player Nmae " + jobj.getString("player_name"));
			//check login user data and mongodb data if here player exist so Login success fully and return user data index   
			if ((jobj.getString("player_name").equals(playerData.getPlayer_name()))
					&& (jobj.getString("Email").equals(playerData.getEmail()))) {
				break;
			} else {
				i++;
			}
		}
		System.out.println("I value is "+i);
		return i;
	}
	//Iterator data convert into list 
	private List<PlayerData> getListFromIterator(Iterator it) {
		// Convert iterator to iterable
		Iterable<PlayerData> iterable = () -> it;
		// Create a List from the Iterable
		List<PlayerData> list = StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
		// Return the List
		return list;
	}
}
