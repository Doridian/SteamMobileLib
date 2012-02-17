package de.doridian.steammobile.methods.web.mobilesettings;

import de.doridian.steammobile.methods.web.BaseWebMethod;

import java.net.MalformedURLException;
import java.net.URL;

public class GetManifest extends BaseWebMethod {
	@Override
	public boolean isPOST() {
		return false;
	}

	@Override
	public URL getURL() throws MalformedURLException {
		return new URL(super.getURL(), "v0001");
	}
}
