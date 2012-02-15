package de.doridian.steammobile;

import de.doridian.steammobile.messages.Message;
import de.doridian.steammobile.messages.TextMessage;
import de.doridian.steammobile.methods.RequestException;

import java.io.*;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
	public static final Scanner input = new Scanner(System.in);
	public static final Random random = new Random();

	private static final String AUTHFILE = "auth.cfg";

	public static SteamConnection connection;
	public static MessageHandler handler;

	public static boolean isRunning = true;
	
	public static void main(String[] args) {
		connect();
	}

	public static void connect() {
		File file = new File(AUTHFILE);

		String user; String password; String token;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			user = reader.readLine();
			password = reader.readLine();
			token = reader.readLine();
			reader.close();
		} catch(Exception e) {
			System.out.print("Username: ");
			user = input.nextLine();
			System.out.print("Password: ");
			password = input.nextLine();
			token = null;
			writeAuthFile(user, password, null);
		}

		connection = new SteamConnection(user, password, token);
		handler = new MessageHandler(connection);
		handler.logon();
		handler.loadFriendsList();
		for(Friend friend : handler.friends) {
			try {
				friend.getFullStats(connection);
			} catch(RequestException e) { }
			System.out.println(friend.steamid + " = " + friend.personaname);
		}

		new Thread() {
			@Override
			public void run() {
				while(isRunning) {
					List<Message> newMessages = handler.getMessages();
					for(Message msg : newMessages) {
						if(msg instanceof TextMessage) {
							TextMessage txtmsg = (TextMessage)msg;
							System.out.println("<" + txtmsg.steamid_from + "> " + txtmsg.text);
						}
					}
					try {
						Thread.sleep(5000);
					} catch(Exception e) { }
				}
			}
		}.start();
	}


	
	public static void writeAuthFile(String username, String password, String token) {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(AUTHFILE));
			writer.println(username);
			writer.println(password);
			writer.println(token);
			writer.close();
		} catch(Exception e) {
			
		}
	}
}
