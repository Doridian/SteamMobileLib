package de.doridian.steammobile.friend;

public class Avatar {
	private final String avatarurl;
	private final String avatarmediumurl;
	private final String avatarfullurl;
	
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
