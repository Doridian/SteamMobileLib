package de.doridian.steammobile.methods.ISteamUserOAuth;

import de.doridian.steammobile.SteamConnection;
import de.doridian.steammobile.methods.BaseMethod;

public class GetUserSummaries extends BaseMethod {
	public GetUserSummaries(SteamConnection connection) {
		connection.addAuthData(this, true, true, false);
	}
}
