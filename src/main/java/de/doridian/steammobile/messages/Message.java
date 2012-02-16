package de.doridian.steammobile.messages;

import org.json.simple.JSONObject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public abstract class Message {
	@Retention(RetentionPolicy.RUNTIME) @Target(ElementType.TYPE) protected @interface Type { String value(); }

	public final long timestamp;
	public final String steamid_other;

	public Message(JSONObject json) {
		this.steamid_other = json.get("steamid_from").toString();
		this.timestamp = Long.valueOf(json.get("timestamp").toString());
	}

	public Message(String steamid_other) {
		this.steamid_other = steamid_other;
		this.timestamp = System.currentTimeMillis() / 1000;
	}

	public void addParameters(Map<String, String> params) { }

	public final String getType() {
		return this.getClass().getAnnotation(Type.class).value();
	}
	
	//Methods to get the classes by type etc..
	private static Map<String, Class<? extends Message>> messages;

	static {
		messages = new HashMap<String, Class<? extends Message>>();

		addMessage(TextMessage.class);
		addMessage(EmoteMessage.class);

		addMessage(MyEmoteMessage.class);
		addMessage(MyTextMessage.class);

		addMessage(PersonaStateMessage.class);
		addMessage(TypingMessage.class);
		addMessage(LeftConversationMessage.class);
	}
	
	private static void addMessage(Class<? extends Message> clazz) {
		if(!clazz.isAnnotationPresent(Type.class)) return;
		messages.put(clazz.getAnnotation(Type.class).value().toLowerCase(), clazz);
	}
	
	public static Message craftMessageFromJSON(JSONObject json) {
		try {
			String type = json.get("type").toString().toLowerCase();
			if(!messages.containsKey(type)) {
				return null;
			}
			Class<? extends Message> clazz = messages.get(type);
			Constructor<? extends Message> ctor = clazz.getConstructor(JSONObject.class);
			return ctor.newInstance(json);
		} catch(Exception e) {
			return null;
		}
	}
}
