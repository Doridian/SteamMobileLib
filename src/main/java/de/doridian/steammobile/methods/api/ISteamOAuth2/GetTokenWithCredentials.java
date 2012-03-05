package de.doridian.steammobile.methods.api.ISteamOAuth2;

import de.doridian.steammobile.methods.RequestException;
import de.doridian.steammobile.methods.api.BaseAPIMethod;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GetTokenWithCredentials extends BaseAPIMethod {
	private static final String CLIENTID = "DE45CD61";
	
	private String username;
	private String password;
	private String token;
	
	public GetTokenWithCredentials(String username, String password) {
		this(username, password, null);
	}
	
	public GetTokenWithCredentials(String username, String password, String token) {
		this.username = username;
		this.password = password;
		this.token = token;
	}

	@Override
	public boolean isPOST() {
		return true;
	}

	@Override
	public JSONObject send() throws RequestException {
		Map<String, String> params = new HashMap<String,String>();
		params.put("client_id", CLIENTID);

		params.put("grant_type", "password");

		params.put("username", username);
		params.put("password", password);

		if(token != null && !token.equals("null"))
			params.put("x_emailauthcode", token);
		else
			params.put("x_emailauthcode", "");
		
		params.put("x_webcookie", "");
		
		params.put("scope", "read_profile write_profile read_client write_client");
		return doRequest(params);
	}
}
