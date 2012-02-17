package de.doridian.steammobile.methods.ISteamUserOAuth;

import de.doridian.steammobile.connection.SteamConnection;
import de.doridian.steammobile.methods.BaseAPIMethod;

public class GetGroupList extends BaseAPIMethod {
	public GetGroupList(SteamConnection connection) {
		connection.addAuthData(this, true, true, false);
	}

	@Override
	public boolean isPOST() {
		return false;
	}
}
