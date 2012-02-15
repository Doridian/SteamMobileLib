package de.doridian.steammobile.methods.ISteamUserOAuth;

import de.doridian.steammobile.SteamConnection;
import de.doridian.steammobile.methods.BaseMethod;

public class GetGroupList extends BaseMethod {
	public GetGroupList(SteamConnection connection) {
		connection.addAuthData(this, true, true, false);
	}
}
