package de.doridian.steammobile.messages;

import org.json.simple.JSONObject;

@Message.Type("typing")
public class TypingMessage extends Message {
	public TypingMessage(JSONObject json) {
		super(json);
	}

	public TypingMessage(String steamid_other) {
		super(steamid_other);
	}
}
