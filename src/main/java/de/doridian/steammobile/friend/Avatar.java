package de.doridian.steammobile.friend;

import org.json.simple.JSONObject;

public class Avatar {
	private final String avatarurl;
	private final String avatarmediumurl;
	private final String avatarfullurl;

	protected Avatar(JSONObject json) {
		this(json.get("avatar").toString(), json.get("avatarmedium").toString(), json.get("avatarfull").toString());
	}

	protected Avatar(String avatarurl, String avatarmediumurl, String avatarfullurl) {
		this.avatarurl = avatarurl;
		this.avatarmediumurl = avatarmediumurl;
		this.avatarfullurl = avatarfullurl;
	}
	
	public String getURLSmallSize() {
		return avatarurl;
	}
	
	public String getURLFullSize() {
		return avatarfullurl;
	}
	
	public String getURLMediumSize() {
		return avatarmediumurl;
	}
}
