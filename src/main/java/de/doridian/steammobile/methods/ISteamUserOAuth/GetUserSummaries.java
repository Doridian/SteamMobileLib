package de.doridian.steammobile.methods.ISteamUserOAuth;

import de.doridian.steammobile.SteamConnection;
import de.doridian.steammobile.methods.BaseMethod;
import de.doridian.steammobile.methods.RequestException;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GetUserSummaries extends BaseMethod {
	public GetUserSummaries(SteamConnection connection) {
		connection.addAuthData(this, true, true, false);
	}
	
	private String steamIDs;
	public void setSteamIDs(String sids) {
		steamIDs = sids;
	}

	@Override
	public boolean isPOST() {
		return false;
	}

	@Override
	public JSONObject send() throws RequestException {
		Map<String, String> params = new HashMap<String,String>();
		params.put("steamids", steamIDs);
		return doRequest(params);
	}
}
