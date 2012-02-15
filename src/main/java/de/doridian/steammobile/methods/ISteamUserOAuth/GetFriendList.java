package de.doridian.steammobile.methods.ISteamUserOAuth;

import de.doridian.steammobile.SteamConnection;
import de.doridian.steammobile.methods.BaseMethod;

public class GetFriendList extends BaseMethod {
	public GetFriendList(SteamConnection connection) {
		connection.addAuthData(this, true, true, false);
	}
}
