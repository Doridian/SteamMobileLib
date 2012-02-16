package de.doridian.steammobile.messages;

import org.json.simple.JSONObject;

@Message.Type("emote")
public class EmoteMessage extends TextMessage {
	public EmoteMessage(JSONObject json) {
		super(json);
	}

	public EmoteMessage(String steamid_other, String text) {
		super(steamid_other, text);
	}
}
