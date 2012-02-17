package de.doridian.steammobile.methods.api.ISteamUserOAuth;

import de.doridian.steammobile.connection.SteamConnection;
import de.doridian.steammobile.methods.api.BaseAPIMethod;

public class GetSteamguardCode extends BaseAPIMethod {
	public GetSteamguardCode(SteamConnection connection) {
		connection.addAuthData(this, false, true);
	}

	@Override
	public boolean isPOST() {
		return false;
	}
}
