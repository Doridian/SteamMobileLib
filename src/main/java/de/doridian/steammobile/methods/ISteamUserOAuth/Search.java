package de.doridian.steammobile.methods.ISteamUserOAuth;

import de.doridian.steammobile.connection.SteamConnection;
import de.doridian.steammobile.methods.BaseMethod;
import de.doridian.steammobile.methods.RequestException;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Search extends BaseMethod {
	public Search(SteamConnection connection) {
		connection.addAuthData(this, false, true, false);
	}

	private String keywords;
	private long offset;
	private long count;
	private String targets = "users";
	private String fields = "all";

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public void setTargets(String targets) {
		this.targets = targets;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	@Override
	public JSONObject send() throws RequestException {
		Map<String, String> params = new HashMap<String,String>();
		params.put("keywords", keywords);
		params.put("offset", ""+offset);
		params.put("count", ""+count);
		params.put("targets", targets);
		params.put("fields", fields);
		return doRequest(params);
	}
}
