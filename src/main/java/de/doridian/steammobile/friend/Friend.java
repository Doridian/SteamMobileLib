package de.doridian.steammobile.friend;

import de.doridian.steammobile.connection.SteamConnection;
import de.doridian.steammobile.methods.RequestException;
import de.doridian.steammobile.methods.api.ISteamUserOAuth.GetGroupSummaries;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Friend {
	public enum State {
		OFFLINE(0),
		ONLINE(1),
		BUSY(2),
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

	private final SteamConnection connection;

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

	public HashMap<String, Object> infos = new HashMap<String, Object>();
	
	public Friend(SteamConnection connection, String steamid, String relationship, long friend_since) {
		this.steamid = steamid;
		this.relationship = relationship;
		this.friend_since = friend_since;
		this.connection = connection;
	}

	public void getFullStats() throws RequestException {
		getFullStats(false);
	}

	public void getFullStats(boolean force) throws RequestException {
		if(personaname != null && !force) {
			return;
		}

		GetGroupSummaries msg = new GetGroupSummaries(connection);
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
			avatar = new Avatar(friend);
		}

		if(friend.containsKey("primaryclanid")) {
			primarygroup = new Group(connection, friend.get("primaryclanid").toString());
		}
		if(friend.containsKey("timecreated")) {
			timecreated = Long.valueOf(friend.get("timecreated").toString());
		}

		infos.clear();
		for(Map.Entry<Object, Object> ent : (Set<Map.Entry<Object, Object>>)friend.entrySet()) {
			infos.put(ent.getKey().toString(), ent.getValue());
		}
	}
}
