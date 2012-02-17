package de.doridian.steammobile.methods.ISteamUserOAuth;

import de.doridian.steammobile.connection.SteamConnection;
import de.doridian.steammobile.methods.BaseAPIMethod;

public class GetSteamguardCode extends BaseAPIMethod {
	public GetSteamguardCode(SteamConnection connection) {
		connection.addAuthData(this, false, true, false);
	}

	@Override
	public boolean isPOST() {
		return false;
	}
}
