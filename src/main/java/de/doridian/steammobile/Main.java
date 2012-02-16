package de.doridian.steammobile;

import de.doridian.steammobile.connection.MessageHandler;
import de.doridian.steammobile.connection.MessageListener;
import de.doridian.steammobile.connection.SteamConnection;
import de.doridian.steammobile.connection.exceptions.InvalidSteamguardTokenException;
import de.doridian.steammobile.connection.exceptions.LoginException;
import de.doridian.steammobile.connection.exceptions.RequireSteamguardTokenException;
import de.doridian.steammobile.friend.Friend;
import de.doridian.steammobile.messages.Message;
import de.doridian.steammobile.messages.TextMessage;
import de.doridian.steammobile.methods.RequestException;

import java.io.*;
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

	private static void tryLogin(String username, String password, String token) {
		try {
			connection.tryLogin(username, password, token);
		} catch(LoginException e) {
			if(e instanceof InvalidSteamguardTokenException) {
				System.out.println("Invalid steam guard token.");
				System.out.print("Retry (Y/N)? ");
				if(Main.input.nextLine().toLowerCase().startsWith("n")) {
					System.exit(0);
				} else {
					tryLogin(username, password, null);
				}
			} else if(e instanceof RequireSteamguardTokenException) {
				System.out.print("Please enter the steam guard code you received: ");
				token = Main.input.nextLine();
				if(token.isEmpty()) {
					System.exit(0);
				} else {
					tryLogin(username, password, token);
				}
			} else {
				System.out.println("Unknown error: " + e.getMessage());
			}
			return;
		}

		writeAuthFile(username, password, token);
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

		try {
			connection = new SteamConnection();
			tryLogin(user, password, token);
			handler = new MessageHandler(connection);
			handler.logon();
			connection.loadFriendsList();
			connection.loadFriendsDetails();
			for(Friend friend : connection.friends.values()) {
				System.out.println(friend.steamid + " = " + friend.personaname);
			}
		} catch(RequestException e) {
			e.printStackTrace();
			System.exit(0);
			return;
		}

		handler.registerListener(new MainMessageListener());

		handler.startGetMessagesLoop();
	}

	public static void writeAuthFile(String username, String password, String token) {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(AUTHFILE));
			writer.println(username);
			writer.println(password);
			writer.println(token);
			writer.close();
		} catch(Exception e) {
			System.out.println("Writing authentication file failed because:");
			e.printStackTrace();
		}
	}

	public static class MainMessageListener implements MessageListener {
		@Handler
		public void onTextMsg(TextMessage msg) {
			System.out.println("<" + msg.steamid_other + "> " + msg.text);
		}

		@Handler
		public void onMsg(Message msg) {
			System.out.println("[" + msg.steamid_other + "] " + msg.getType());
		}
	}
}
