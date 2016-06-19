package com.jdroid.android.googleplay.publisher;

import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.jdroid.java.exception.UnexpectedException;
import com.jdroid.java.utils.PropertiesUtils;

public class AppContext {
	
	// Specify the package name of the app.
	private String packageName;
	
	// Installed application: Leave this string empty and copy or edit resources/client_secrets.json. Service accounts:
	// Enter the service account email and add your key.p12 file to the resources directory.
	private String serviceAccountEmail;
	
	// Path to the private key file (only used for Service Account auth
	private String privateKeyFile;
	
	private String listingPath;
	private String locales;
	private String apkPath;
	private TrackType trackType;
	
	public AppContext(String fileName) {
		
		PropertiesUtils.loadExternalProperties(fileName);
		
		packageName = PropertiesUtils.getStringProperty("package.name");
		if (Strings.isNullOrEmpty(packageName)) {
			throw new UnexpectedException("packageName cannot be null or empty!");
		}


		serviceAccountEmail = PropertiesUtils.getStringProperty("service.account.email");
		if (Strings.isNullOrEmpty(serviceAccountEmail)) {
			throw new UnexpectedException("serviceAccountEmail cannot be null or empty!");
		}
		
		privateKeyFile = PropertiesUtils.getStringProperty("private.key.file");
		if (Strings.isNullOrEmpty(privateKeyFile)) {
			throw new UnexpectedException("privateKeyFile cannot be null or empty!");
		}
		
		listingPath = PropertiesUtils.getStringProperty("listing.path");
		if (Strings.isNullOrEmpty(listingPath)) {
			throw new UnexpectedException("listingPath cannot be null or empty!");
		}
		
		locales = PropertiesUtils.getStringProperty("locales");
		if (Strings.isNullOrEmpty(locales)) {
			throw new UnexpectedException("locales cannot be null or empty!");
		}
		
		apkPath = PropertiesUtils.getStringProperty("apk.path");
		trackType = TrackType.findByKey(PropertiesUtils.getStringProperty("track.type"));
	}
	
	public String getPackageName() {
		return packageName;
	}
	
	public String getServiceAccountEmail() {
		return serviceAccountEmail;
	}
	
	public String getPrivateKeyFile() {
		return privateKeyFile;
	}
	
	public String getListingPath() {
		return listingPath;
	}
	
	public String getLocales() {
		return locales;
	}
	
	public String getApkPath() {
		return apkPath;
	}
	
	public TrackType getTrackType() {
		return trackType;
	}
	
}
