package de.doridian.steammobile;

import de.doridian.steammobile.methods.BaseMethod;
import de.doridian.steammobile.methods.ISteamOAuth2.GetTokenWithCredentials;
import de.doridian.steammobile.methods.RequestException;
import org.json.simple.JSONObject;

import java.util.Scanner;

public class SteamConnection {
	private String steamid;
	private String access_token;
	private String umqid;

	public SteamConnection(String username, String password) {
		this(username, password, null);
	}

	public SteamConnection(String username, String password, String token) {
		tryLogin(username, password, token);
	}

	private void tryLogin(String username, String password, String token) {
		GetTokenWithCredentials request = new GetTokenWithCredentials(username, password, token);
		try {
			JSONObject ret = request.send();
			System.out.println(ret.toJSONString());
		} catch(RequestException e) {
			if(e.errorCode.equalsIgnoreCase("invalid_steamguard_code")) {
				System.out.println("Invalid steam guard token.");
				System.out.print("Retry (Y/N)? ");
				if(Main.input.nextLine().toLowerCase().startsWith("n")) {
					System.exit(0);
				} else {
					tryLogin(username, password, null);
				}
			} else if(e.errorCode.equalsIgnoreCase("steamguard_code_required")) {
				System.out.print("Please enter the steam guard code you received: ");
				token = Main.input.nextLine();
				if(token.isEmpty()) {
					System.exit(0);
				} else {
					tryLogin(username, password, token);
				}
			} else {
				System.out.println("Unknown error: " + e.errorCode);
			}
		}
	}
}
