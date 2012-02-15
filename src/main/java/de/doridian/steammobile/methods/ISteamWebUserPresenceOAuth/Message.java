package de.doridian.steammobile.methods.ISteamWebUserPresenceOAuth;

import de.doridian.steammobile.SteamConnection;
import de.doridian.steammobile.methods.BaseMethod;
import de.doridian.steammobile.methods.RequestException;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Message extends BaseMethod {
	public Message(SteamConnection connection) {
		connection.addAuthData(this, false, true, true);
	}
	
	private String type = "saytext";
	public void setType(String type) {
		this.type = type;
	}
	
	private String message = null;
	public void setMessage(String message) {
		this.message = message;
	}
	
	private String destination;
	public void setDestination(String destination) {
		this.destination = destination;
	}

	@Override
	public JSONObject send() throws RequestException {
		Map<String, String> params = new HashMap<String,String>();
		params.put("type", type);
		params.put("steamid_dst", destination);
		if(message != null)
			params.put("text", message);
		return doRequest(params);
	}
}
