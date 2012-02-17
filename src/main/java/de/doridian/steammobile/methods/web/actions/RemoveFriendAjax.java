package de.doridian.steammobile.methods.web.actions;

import de.doridian.steammobile.connection.SteamConnection;
import de.doridian.steammobile.methods.RequestException;
import de.doridian.steammobile.methods.web.BaseWebMethod;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RemoveFriendAjax extends BaseWebMethod {
	public RemoveFriendAjax(SteamConnection connection) {
		connection.addAuthData(this, true, true);
	}

	private String steamid_other = null;
	public void setSteamIDOther(String steamid_other) {
		this.steamid_other = steamid_other;
	}

	@Override
	protected JSONObject doRequest(Map<String, String> data) throws RequestException {
		if(data == null) {
			data = new HashMap<String, String>();
		}

		if(steamid_other != null) {
			data.put("steamid", steamid_other);
		}

		return super.doRequest(data);
	}
}
