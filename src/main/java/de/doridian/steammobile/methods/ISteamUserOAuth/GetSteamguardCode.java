package de.doridian.steammobile.methods.ISteamUserOAuth;

import de.doridian.steammobile.connection.SteamConnection;
import de.doridian.steammobile.methods.BaseMethod;

public class GetSteamguardCode extends BaseMethod {
	public GetSteamguardCode(SteamConnection connection) {
		connection.addAuthData(this, false, true, false);
	}

	@Override
	public boolean isPOST() {
		return false;
	}
}
