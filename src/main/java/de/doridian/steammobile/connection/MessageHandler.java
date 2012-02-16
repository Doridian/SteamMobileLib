package de.doridian.steammobile.connection;

import de.doridian.steammobile.connection.SteamConnection;
import de.doridian.steammobile.messages.Message;
import de.doridian.steammobile.messages.PersonaStateMessage;
import de.doridian.steammobile.messages.TextMessage;
import de.doridian.steammobile.messages.TypingMessage;
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

	public MessageHandler(SteamConnection connection) {
		this.connection = connection;
	}

	public void logon() throws RequestException {
		Logon msg = new Logon(connection);
		lastMessageID = msg.send().get("message").toString();
	}
	
	public void sendMessage(Message message) throws RequestException {
		de.doridian.steammobile.methods.ISteamWebUserPresenceOAuth.Message msg = new de.doridian.steammobile.methods.ISteamWebUserPresenceOAuth.Message(connection);
		msg.setMessage(message);
		msg.send();
	}

	public List<Message> getMessages() throws RequestException {
		Poll msg = new Poll(connection);
		msg.setLastMessageID(lastMessageID);
		List<Message> ret = new ArrayList<Message>();
		try {
			JSONObject obj = msg.send();

			if(obj.containsKey("messagelast")) {
				lastMessageID = obj.get("messagelast").toString();
			}

			if(!obj.containsKey("messages")) return ret;
			JSONArray arr = (JSONArray)obj.get("messages");
			for(Object ent : arr) {
				JSONObject entj = (JSONObject)ent;
				Message recvd = Message.craftMessageFromJSON(entj);
				if(recvd != null) {
					ret.add(recvd);
				} else {
					System.out.println("Unknown message: " + entj.toJSONString());
				}
			}
			return ret;
		} catch(RequestException e) {
			if(e.error.equalsIgnoreCase("Timeout")) {
				return ret;
			}
			throw e;
		}
	}
}
