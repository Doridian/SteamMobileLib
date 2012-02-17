package de.doridian.steammobile.methods.api.ISteamWebUserPresenceOAuth;

import de.doridian.steammobile.connection.SteamConnection;
import de.doridian.steammobile.methods.api.BaseAPIMethod;

public class Logoff extends BaseAPIMethod {
	public Logoff(SteamConnection connection) {
		connection.addAuthData(this, false, true, true);
	}
}
