package de.doridian.steammobile.methods.ISteamWebUserPresenceOAuth;

import de.doridian.steammobile.connection.SteamConnection;
import de.doridian.steammobile.methods.BaseMethod;

public class Logon extends BaseMethod {
	public Logon(SteamConnection connection) {
		connection.addAuthData(this, false, true, true);
	}
}
