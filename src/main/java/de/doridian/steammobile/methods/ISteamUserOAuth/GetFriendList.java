package de.doridian.steammobile.methods.ISteamUserOAuth;

import de.doridian.steammobile.SteamConnection;
import de.doridian.steammobile.methods.BaseMethod;
import org.json.simple.JSONObject;

public class GetFriendList extends BaseMethod {
	public GetFriendList(SteamConnection connection) {
		connection.addAuthData(this, true, true, false);
	}

	@Override
	public JSONObject send() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
