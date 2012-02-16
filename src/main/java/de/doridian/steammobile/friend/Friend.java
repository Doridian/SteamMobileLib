package de.doridian.steammobile.friend;

import de.doridian.steammobile.connection.SteamConnection;
import de.doridian.steammobile.methods.ISteamUserOAuth.GetUserSummaries;
import de.doridian.steammobile.methods.RequestException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Friend {
	enum State {
		OFFLINE(0),
		ONLINE(1),
		INGAME(2),
		AWAY(3),
		SNOOZE(4),
		INVALID(-1);

		int sid;
		State(int id) {
			sid = id;
		}

		public static State getByID(int id) {
			for(State s : State.values()) {
				if(s.sid == id) return s;
			}
			return State.INVALID;
		}
		
		public int getID() {
			return sid;
		}
	}

	//Filled always
	public final String steamid;
	public String relationship;
	public long friend_since;

	//Only filled by getFullStats
	public Avatar avatar = null;

	public String personaname = null;
	public String profileurl = null;
	
	public State personastate = null;
	
	public Group primarygroup = null;
	public long timecreated = 0;
	
	public Friend(String steamid, String relationship, long friend_since) {
		this.steamid = steamid;
		this.relationship = relationship;
		this.friend_since = friend_since;
	}

	public void getFullStats(SteamConnection connection) throws RequestException {
		getFullStats(connection, false);
	}

	public void getFullStats(SteamConnection connection, boolean force) throws RequestException {
		if(personaname != null && !force) {
			return;
		}

		GetUserSummaries msg = new GetUserSummaries(connection);
		msg.setSteamIDs(this.steamid);
		setFullStats((JSONObject)(((JSONArray)msg.send().get("players")).get(0)));
	}

	public void setFullStats(JSONObject friend) {
		personaname = friend.get("personaname").toString();
		if(friend.containsKey("personastate")) {
			personastate = State.getByID(Integer.valueOf(friend.get("personastate").toString()));
		}

		if(friend.containsKey("profileurl")) {
			profileurl = friend.get("profileurl").toString();
		}

		if(friend.containsKey("avatar")) {
			avatar = new Avatar(friend.get("avatar").toString(), friend.get("avatarmedium").toString(), friend.get("avatarfull").toString());
		}

		if(friend.containsKey("primaryclanid")) {
			primarygroup = new Group(friend.get("primaryclanid").toString());
		}
		if(friend.containsKey("timecreated")) {
			timecreated = Long.valueOf(friend.get("timecreated").toString());
		}
	}
}
