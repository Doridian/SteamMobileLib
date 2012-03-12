package de.doridian.steammobile.test;

import de.doridian.steammobile.auth.base.AuthStorage;
import de.doridian.steammobile.auth.Credentials;
import de.doridian.steammobile.auth.impl.FlatFileAuthStorage;
import de.doridian.steammobile.connection.MessageHandler;
import de.doridian.steammobile.connection.MessageListener;
import de.doridian.steammobile.connection.SteamConnection;
import de.doridian.steammobile.connection.WebConnection;
import de.doridian.steammobile.connection.exceptions.InvalidSteamguardTokenException;
import de.doridian.steammobile.connection.exceptions.LoginException;
import de.doridian.steammobile.connection.exceptions.RequireSteamguardTokenException;
import de.doridian.steammobile.friend.Friend;
import de.doridian.steammobile.friend.Group;
import de.doridian.steammobile.messages.Message;
import de.doridian.steammobile.messages.TextMessage;
import de.doridian.steammobile.methods.RequestException;

import java.io.File;
import java.util.Scanner;

public class Main {
	public static final Scanner input = new Scanner(System.in);

	private static final String AUTHFILE = "auth.cfg";

	public static SteamConnection connection;
	public static MessageHandler handler;
	public static WebConnection webConnection;

	public static boolean isRunning = true;
	
	public static void main(String[] args) {
		connect();
	}

	private static void tryLogin(Credentials credentials, AuthStorage storage) {
		try {
			connection.tryLogin(credentials, storage);
		} catch(LoginException e) {
			if(e instanceof InvalidSteamguardTokenException) {
				System.out.println("Invalid steam guard token.");
				System.out.print("Retry (Y/N)? ");
				if(Main.input.nextLine().toLowerCase().startsWith("n")) {
					System.exit(0);
				} else {
					credentials.steamguard_token = null;
					tryLogin(credentials, storage);
				}
			} else if(e instanceof RequireSteamguardTokenException) {
				System.out.print("Please enter the steam guard code you received: ");
				String token = Main.input.nextLine();
				if(token.isEmpty()) {
					System.exit(0);
				} else {
					credentials.steamguard_token = token;
					tryLogin(credentials, storage);
				}
			} else {
				System.out.println("Unknown error: " + e.getMessage());
			}
		}
	}

	public static void connect() {
		Credentials credentials;
		AuthStorage storage = new FlatFileAuthStorage(new File(AUTHFILE));
		credentials = storage.getCredentials();
		if(credentials == null) {
			credentials = new Credentials();
			System.out.print("Username: ");
			credentials.username = input.nextLine();
			System.out.print("Password: ");
			credentials.password = input.nextLine();
		}

		try {
			connection = new SteamConnection();
			tryLogin(credentials, storage);

			webConnection = new WebConnection(connection);
			webConnection.login();

			handler = new MessageHandler(connection);
			handler.logon();

			connection.loadFriendList();
			connection.loadFriendDetails();
			System.out.println("Done friends!");

			connection.loadGroupList();
			connection.loadGroupDetails();
			System.out.println("Done groups!");

			for(Group group : connection.groups.values()) {
				System.out.println(group.steamid + " = " + group.name);
			}

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
