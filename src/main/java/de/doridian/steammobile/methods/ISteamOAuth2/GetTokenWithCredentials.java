package de.doridian.steammobile.methods.ISteamOAuth2;

import de.doridian.steammobile.methods.BaseAPIMethod;
import de.doridian.steammobile.methods.RequestException;
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
	public JSONObject send() throws RequestException {
		Map<String, String> params = new HashMap<String,String>();
		params.put("client_id", CLIENTID);
		params.put("grant_type", "password");
		params.put("username", username);
		params.put("password", password);
		if(token != null)
			params.put("x_emailauthcode", token);
		params.put("scope", "read_profile write_profile read_client write_client");
		return doRequest(params);
	}
}
