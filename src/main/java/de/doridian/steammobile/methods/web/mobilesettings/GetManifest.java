package de.doridian.steammobile.methods.web.mobilesettings;

import de.doridian.steammobile.connection.SteamConnection;
import de.doridian.steammobile.methods.web.BaseWebMethod;

import java.net.MalformedURLException;
import java.net.URL;

public class GetManifest extends BaseWebMethod {
	public GetManifest(SteamConnection connection) {
		connection.addAuthData(this, true, true);
	}

	@Override
	public boolean isPOST() {
		return false;
	}

	@Override
	protected String getCookies() {
		return "forceMobile=1;mobileClient=ios;mobileClientVersion=1291812;Steam_Language=english;steamLogin="+steamid+"||oauth:"+access_token;
	}

	@Override
	public URL getURL() throws MalformedURLException {
		return new URL(super.getURL() + "/v0001");
	}
}
