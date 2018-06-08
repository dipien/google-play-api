package com.jdroid.android.googleplay.publisher;

import com.google.common.collect.Lists;

import java.util.List;

public class AppContext {
	
	// The application id of the app
	private String applicationId;

	// Path to the private key json file
	private String privateKeyJsonFilePath;
	
	private String metadataPath;
	private List<String> locales = Lists.newArrayList();
	
	private String apkPath;
	private String apkDir;
	private String bundlePath;
	private String bundleDir;
	
	private TrackType trackType;
	private Double userFraction;
	private Boolean draft = false;
	private String releaseName;
	
	// Validations
	private Boolean releaseNotesRequired = false;
	private Boolean videoRequired = false;
	private Boolean promoGraphicRequired = false;
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
	
	public List<String> getLocales() {
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

	public void setLocales(List<String> locales) {
		if (locales != null) {
			this.locales = locales;
		}
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
	
	public Boolean isDraft() {
		return draft;
	}
	
	public void setDraft(Boolean draft) {
		if (draft != null) {
			this.draft = draft;
		}
	}
	
	public String getReleaseName() {
		return releaseName;
	}
	
	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}
	
	public String getBundlePath() {
		return bundlePath;
	}
	
	public void setBundlePath(String bundlePath) {
		this.bundlePath = bundlePath;
	}
	
	public String getPrivateKeyJsonFilePath() {
		return privateKeyJsonFilePath;
	}
	
	public void setPrivateKeyJsonFilePath(String privateKeyJsonFilePath) {
		this.privateKeyJsonFilePath = privateKeyJsonFilePath;
	}
	
	public Boolean isReleaseNotesRequired() {
		return releaseNotesRequired;
	}
	
	public void setReleaseNotesRequired(Boolean releaseNotesRequired) {
		if (releaseNotesRequired != null) {
			this.releaseNotesRequired = releaseNotesRequired;
		}
	}
	
	public String getApkDir() {
		return apkDir;
	}
	
	public void setApkDir(String apkDir) {
		this.apkDir = apkDir;
	}
	
	public String getBundleDir() {
		return bundleDir;
	}
	
	public void setBundleDir(String bundleDir) {
		this.bundleDir = bundleDir;
	}
}
