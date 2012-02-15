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
	
	private de.doridian.steammobile.messages.Message message;
	public void setMessage(de.doridian.steammobile.messages.Message message) {
		this.message = message;
	}

	@Override
	public JSONObject send() throws RequestException {
		Map<String, String> params = new HashMap<String,String>();
		message.addParameters(params);
		params.put("type", message.getType());
		params.put("steamid_dst", message.steamid_other);
		return doRequest(params);
	}
}
