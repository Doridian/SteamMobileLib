package de.doridian.steammobile.methods;

import org.json.simple.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseWebMethod extends BaseMethod {
	private static final String BASEURL = "http://steamcommunity.com/actions/";
	private static final String BASEURL_SSL = "https://steamcommunity.com/actions/";

	private String sessionid = null;
	public void setSessionID(String sessionid) {
		this.sessionid = sessionid;
	}

	@Override
	public URL getBaseURL() throws MalformedURLException {
		if(isSSL()) {
			return new URL(BASEURL_SSL);
		} else {
			return new URL(BASEURL);
		}
	}

	@Override
	public URL getURL() throws MalformedURLException {
		return new URL(getBaseURL(), this.getClass().getSimpleName());
	}

	@Override
	protected JSONObject doRequest(Map<String, String> data) throws RequestException {
		if(data == null) {
			data = new HashMap<String, String>();
		}

		if(this.sessionid != null) {
			data.put("sessionID", sessionid);
		}

		return super.doRequest(data);
	}
}
