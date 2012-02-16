package de.doridian.steammobile.messages;

import org.json.simple.JSONObject;

@Message.Type("typing")
public class TypingMessage extends Message {
	public TypingMessage(JSONObject json) {
		super(json);
	}

	public TypingMessage(long timestamp, String steamid_other) {
		super(timestamp, steamid_other);
	}
}
