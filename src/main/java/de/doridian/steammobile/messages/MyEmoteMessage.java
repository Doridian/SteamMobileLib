package de.doridian.steammobile.messages;

import org.json.simple.JSONObject;

@Message.Type("my_emote")
public class MyEmoteMessage extends EmoteMessage {
	public MyEmoteMessage(JSONObject json) {
		super(json);
	}

	public MyEmoteMessage(long timestamp, String steamid_other, String text) {
		super(timestamp, steamid_other, text);
	}
}
