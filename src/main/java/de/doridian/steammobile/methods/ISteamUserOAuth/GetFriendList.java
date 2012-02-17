package de.doridian.steammobile.methods.ISteamUserOAuth;

import de.doridian.steammobile.connection.SteamConnection;
import de.doridian.steammobile.methods.BaseAPIMethod;

public class GetFriendList extends BaseAPIMethod {
	public GetFriendList(SteamConnection connection) {
		connection.addAuthData(this, true, true, false);
	}

	@Override
	public boolean isPOST() {
		return false;
	}
}
