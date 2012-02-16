package de.doridian.steammobile.messages;

import org.json.simple.JSONObject;

@Message.Type("emote")
public class EmoteMessage extends TextMessage {
	public EmoteMessage(JSONObject json) {
		super(json);
	}

	public EmoteMessage(long timestamp, String steamid_other, String text) {
		super(timestamp, steamid_other, text);
	}
}
