package com.jdroid.android.googleplay.publisher;

public class AppContext {
	
	// The application id of the app
	private String applicationId;

	// The service account email used to authenticate on Google Play
	private String serviceAccountEmail;
	
	// Path to the private key file extension (.p12 extension)
	private String privateKeyFile;
	
	private String listingPath;
	private String locales;
	private String apkPath;
	private TrackType trackType;

	// Validations
	private Boolean promoGraphicRequired;
	private Boolean phoneScreenshotsRequired;
	private Boolean sevenInchScreenshotsRequired;
	private Boolean tenInchScreenshotsRequired;

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

	public void setPromoGraphicRequired(Boolean promoGraphicRequired) {
		this.promoGraphicRequired = promoGraphicRequired;
	}

	public Boolean isPromoGraphicRequired() {
		return promoGraphicRequired;
	}

	public Boolean isPhoneScreenshotsRequired() {
		return phoneScreenshotsRequired;
	}

	public void setPhoneScreenshotsRequired(Boolean phoneScreenshotsRequired) {
		this.phoneScreenshotsRequired = phoneScreenshotsRequired;
	}

	public Boolean isSevenInchScreenshotsRequired() {
		return sevenInchScreenshotsRequired;
	}

	public void setSevenInchScreenshotsRequired(Boolean sevenInchScreenshotsRequired) {
		this.sevenInchScreenshotsRequired = sevenInchScreenshotsRequired;
	}

	public Boolean isTenInchScreenshotsRequired() {
		return tenInchScreenshotsRequired;
	}

	public void setTenInchScreenshotsRequired(Boolean tenInchScreenshotsRequired) {
		this.tenInchScreenshotsRequired = tenInchScreenshotsRequired;
	}
}
