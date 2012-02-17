package de.doridian.steammobile.methods.ISteamWebUserPresenceOAuth;

import de.doridian.steammobile.connection.SteamConnection;
import de.doridian.steammobile.methods.BaseAPIMethod;

public class Logon extends BaseAPIMethod {
	public Logon(SteamConnection connection) {
		connection.addAuthData(this, false, true, true);
	}
}
