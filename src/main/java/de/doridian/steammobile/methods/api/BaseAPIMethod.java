package de.doridian.steammobile.methods.api;

import de.doridian.steammobile.methods.BaseMethod;
import de.doridian.steammobile.methods.RequestException;
import org.json.simple.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseAPIMethod extends BaseMethod {
	private static final String BASEURL = "http://api.steampowered.com/";
	private static final String BASEURL_SSL = "https://api.steampowered.com/";

	private String steamid = null;
	public void setSteamID(String steamid) {
		this.steamid = steamid;
	}

	private String access_token = null;
	public void setAccessToken(String access_token) {
		this.access_token = access_token;
	}

	private String umqid = null;
	public void setUmqid(String umqid) {
		this.umqid = umqid;
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
		Package basePkg = BaseAPIMethod.class.getPackage();
		return new URL(getBaseURL(), pkg.getName().substring(basePkg.getName().length() + 1).replace('.', '/') + "/" + clazz.getSimpleName() + "/v0001");
	}
	
	@Override
	protected JSONObject doRequest(Map<String, String> data) throws RequestException {
		if(data == null) {
			data = new HashMap<String, String>();
		}

		if(steamid != null) {
			data.put("steamid", steamid);
		}
		if(access_token != null) {
			data.put("access_token", access_token);
		}
		if(umqid != null) {
			data.put("umqid", umqid);
		}

		return super.doRequest(data);
	}
}
