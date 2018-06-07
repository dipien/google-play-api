package com.jdroid.android.googleplay.publisher;

import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.util.Lists;

import java.util.List;
import java.util.Locale;

public class App {

	private AppContext appContext;
	private List<LocaleListing> localeListings;
	private LocaleListing defaultLocaleListing;

	public App(AppContext appContext) {
		this.appContext = appContext;
		
		this.localeListings = Lists.newArrayList();
		for (String each : appContext.getLocales()) {
			String[] split = each.split("-");
			String language = split[0];
			String country = "";
			if (split.length > 1) {
				country = split[1];
			}
			localeListings.add(new LocaleListing(new Locale(language, country), appContext.getMetadataPath()));
		}
		this.defaultLocaleListing = new LocaleListing(null, appContext.getMetadataPath());
	}

	public String getTitle(LocaleListing localeListing) {
		return localeListing.getTitle(defaultLocaleListing);
	}

	public String getFullDescription(LocaleListing localeListing) {
		return localeListing.getFullDescription(defaultLocaleListing);
	}

	public String getShortDescription(LocaleListing localeListing) {
		return localeListing.getShortDescription(defaultLocaleListing);
	}
	
	public String getVideo(LocaleListing localeListing) {
		return localeListing.getVideo(defaultLocaleListing, appContext.isVideoRequired());
	}

	public AbstractInputStreamContent getFeatureGraphic(LocaleListing localeListing) {
		return localeListing.getFeatureGraphic(defaultLocaleListing);
	}

	public AbstractInputStreamContent getPromoGraphic(LocaleListing localeListing) {
		return localeListing.getPromoGraphic(defaultLocaleListing, appContext.isPromoGraphicRequired());
	}

	public AbstractInputStreamContent getHighResolutionIcon(LocaleListing localeListing) {
		return localeListing.getHighResolutionIcon(defaultLocaleListing);
	}

	public AbstractInputStreamContent getTvBanner(LocaleListing localeListing) {
		return localeListing.getTvBanner(defaultLocaleListing, appContext.isTvBannerRequired());
	}

	public List<AbstractInputStreamContent> getPhoneScreenshots(LocaleListing localeListing) {
		return localeListing.getPhoneScreenshots(defaultLocaleListing, appContext.isPhoneScreenshotsRequired());
	}

	public List<AbstractInputStreamContent> getSevenInchScreenshots(LocaleListing localeListing) {
		return localeListing.getSevenInchScreenshots(defaultLocaleListing, appContext.isSevenInchScreenshotsRequired());
	}

	public List<AbstractInputStreamContent> getTenInchScreenshots(LocaleListing localeListing) {
		return localeListing.getTenInchScreenshots(defaultLocaleListing, appContext.isTenInchScreenshotsRequired());
	}
	
	public List<AbstractInputStreamContent> getWearScreenshots(LocaleListing localeListing) {
		return localeListing.getWearScreenshots(defaultLocaleListing, appContext.isWearScreenshotsRequired());
	}
	
	public List<AbstractInputStreamContent> getTvScreenshots(LocaleListing localeListing) {
		return localeListing.getTvScreenshots(defaultLocaleListing, appContext.isTvScreenshotsRequired());
	}
	
	public String getReleaseNotes(LocaleListing localeListing, Integer versionCode) {
		return localeListing.getReleaseNotes(versionCode, defaultLocaleListing, appContext.isReleaseNotesRequired());
	}
	
	public List<LocaleListing> getLocaleListings() {
		return localeListings;
	}

	public String getApplicationId() {
		return appContext.getApplicationId();
	}

	public AppContext getAppContext() {
		return appContext;
	}
}
