package de.doridian.steammobile.messages;

import de.doridian.steammobile.friend.Friend;
import org.json.simple.JSONObject;

import java.util.Map;

@Message.Type("personarelationship")
public class PersonaRelationshipMessage extends PersonaStateMessage {
	public PersonaRelationshipMessage(JSONObject json) {
		super(json);
	}

	public PersonaRelationshipMessage(String steamid_other, Friend.State persona_state, String persona_name) {
		this(steamid_other, persona_state, persona_name, 863);
	}

	public PersonaRelationshipMessage(String steamid_other, Friend.State persona_state, String persona_name, int status_flags) {
		super(steamid_other, persona_state, persona_name, status_flags);
	}
}