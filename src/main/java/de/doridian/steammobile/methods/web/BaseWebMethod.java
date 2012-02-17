package de.doridian.steammobile.methods.web;

import de.doridian.steammobile.methods.BaseMethod;
import de.doridian.steammobile.methods.RequestException;
import org.json.simple.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseWebMethod extends BaseMethod {
	private static final String BASEURL = "http://steamcommunity.com/";
	private static final String BASEURL_SSL = "https://steamcommunity.com/";

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
		Class clazz = this.getClass();
		Package pkg = clazz.getPackage();
		Package basePkg = BaseWebMethod.class.getPackage();
		return new URL(getBaseURL(), pkg.getName().substring(basePkg.getName().length() + 1).replace('.', '/') + "/" + clazz.getSimpleName());
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
