package de.doridian.steammobile.methods.ISteamUserOAuth;

import de.doridian.steammobile.connection.SteamConnection;
import de.doridian.steammobile.methods.BaseMethod;
import de.doridian.steammobile.methods.RequestException;
import org.json.simple.JSONObject;

import java.util.Collection;
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

	public void setSteamIDs(Collection<String> sids) {
		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		for(String s : sids) {
			if(isFirst)
				isFirst = false;
			else
				sb.append(',');

			sb.append(s);
		}
		steamIDs = sb.toString();
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
