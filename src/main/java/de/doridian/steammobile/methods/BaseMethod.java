package de.doridian.steammobile.methods;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseMethod {
	private static final String BASEURL = "http://api.steampowered.com/";
	private static final String BASEURL_SSL = "https://api.steampowered.com/";

	public JSONObject send() throws RequestException {
		return doRequest(null);
	}

	private String steamid = null;
	private String access_token = null;
	private String umqid = null;
	public void setSteamID(String steamid) {
		this.steamid = steamid;
	}
	public void setAccessToken(String access_token) {
		this.access_token = access_token;
	}
	public void setUmqid(String umqid) {
		this.umqid = umqid;
	}
	
	public boolean isPOST() {
		return true;
	}

	public boolean isSSL() {
		return true;
	}
	
	public URL getBaseURL() throws MalformedURLException {
		if(isSSL()) {
			return new URL(BASEURL_SSL);
		} else {
			return new URL(BASEURL);
		}
	}

	public URL getURL() throws MalformedURLException {
		Class clazz = this.getClass();
		Package pkg = clazz.getPackage();
		Package basePkg = BaseMethod.class.getPackage();
		return new URL(getBaseURL(), pkg.getName().substring(basePkg.getName().length() + 1).replace('.', '/') + "/" + clazz.getSimpleName() + "/v0001");
	}
	
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

		try {
			URL url = getURL();
			HttpURLConnection conn;

			if(isPOST()) {
				conn = (HttpURLConnection)url.openConnection();
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);

				OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
				boolean isFirst = true;
				for(Map.Entry<String, String> param : data.entrySet()) {
					if(isFirst)
						isFirst = false;
					else
						writer.write('&');

					writer.write(URLEncoder.encode(param.getKey()));
					writer.write('=');
					writer.write(URLEncoder.encode(param.getValue()));
				}
				writer.close();
			} else {
				StringBuilder sb = new StringBuilder();
				sb.append(url);
				sb.append('?');
				boolean isFirst = true;
				for(Map.Entry<String, String> param : data.entrySet()) {
					if(isFirst)
						isFirst = false;
					else
						sb.append('&');

					sb.append(URLEncoder.encode(param.getKey()));
					sb.append('=');
					sb.append(URLEncoder.encode(param.getValue()));
				}
				url = new URL(sb.toString());
				conn = (HttpURLConnection)url.openConnection();
			}
			
			InputStreamReader reader = new InputStreamReader(conn.getInputStream());
			JSONObject ret = (JSONObject)((new JSONParser()).parse(reader));
			reader.close();

			if(ret.containsKey("error") && !ret.get("error").toString().toLowerCase().equals("ok")) {
				throw new RequestException((String)ret.get("error"), (String)ret.get("x_errorcode"), (String)ret.get("error_description"));
			}
			
			return ret;
		} catch(RequestException e) {
			throw e;
		} catch(Exception e) {
			return null;
		}
	}
}
