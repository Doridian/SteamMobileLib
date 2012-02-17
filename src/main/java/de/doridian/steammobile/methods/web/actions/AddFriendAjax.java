package de.doridian.steammobile.methods.web.actions;

import de.doridian.steammobile.connection.SteamConnection;
import de.doridian.steammobile.methods.RequestException;
import de.doridian.steammobile.methods.web.BaseWebMethod;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddFriendAjax extends BaseWebMethod {
	public AddFriendAjax(SteamConnection connection) {
		connection.addAuthData(this, true, true);
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
