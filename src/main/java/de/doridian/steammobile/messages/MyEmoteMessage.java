package de.doridian.steammobile.messages;

import org.json.simple.JSONObject;

@Message.Type("my_emote")
public class MyEmoteMessage extends EmoteMessage {
	public MyEmoteMessage(JSONObject json) {
		super(json);
	}

	public MyEmoteMessage(String steamid_other, String text) {
		super(steamid_other, text);
	}
}
