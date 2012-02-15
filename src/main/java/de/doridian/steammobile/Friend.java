package de.doridian.steammobile;

import de.doridian.steammobile.methods.ISteamUserOAuth.GetUserSummaries;
import de.doridian.steammobile.methods.RequestException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Friend {
	//Filled always
	public String steamid;
	public String relationship;
	public long friend_since;

	//Only filled by getFullStats
	public String personaname;
	public String profileurl;
	
	public String avatarurl;
	public String avatarmediumurl;
	public String avatarfullurl;
	
	public String personastate;
	
	public String primaryclanid;
	public long timecreated;
	
	public Friend(String steamid, String relationship, long friend_since) {
		this.steamid = steamid;
		this.relationship = relationship;
		this.friend_since = friend_since;
	}

	public void getFullStats(SteamConnection connection) throws RequestException {
		GetUserSummaries msg = new GetUserSummaries(connection);
		msg.setSteamIDs(this.steamid);
		JSONObject friend = (JSONObject)(((JSONArray)msg.send().get("players")).get(0));

		personaname = friend.get("personaname").toString();
		if(friend.containsKey("personastate")) {
			personastate = friend.get("personastate").toString();
		}

		if(friend.containsKey("profileurl")) {
			profileurl = friend.get("profileurl").toString();
		}

		if(friend.containsKey("avatar")) {
			avatarurl = friend.get("avatar").toString();
			avatarmediumurl = friend.get("avatarmedium").toString();
			avatarfullurl = friend.get("avatarfull").toString();
		}

		if(friend.containsKey("primaryclanid")) {
			primaryclanid = friend.get("primaryclanid").toString();
		}
		if(friend.containsKey("timecreated")) {
			timecreated = Long.valueOf(friend.get("timecreated").toString());
		}
	}
}
