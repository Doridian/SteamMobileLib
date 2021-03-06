package de.doridian.steammobile.methods;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseMethod {
	public JSONObject send() throws RequestException {
		return doRequest(null);
	}

	protected String steamid = null;
	public void setSteamID(String steamid) {
		this.steamid = steamid;
	}

	protected String access_token = null;
	public void setAccessToken(String access_token) {
		this.access_token = access_token;
	}

	public boolean isPOST() {
		return true;
	}

	public boolean isSSL() {
		return true;
	}

	public abstract URL getBaseURL() throws MalformedURLException;

	public abstract URL getURL() throws MalformedURLException;

	protected String getCookies() {
		return null;
	}

	@SuppressWarnings("unchecked")
	protected JSONObject doRequest(Map<String, String> data) throws RequestException {
		if(data == null) {
			data = new HashMap<String, String>();
		}

		try {
			System.setProperty("http.agent", "");

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			OutputStreamWriter writer = new OutputStreamWriter(outputStream);
			boolean isFirst = true;
			for(Map.Entry<String, String> param : data.entrySet()) {
				if(isFirst)
					isFirst = false;
				else
					writer.write('&');

				writer.write(URLEncoder.encode(param.getKey(), "UTF-8"));
				writer.write('=');
				writer.write(URLEncoder.encode(param.getValue(), "UTF-8"));
			}
			writer.close();

			URL url = getURL();
			boolean post = isPOST();

			if(!post) {
				StringBuilder sb = new StringBuilder();
				sb.append(url);
				sb.append('?');
				sb.append(outputStream);
				url = new URL(sb.toString());
			}

			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestProperty("User-Agent", "Steam App / Android / 1.0 / 1297579");

			if(post) {
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.setRequestProperty("Content-Length", String.valueOf(outputStream.size()));
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);

				OutputStream stream = conn.getOutputStream();
				outputStream.writeTo(stream);
				stream.close();
			}
			
			String cookies = getCookies();
			if(cookies != null) {
				conn.setRequestProperty("cookie", cookies);
			}

			InputStreamReader reader = new InputStreamReader(conn.getInputStream());
			JSONObject ret = (JSONObject)((new JSONParser()).parse(reader));
			reader.close();

			if(ret.containsKey("error") && !ret.get("error").toString().toLowerCase().equals("ok")) {
				throw new RequestException((String)ret.get("error"), (String)ret.get("x_errorcode"), (String)ret.get("error_description"));
			}
			
			cookies = conn.getHeaderField("set-cookie");
			if(cookies != null) {
				ret.put("cookie", cookies);
			}

			return ret;
		} catch(RequestException e) {
			throw e;
		} catch(Exception e) {
			return null;
		}
	}
}
