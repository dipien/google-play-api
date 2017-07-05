package com.jdroid.android.googleplay.publisher;

public class AppContext {
	
	// The application id of the app
	private String applicationId;

	// Path to the private key json file parent directory
	private String privateKeyJsonFileDir;
	
	private String metadataPath;
	private String locales;
	
	private String apkPath;
	private TrackType trackType;
	private Double userFraction;
	
	// Validations
	private Boolean videoRequired = false;
	private Boolean promoGraphicRequired = true;
	private Boolean phoneScreenshotsRequired = true;
	private Boolean sevenInchScreenshotsRequired = false;
	private Boolean tenInchScreenshotsRequired = false;
	private Boolean wearScreenshotsRequired = false;
	private Boolean tvScreenshotsRequired = false;
	private Boolean tvBannerRequired = false;
	
	private Boolean failOnApkUpgradeVersionConflict = true;

	public String getApplicationId() {
		return applicationId;
	}
	
	public String getMetadataPath() {
		return metadataPath;
	}
	
	public String getLocales() {
		return locales;
	}
	
	public String getApkPath() {
		return apkPath;
	}
	
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public void setMetadataPath(String metadataPath) {
		this.metadataPath = metadataPath;
	}

	public void setLocales(String locales) {
		this.locales = locales;
	}

	public void setApkPath(String apkPath) {
		this.apkPath = apkPath;
	}

	public Boolean isVideoRequired() {
		return videoRequired;
	}
	
	public void setVideoRequired(Boolean videoRequired) {
		if (videoRequired != null) {
			this.videoRequired = videoRequired;
		}
	}
	
	public void setPromoGraphicRequired(Boolean promoGraphicRequired) {
		if (promoGraphicRequired != null) {
			this.promoGraphicRequired = promoGraphicRequired;
		}
	}

	public Boolean isPromoGraphicRequired() {
		return promoGraphicRequired;
	}

	public Boolean isPhoneScreenshotsRequired() {
		return phoneScreenshotsRequired;
	}

	public void setPhoneScreenshotsRequired(Boolean phoneScreenshotsRequired) {
		if (phoneScreenshotsRequired != null) {
			this.phoneScreenshotsRequired = phoneScreenshotsRequired;
		}
	}

	public Boolean isSevenInchScreenshotsRequired() {
		return sevenInchScreenshotsRequired;
	}

	public void setSevenInchScreenshotsRequired(Boolean sevenInchScreenshotsRequired) {
		if (sevenInchScreenshotsRequired != null) {
			this.sevenInchScreenshotsRequired = sevenInchScreenshotsRequired;
		}
	}

	public Boolean isTenInchScreenshotsRequired() {
		return tenInchScreenshotsRequired;
	}

	public void setTenInchScreenshotsRequired(Boolean tenInchScreenshotsRequired) {
		if (tenInchScreenshotsRequired != null) {
			this.tenInchScreenshotsRequired = tenInchScreenshotsRequired;
		}
	}
	
	public Boolean isWearScreenshotsRequired() {
		return wearScreenshotsRequired;
	}
	
	public void setWearScreenshotsRequired(Boolean wearScreenshotsRequired) {
		if (wearScreenshotsRequired != null) {
			this.wearScreenshotsRequired = wearScreenshotsRequired;
		}
	}
	
	public Boolean isTvScreenshotsRequired() {
		return tvScreenshotsRequired;
	}
	
	public void setTvScreenshotsRequired(Boolean tvScreenshotsRequired) {
		if (tvScreenshotsRequired != null) {
			this.tvScreenshotsRequired = tvScreenshotsRequired;
		}
	}
	
	public Boolean isTvBannerRequired() {
		return tvBannerRequired;
	}
	
	public void setTvBannerRequired(Boolean tvBannerRequired) {
		if (tvBannerRequired != null) {
			this.tvBannerRequired = tvBannerRequired;
		}
	}
	
	public String getPrivateKeyJsonFileDirectory() {
		return privateKeyJsonFileDir;
	}
	
	public void setPrivateKeyJsonFileDirectory(String privateKeyJsonFileDir) {
		this.privateKeyJsonFileDir = privateKeyJsonFileDir;
	}
	
	public TrackType getTrackType() {
		return trackType;
	}
	
	public void setTrackType(TrackType trackType) {
		this.trackType = trackType;
	}
	
	public Double getUserFraction() {
		return userFraction;
	}
	
	public void setUserFraction(Double userFraction) {
		this.userFraction = userFraction;
	}
	
	public Boolean failOnApkUpgradeVersionConflict() {
		return failOnApkUpgradeVersionConflict;
	}
	
	public void setFailOnApkUpgradeVersionConflict(Boolean failOnApkUpgradeVersionConflict) {
		if (failOnApkUpgradeVersionConflict != null) {
			this.failOnApkUpgradeVersionConflict = failOnApkUpgradeVersionConflict;
		}
	}
}
