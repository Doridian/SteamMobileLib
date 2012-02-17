package de.doridian.steammobile.methods.api.ISteamWebUserPresenceOAuth;

import de.doridian.steammobile.connection.SteamConnection;
import de.doridian.steammobile.methods.api.BaseAPIMethod;
import de.doridian.steammobile.methods.RequestException;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Poll extends BaseAPIMethod {
	public Poll(SteamConnection connection) {
		connection.addAuthData(this, true, true);
		connection.addUmqid(this);
	}

	private String lastMessageID;
	public void setLastMessageID(String lastMessageID) {
		this.lastMessageID = lastMessageID;
	}

	@Override
	public JSONObject send() throws RequestException {
		Map<String, String> params = new HashMap<String,String>();
		params.put("message", lastMessageID);
		return doRequest(params);
	}
}
