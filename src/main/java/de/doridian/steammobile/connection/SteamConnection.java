package de.doridian.steammobile.connection;

import de.doridian.steammobile.Main;
import de.doridian.steammobile.connection.exceptions.InvalidSteamguardTokenException;
import de.doridian.steammobile.connection.exceptions.LoginException;
import de.doridian.steammobile.connection.exceptions.RequireSteamguardTokenException;
import de.doridian.steammobile.friend.Friend;
import de.doridian.steammobile.friend.Group;
import de.doridian.steammobile.methods.BaseMethod;
import de.doridian.steammobile.methods.ISteamOAuth2.GetTokenWithCredentials;
import de.doridian.steammobile.methods.ISteamUserOAuth.GetFriendList;
import de.doridian.steammobile.methods.ISteamUserOAuth.GetGroupList;
import de.doridian.steammobile.methods.ISteamUserOAuth.GetGroupSummaries;
import de.doridian.steammobile.methods.ISteamUserOAuth.GetUserSummaries;
import de.doridian.steammobile.methods.RequestException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public class SteamConnection {
	private String steamid;
	private String access_token;
	private String umqid;

	public static final Random random = new Random();

	public Map<String, Friend> friends = new HashMap<String, Friend>();
	public Map<String, Group> groups = new HashMap<String, Group>();

	public SteamConnection() {
		long x = random.nextLong();
		if(x < 0) x = -x;
		umqid = String.valueOf(x);
	}

	public void loadFriendList() throws RequestException {
		GetFriendList msg = new GetFriendList(this);
		try {
			JSONObject ret = msg.send();
			JSONArray arr = (JSONArray)ret.get("friends");
			friends.clear();
			for(Object ent : arr) {
				JSONObject entj = (JSONObject)ent;
				Friend friend = new Friend(this, entj.get("steamid").toString(), entj.get("relationship").toString(), Long.valueOf(entj.get("friend_since").toString()));
				friends.put(friend.steamid, friend);
			}
		} catch(RequestException e) {
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void loadGroupList() throws RequestException {
		GetGroupList msg = new GetGroupList(this);
		try {
			JSONObject ret = msg.send();
			JSONArray arr = (JSONArray)ret.get("groups");
			friends.clear();
			for(Object ent : arr) {
				JSONObject entj = (JSONObject)ent;
				Group group = new Group(this, entj.get("steamid").toString());
				groups.put(group.steamid, group);
			}
		} catch(RequestException e) {
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static final int MAX_DETAILS_PER_REQUEST = 90;

	public void loadFriendDetails() throws RequestException {
		GetUserSummaries msg = new GetUserSummaries(this);
		try {
			Set<String> tsids = friends.keySet();
			String[] sids = tsids.toArray(new String[tsids.size()]);
			for(int i = 0; i < sids.length; i += MAX_DETAILS_PER_REQUEST) {
				tsids = new HashSet<String>();
				for(int j = 0; j < MAX_DETAILS_PER_REQUEST; j++) {
					int k = i + j;
					if(k >= sids.length) break;
					tsids.add(sids[k]);
				}
				msg.setSteamIDs(tsids);
				JSONArray res = (JSONArray)msg.send().get("players");
				for(Object ent : res) {
					JSONObject entj = (JSONObject)ent;
					friends.get(entj.get("steamid").toString()).setFullStats(entj);
				}
			}
		} catch(RequestException e) {
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void loadGroupDetails() throws RequestException {
		GetGroupSummaries msg = new GetGroupSummaries(this);
		try {
			Set<String> tsids = groups.keySet();
			String[] sids = tsids.toArray(new String[tsids.size()]);
			for(int i = 0; i < sids.length; i += MAX_DETAILS_PER_REQUEST) {
				tsids = new HashSet<String>();
				for(int j = 0; j < MAX_DETAILS_PER_REQUEST; j++) {
					int k = i + j;
					if(k >= sids.length) break;
					tsids.add(sids[k]);
				}
				msg.setSteamIDs(tsids);
				JSONArray res = (JSONArray)msg.send().get("groups");
				for(Object ent : res) {
					JSONObject entj = (JSONObject)ent;
					groups.get(entj.get("steamid").toString()).setFullStats(entj);
				}
			}
		} catch(RequestException e) {
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void tryLogin(String username, String password, String token) throws LoginException {
		GetTokenWithCredentials request = new GetTokenWithCredentials(username, password, token);

		JSONObject ret;
		try {
			ret = request.send();
			if(ret == null) return;
		} catch(RequestException e) {
			if(e.errorCode.equalsIgnoreCase("invalid_steamguard_code")) {
				throw new InvalidSteamguardTokenException();
			} else if(e.errorCode.equalsIgnoreCase("steamguard_code_required")) {
				throw new RequireSteamguardTokenException();
			} else {
				throw new LoginException(e.errorCode);
			}
		}

		steamid = (String)ret.get("x_steamid");
		access_token = (String)ret.get("access_token");
	}

	public void addAuthData(BaseMethod method, boolean addSteamid, boolean addToken, boolean addUmqid) {
		if(addSteamid)
			method.setSteamID(steamid);
		if(addToken)
			method.setAccessToken(access_token);
		if(addUmqid)
			method.setUmqid(umqid);
	}
}
