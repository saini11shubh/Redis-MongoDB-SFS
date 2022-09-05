package sfs2x.client.examples;

import java.util.Scanner;

import com.java.playerdetail.PlayerData;

public class MainClass {
	public static void main(String[] args) throws Exception {
		try (Scanner scanner = new Scanner(System.in)) {
			PlayerData playerData=new PlayerData();
//			System.out.print("Please enter you username: ");
//			String username = scanner.next();
//			System.out.print("Please enter you Email Id: ");
//			String email = scanner.next();
//			System.out.print("Please enter you Mobile No: ");
//			int Mobile_no = scanner.nextInt();
//			playerData.setPlayer_name(username);
//			playerData.setEmail(email);
//			playerData.setMobile_no(Mobile_no);
			
			//for testing purpose
			playerData.setPlayer_name("E");
			playerData.setEmail("e@gmail.com");
			playerData.setMobile_no(685654564);
			new SFS2XConnector(playerData);
		}
	}
}