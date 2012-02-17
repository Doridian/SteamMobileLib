package de.doridian.steammobile.methods.api;

import de.doridian.steammobile.connection.SteamConnection;
import de.doridian.steammobile.methods.BaseWebMethod;
import de.doridian.steammobile.methods.RequestException;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddFriendAjax extends BaseWebMethod {
	public AddFriendAjax(SteamConnection connection) {
		connection.addSessionID(this);
	}

	private String steamid = null;
	public void setSteamID(String steamid) {
		this.steamid = steamid;
	}

	@Override
	protected JSONObject doRequest(Map<String, String> data) throws RequestException {
		if(data == null) {
			data = new HashMap<String, String>();
		}

		if(steamid != null) {
			data.put("steamid", steamid);
		}

		return super.doRequest(data);
	}
}
