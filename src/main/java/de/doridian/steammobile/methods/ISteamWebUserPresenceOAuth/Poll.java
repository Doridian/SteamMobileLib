package de.doridian.steammobile.methods.ISteamWebUserPresenceOAuth;

import de.doridian.steammobile.SteamConnection;
import de.doridian.steammobile.methods.BaseMethod;
import org.json.simple.JSONObject;

public class Poll extends BaseMethod {
	public Poll(SteamConnection connection) {
		connection.addAuthData(this, true, true, true);
	}

	@Override
	public JSONObject send() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
