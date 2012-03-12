package de.doridian.steammobile.messages;

import de.doridian.steammobile.friend.Friend;
import org.json.simple.JSONObject;

import java.util.Map;

@Message.Type("personarelationship")
public class PersonaRelationshipMessage extends Message {
	public final int persona_state;
	public final String persona_name;
	public final int status_flags;

	public PersonaRelationshipMessage(JSONObject json) {
		super(json);
		this.persona_state = Integer.valueOf(json.get("persona_state").toString());
		this.persona_name = json.get("persona_name").toString();
		this.status_flags = Integer.valueOf(json.get("status_flags").toString());
	}

	public PersonaRelationshipMessage(String steamid_other, int persona_state, String persona_name) {
		this(steamid_other, persona_state, persona_name, 1);
	}

	public PersonaRelationshipMessage(String steamid_other, int persona_state, String persona_name, int status_flags) {
		super(steamid_other);
		this.persona_state = persona_state;
		this.persona_name = persona_name;
		this.status_flags = status_flags;
	}

	@Override
	public void addParameters(Map<String, String> params) {
		params.put("status_flags", "" + status_flags);
		params.put("persona_state", "" + persona_state);
		params.put("persona_name", persona_name);
	}
}