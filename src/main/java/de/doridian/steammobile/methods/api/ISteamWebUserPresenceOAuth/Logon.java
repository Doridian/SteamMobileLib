package de.doridian.steammobile.methods.api.ISteamWebUserPresenceOAuth;

import de.doridian.steammobile.connection.SteamConnection;
import de.doridian.steammobile.methods.api.BaseAPIMethod;

public class Logon extends BaseAPIMethod {
	public Logon(SteamConnection connection) {
		connection.addAuthData(this, false, true);
		connection.addUmqid(this);
	}
}
