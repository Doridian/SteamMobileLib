package de.doridian.steammobile.methods.api.ISteamUserOAuth;

import de.doridian.steammobile.connection.SteamConnection;
import de.doridian.steammobile.methods.api.BaseAPIMethod;

public class GetGroupList extends BaseAPIMethod {
	public GetGroupList(SteamConnection connection) {
		connection.addAuthData(this, true, true, false);
	}

	@Override
	public boolean isPOST() {
		return false;
	}
}
