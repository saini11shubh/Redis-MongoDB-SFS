package sfs2x.client.examples;

import java.sql.SQLException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.java.playerdetail.PlayerData;
import com.java.redis.RedisConnection;

import sfs2x.client.SmartFox;
import sfs2x.client.core.SFSEvent;
import sfs2x.client.entities.Room;
import sfs2x.client.requests.JoinRoomRequest;
import sfs2x.client.requests.LoginRequest;
import sfs2x.client.util.ConfigData;

public class SFS2XConnector {
	private final ConfigData cfg;
	private final SmartFox sfs;
	private final String userName;
	PlayerData playerData;
	// Save data on redis
	RedisConnection rd = new RedisConnection();

	public SFS2XConnector(PlayerData playerData) throws Exception {
		// Configure client connection settings
		cfg = new ConfigData();
		cfg.setHost("localhost");
		cfg.setPort(9933);
		cfg.setZone("BasicExamples");
		cfg.setDebug(false);

		this.playerData = playerData;
		this.userName = playerData.getPlayer_name();
		// Set up event handlers
		sfs = new SmartFox();
		try {
			System.out.println("redis method call");
			boolean flag = rd.redisConnect(playerData);
			if (flag == true) {
				sfs.addEventListener(SFSEvent.CONNECTION, evt -> {
					boolean success = (boolean) evt.getArguments().get("success");
					if (success) {
						System.out.println("Connection success");
						sfs.send(new LoginRequest(userName));						
					} else {
						System.out.println("Connection Failed. Is the server running?");
					}
				});
			}
			else {
				System.out.println("User is Not Here");
				System.exit(1);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		sfs.addEventListener(SFSEvent.LOGIN, evt -> {
			System.out.println("Logged in as: " + sfs.getMySelf().getName());
			sfs.send(new JoinRoomRequest("The Lobby"));
		//	rd.deleteDataOnRedis();
		});
		sfs.addEventListener(SFSEvent.CONNECTION_LOST, evt -> {
			System.out.println("-- Connection lost --");
		});

		sfs.addEventListener(SFSEvent.LOGIN_ERROR, evt -> {
			String message = (String) evt.getArguments().get("errorMessage");
			System.out.println("Login failed. Cause: " + message);
		});
		sfs.addEventListener(SFSEvent.ROOM_JOIN, evt -> {
			Room room = (Room) evt.getArguments().get("room");
			System.out.println("Joined Room: " + room.getName());
		});
		sfs.addEventListener(SFSEvent.ADMIN_MESSAGE, baseEvent -> {
			System.out.println("Message from Server Admin: " + baseEvent.getArguments().get("message"));
		});
		
		sfs.connect(cfg);
	}
}