package de.doridian.steammobile.messages;

import de.doridian.steammobile.friend.Friend;

import java.util.Map;

public class PersonaStateMessage extends Message {
	public final Friend.State persona_state;
	public final String persona_name;
	public final int status_flags;

	public PersonaStateMessage(long timestamp, String steamid_other, int persona_state, String persona_name) {
		this(timestamp, steamid_other, persona_state, persona_name, 863);
	}

	public PersonaStateMessage(long timestamp, String steamid_other, int persona_state, String persona_name, int status_flags) {
		super(timestamp, steamid_other);
		this.persona_state = Friend.State.getByID(persona_state);
		this.persona_name = persona_name;
		this.status_flags = status_flags;
	}

	@Override
	public String getType() {
		return "personastate";
	}

	@Override
	public void addParameters(Map<String, String> params) {
		params.put("status_flags", "" + status_flags);
		params.put("persona_state", "" + persona_state.getID());
	}
}
