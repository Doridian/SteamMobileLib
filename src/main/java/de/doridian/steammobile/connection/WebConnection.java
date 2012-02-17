package de.doridian.steammobile.connection;

import de.doridian.steammobile.methods.BaseMethod;
import de.doridian.steammobile.methods.RequestException;
import de.doridian.steammobile.methods.web.BaseWebMethod;
import de.doridian.steammobile.methods.web.mobilesettings.GetManifest;
import org.json.simple.JSONObject;

public class WebConnection {
	private String sessionid;

	private final SteamConnection connection;

	public WebConnection(SteamConnection connection) {
		this.connection = connection;
	}

	public void login() {
		GetManifest msg = new GetManifest(connection);
		try {
			JSONObject response = msg.send();
			String sid = response.get("cookie").toString();
			int x = sid.indexOf("sessionid=") + "sessionid=".length();
			sessionid = sid.substring(x, sid.indexOf(';', x));
		} catch(RequestException e) {
			e.printStackTrace();
		}
	}

	public void addSessionID(BaseWebMethod method) {
		method.setSessionID(sessionid);
	}

	public void addAuthData(BaseMethod method, boolean addSteamid, boolean addToken) {
		connection.addAuthData(method, addSteamid, addToken);
	}
}
