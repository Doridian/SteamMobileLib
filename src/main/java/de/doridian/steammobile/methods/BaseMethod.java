package de.doridian.steammobile.methods;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public abstract class BaseMethod {
	private static final String BASEURL = "https://api.steampowered.com/";
	
	public abstract JSONObject send() throws RequestException;

	protected JSONObject doRequest(Map<String, String> data) throws RequestException {
		try {
			Class clazz = this.getClass();
			Package pkg = clazz.getPackage();
			Package basePkg = BaseMethod.class.getPackage();
	
			URL url = new URL(BASEURL + pkg.getName().substring(basePkg.getName().length() + 1).replace('.', '/') + "/" + clazz.getSimpleName() + "/v0001");
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
	
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
	
			InputStreamReader reader = new InputStreamReader(conn.getInputStream());
			JSONObject ret = (JSONObject)((new JSONParser()).parse(reader));
			reader.close();
			
			if(ret.containsKey("error")) {
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
