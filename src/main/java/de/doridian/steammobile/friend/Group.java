package de.doridian.steammobile.friend;

import de.doridian.steammobile.connection.SteamConnection;
import de.doridian.steammobile.methods.RequestException;
import de.doridian.steammobile.methods.api.ISteamUserOAuth.GetGroupSummaries;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Group {
	private final SteamConnection connection;

	public final String steamid;

	public String name;
	public Avatar avatar;
	
	public HashMap<String, Object> infos = new HashMap<String, Object>();

	public Group(SteamConnection connection, String gid) {
		steamid = gid;
		this.connection = connection;
	}

	public void getFullStats() throws RequestException {
			getFullStats(false);
	}

	public void getFullStats(boolean force) throws RequestException {
		if(name != null && !force) {
			return;
		}

		GetGroupSummaries msg = new GetGroupSummaries(connection);
		msg.setSteamIDs(this.steamid);
		setFullStats((JSONObject)(((JSONArray)msg.send().get("groups")).get(0)));
	}

	public void setFullStats(JSONObject group) {
		if(group.containsKey("avatar")) {
			avatar = new Avatar(group);
		}

		name = group.get("name").toString();

		infos.clear();
		for(Map.Entry<Object, Object> ent : (Set<Map.Entry<Object, Object>>)group.entrySet()) {
			infos.put(ent.getKey().toString(), ent.getValue());
		}
	}
}
