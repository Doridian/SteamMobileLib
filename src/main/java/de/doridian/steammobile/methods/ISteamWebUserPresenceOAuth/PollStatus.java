package de.doridian.steammobile.methods.ISteamWebUserPresenceOAuth;

import de.doridian.steammobile.connection.SteamConnection;
import de.doridian.steammobile.methods.BaseAPIMethod;
import de.doridian.steammobile.methods.RequestException;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PollStatus extends BaseAPIMethod {
	public PollStatus(SteamConnection connection) {
		connection.addAuthData(this, true, false, true);
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
