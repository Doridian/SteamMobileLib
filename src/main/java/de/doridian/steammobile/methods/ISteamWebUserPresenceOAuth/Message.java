package de.doridian.steammobile.methods.ISteamWebUserPresenceOAuth;

import de.doridian.steammobile.SteamConnection;
import de.doridian.steammobile.methods.BaseMethod;
import org.json.simple.JSONObject;

public class Message extends BaseMethod {
	public Message(SteamConnection connection) {
		connection.addAuthData(this, false, true, true);
	}

	@Override
	public JSONObject send() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
