package de.doridian.steammobile.methods;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public abstract class BaseMethod {
	public JSONObject send() throws RequestException {
		return doRequest(null);
	}

	public boolean isPOST() {
		return true;
	}

	public boolean isSSL() {
		return true;
	}

	public abstract URL getBaseURL() throws MalformedURLException;

	public abstract URL getURL() throws MalformedURLException;

	protected JSONObject doRequest(Map<String, String> data) throws RequestException {
		try {
			URL url = getURL();
			HttpURLConnection conn;

			if(isPOST()) {
				conn = (HttpURLConnection)url.openConnection();
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);

				OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
				if(data != null) {
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
				}
				writer.close();
			} else {
				if(data != null) {
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
				}
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
