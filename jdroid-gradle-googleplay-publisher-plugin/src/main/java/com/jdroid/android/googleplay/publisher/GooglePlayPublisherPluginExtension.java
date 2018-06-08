package com.jdroid.android.googleplay.publisher;

import com.jdroid.android.googleplay.publisher.commons.PropertyResolver;

import org.gradle.api.Project;

public class GooglePlayPublisherPluginExtension {
	
	private PropertyResolver propertyResolver;
	
	private String applicationId;
	private String privateKeyJsonFilePath;
	
	private String metadataPath;
	private String locales;
	
	private String apkPath;
	private String bundlePath;
	private String track;
	private Double userFraction;
	private Boolean draft;
	private String releaseName;
	
	private Boolean releaseNotesRequired;
	private Boolean videoRequired;
	private Boolean promoGraphicRequired;
	private Boolean phoneScreenshotsRequired;
	private Boolean sevenInchScreenshotsRequired;
	private Boolean tenInchScreenshotsRequired;
	private Boolean wearScreenshotsRequired;
	private Boolean tvScreenshotsRequired;
	private Boolean tvBannerRequired;
	
	private Boolean failOnApkUpgradeVersionConflict;
	
	public GooglePlayPublisherPluginExtension(Project project) {
		propertyResolver = new PropertyResolver(project);
		
		applicationId = propertyResolver.getStringProp("applicationId");
		privateKeyJsonFilePath = propertyResolver.getStringProp("privateKeyJsonFilePath");
		
		metadataPath = propertyResolver.getStringProp("metadataPath", project.getProjectDir().getAbsolutePath());
		locales = propertyResolver.getStringProp("locales");
		
		apkPath = propertyResolver.getStringProp("apkPath");
		bundlePath = propertyResolver.getStringProp("bundlePath");
		track = propertyResolver.getStringProp("track");
		userFraction = propertyResolver.getDoubleProp("userFraction");
		draft = propertyResolver.getBooleanProp("draft");
		releaseName = propertyResolver.getStringProp("releaseName");
		
		releaseNotesRequired = propertyResolver.getBooleanProp("releaseNotesRequired");
		videoRequired = propertyResolver.getBooleanProp("videoRequired");
		promoGraphicRequired = propertyResolver.getBooleanProp("promoGraphicRequired");
		phoneScreenshotsRequired = propertyResolver.getBooleanProp("phoneScreenshotsRequired");
		sevenInchScreenshotsRequired = propertyResolver.getBooleanProp("sevenInchScreenshotsRequired");
		tenInchScreenshotsRequired = propertyResolver.getBooleanProp("tenInchScreenshotsRequired");
		wearScreenshotsRequired = propertyResolver.getBooleanProp("wearScreenshotsRequired");
		tvScreenshotsRequired = propertyResolver.getBooleanProp("tvScreenshotsRequired");
		tvBannerRequired = propertyResolver.getBooleanProp("tvBannerRequired");
		
		failOnApkUpgradeVersionConflict = propertyResolver.getBooleanProp("failOnApkUpgradeVersionConflict");
	}
	
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	
	public String getApplicationId() {
		return applicationId;
	}
	
	public String getPrivateKeyJsonFilePath() {
		return privateKeyJsonFilePath;
	}
	
	public void setPrivateKeyJsonFilePath(String privateKeyJsonFilePath) {
		this.privateKeyJsonFilePath = privateKeyJsonFilePath;
	}
	
	public String getMetadataPath() {
		return metadataPath;
	}
	
	public void setMetadataPath(String metadataPath) {
		this.metadataPath = metadataPath;
	}
	
	public String getLocales() {
		return locales;
	}
	
	public void setLocales(String locales) {
		this.locales = locales;
	}
	
	public String getApkPath() {
		return apkPath;
	}
	
	public void setApkPath(String apkPath) {
		this.apkPath = apkPath;
	}
	
	public String getBundlePath() {
		return bundlePath;
	}
	
	public void setBundlePath(String bundlePath) {
		this.bundlePath = bundlePath;
	}
	
	public String getTrack() {
		return track;
	}
	
	public void setTrack(String track) {
		this.track = track;
	}
	
	public Double getUserFraction() {
		return userFraction;
	}
	
	public void setUserFraction(Double userFraction) {
		this.userFraction = userFraction;
	}
	
	public Boolean getDraft() {
		return draft;
	}
	
	public void setDraft(Boolean draft) {
		this.draft = draft;
	}
	
	public String getReleaseName() {
		return releaseName;
	}
	
	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}
	
	public Boolean getReleaseNotesRequired() {
		return releaseNotesRequired;
	}
	
	public void setReleaseNotesRequired(Boolean releaseNotesRequired) {
		this.releaseNotesRequired = releaseNotesRequired;
	}
	
	public Boolean getVideoRequired() {
		return videoRequired;
	}
	
	public void setVideoRequired(Boolean videoRequired) {
		this.videoRequired = videoRequired;
	}
	
	public Boolean getPromoGraphicRequired() {
		return promoGraphicRequired;
	}
	
	public void setPromoGraphicRequired(Boolean promoGraphicRequired) {
		this.promoGraphicRequired = promoGraphicRequired;
	}
	
	public Boolean getPhoneScreenshotsRequired() {
		return phoneScreenshotsRequired;
	}
	
	public void setPhoneScreenshotsRequired(Boolean phoneScreenshotsRequired) {
		this.phoneScreenshotsRequired = phoneScreenshotsRequired;
	}
	
	public Boolean getSevenInchScreenshotsRequired() {
		return sevenInchScreenshotsRequired;
	}
	
	public void setSevenInchScreenshotsRequired(Boolean sevenInchScreenshotsRequired) {
		this.sevenInchScreenshotsRequired = sevenInchScreenshotsRequired;
	}
	
	public Boolean getTenInchScreenshotsRequired() {
		return tenInchScreenshotsRequired;
	}
	
	public void setTenInchScreenshotsRequired(Boolean tenInchScreenshotsRequired) {
		this.tenInchScreenshotsRequired = tenInchScreenshotsRequired;
	}
	
	public Boolean getWearScreenshotsRequired() {
		return wearScreenshotsRequired;
	}
	
	public void setWearScreenshotsRequired(Boolean wearScreenshotsRequired) {
		this.wearScreenshotsRequired = wearScreenshotsRequired;
	}
	
	public Boolean getTvScreenshotsRequired() {
		return tvScreenshotsRequired;
	}
	
	public void setTvScreenshotsRequired(Boolean tvScreenshotsRequired) {
		this.tvScreenshotsRequired = tvScreenshotsRequired;
	}
	
	public Boolean getTvBannerRequired() {
		return tvBannerRequired;
	}
	
	public void setTvBannerRequired(Boolean tvBannerRequired) {
		this.tvBannerRequired = tvBannerRequired;
	}
	
	public Boolean getFailOnApkUpgradeVersionConflict() {
		return failOnApkUpgradeVersionConflict;
	}
	
	public void setFailOnApkUpgradeVersionConflict(Boolean failOnApkUpgradeVersionConflict) {
		this.failOnApkUpgradeVersionConflict = failOnApkUpgradeVersionConflict;
	}
	
}
