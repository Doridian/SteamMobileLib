package de.doridian.steammobile;

import de.doridian.steammobile.messages.Message;
import de.doridian.steammobile.messages.TextMessage;
import de.doridian.steammobile.methods.ISteamUserOAuth.GetFriendList;
import de.doridian.steammobile.methods.ISteamWebUserPresenceOAuth.Logon;
import de.doridian.steammobile.methods.ISteamWebUserPresenceOAuth.Poll;
import de.doridian.steammobile.methods.RequestException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessageHandler {
	private SteamConnection connection;

	private String lastMessageID;
	
	public List<Friend> friends = new ArrayList<Friend>();

	public MessageHandler(SteamConnection connection) {
		this.connection = connection;
	}

	public void logon() {
		Logon msg = new Logon(connection);
		try {
			JSONObject ret = msg.send();
			lastMessageID = ret.get("message").toString();
		} catch(RequestException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void loadFriendsList() {
		GetFriendList msg = new GetFriendList(connection);
		try {
			JSONObject ret = msg.send();
			JSONArray arr = (JSONArray)ret.get("friends");
			friends.clear();
			for(Object ent : arr) {
				JSONObject entj = (JSONObject)ent;
				friends.add(new Friend(entj.get("steamid").toString(), entj.get("relationship").toString(), Long.valueOf(entj.get("friend_since").toString())));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public void sendMessage(String to, String message) {
		de.doridian.steammobile.methods.ISteamWebUserPresenceOAuth.Message msg = new de.doridian.steammobile.methods.ISteamWebUserPresenceOAuth.Message(connection);
		msg.setDestination(to);
		msg.setMessage(message);
		try {
			msg.send();
		} catch(RequestException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public List<Message> getMessages() {
		Poll msg = new Poll(connection);
		msg.setLastMessageID(lastMessageID);
		try {
			List<Message> ret = new ArrayList<Message>();
			JSONObject obj = msg.send();

			if(obj.containsKey("messagelast")) {
				lastMessageID = obj.get("messagelast").toString();
			}

			if(!obj.containsKey("messages")) return ret;
			JSONArray arr = (JSONArray)obj.get("messages");
			for(Object ent : arr) {
				JSONObject entj = (JSONObject)ent;
				String type = entj.get("type").toString().toLowerCase();
				if(type.equals("saytext")) {
					ret.add(new TextMessage(
							Long.valueOf(entj.get("timestamp").toString()),
							entj.get("steamid_from").toString(),
							entj.get("text").toString()
					));
				} else if(type.equals("personastate")) {
					//Ignored atm!
				} else if(type.equals("typing")) {
					//Ignored atm too!
				}
			}
			return ret;
		} catch(RequestException e) {
			e.printStackTrace();
			System.exit(0);
			return null;
		}
	}
}
