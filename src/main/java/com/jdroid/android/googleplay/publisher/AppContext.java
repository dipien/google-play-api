package com.jdroid.android.googleplay.publisher;

public class AppContext {
	
	// The application id of the app
	private String applicationId;
	
	// Installed application: Leave this string empty and copy or edit resources/client_secrets.json. Service accounts:
	// Enter the service account email and add your key.p12 file to the resources directory.
	private String serviceAccountEmail;
	
	// Path to the private key file (only used for Service Account auth
	private String privateKeyFile;
	
	private String listingPath;
	private String locales;
	private String apkPath;
	private TrackType trackType;

	public String getApplicationId() {
		return applicationId;
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

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public void setServiceAccountEmail(String serviceAccountEmail) {
		this.serviceAccountEmail = serviceAccountEmail;
	}

	public void setPrivateKeyFile(String privateKeyFile) {
		this.privateKeyFile = privateKeyFile;
	}

	public void setListingPath(String listingPath) {
		this.listingPath = listingPath;
	}

	public void setLocales(String locales) {
		this.locales = locales;
	}

	public void setApkPath(String apkPath) {
		this.apkPath = apkPath;
	}

	public void setTrackType(TrackType trackType) {
		this.trackType = trackType;
	}
}
