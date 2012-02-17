package de.doridian.steammobile.methods.api.ISteamUserOAuth;

import de.doridian.steammobile.connection.SteamConnection;
import de.doridian.steammobile.methods.api.BaseAPIMethod;
import de.doridian.steammobile.methods.RequestException;
import org.json.simple.JSONObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GetGroupSummaries extends BaseAPIMethod {
	public GetGroupSummaries(SteamConnection connection) {
		connection.addAuthData(this, true, true);
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
