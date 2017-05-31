package com.jdroid.android.googleplay.publisher;

import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.util.Lists;
import com.jdroid.java.exception.UnexpectedException;
import com.jdroid.java.utils.StringUtils;

import java.util.List;
import java.util.Locale;

public class App {

	private AppContext appContext;
	private List<LocaleListing> localeListings;
	private LocaleListing defaultLocaleListing;

	public App(AppContext appContext) {
		this.appContext = appContext;
		
		this.localeListings = Lists.newArrayList();
		for (String each : StringUtils.splitToCollectionWithCommaSeparator(appContext.getLocales())) {
			String[] split = each.split("-");
			String language = split[0];
			String country = "";
			if (split.length > 1) {
				country = split[1];
			}
			localeListings.add(new LocaleListing(new Locale(language, country), appContext.getListingPath()));
		}
		this.defaultLocaleListing = new LocaleListing(null, appContext.getListingPath());
	}

	public String getTitle(LocaleListing localeListing) {
		String title = localeListing.getTitle();
		if (title == null) {
			title = defaultLocaleListing.getTitle();
		}
		if (title == null) {
			throw new UnexpectedException("The title.txt was not found for locale " + localeListing.getLocale().toLanguageTag());
		}
		return title;
	}

	public String getFullDescription(LocaleListing localeListing) {
		String fullDescription = localeListing.getFullDescription();
		if (fullDescription == null) {
			fullDescription = defaultLocaleListing.getFullDescription();
		}
		if (fullDescription == null) {
			throw new UnexpectedException("The full_description.txt was not found for locale " + localeListing.getLocale().toLanguageTag());
		}
		return fullDescription;
	}

	public String getShortDescription(LocaleListing localeListing) {
		String shortDescription = localeListing.getShortDescription();
		if (shortDescription == null) {
			shortDescription = defaultLocaleListing.getShortDescription();
		}
		if (shortDescription == null) {
			throw new UnexpectedException("The short_description.txt was not found for locale " + localeListing.getLocale().toLanguageTag());
		}
		return shortDescription;
	}
	
	public String getVideo(LocaleListing localeListing) {
		String video = localeListing.getVideo();
		if (video == null) {
			video = defaultLocaleListing.getVideo();
		}
		if (video == null && appContext.isVideoRequired()) {
			throw new UnexpectedException("The video.txt was not found for locale " + localeListing.getLocale().toLanguageTag());
		}
		return video;
	}

	public AbstractInputStreamContent getFeatureGraphic(LocaleListing localeListing) {
		AbstractInputStreamContent featureGraphic = localeListing.getFeatureGraphic();
		if (featureGraphic == null) {
			featureGraphic = defaultLocaleListing.getFeatureGraphic();
		}
		if (featureGraphic == null) {
			throw new UnexpectedException("images/featureGraphic.png was not found for locale " + localeListing.getLocale().toLanguageTag());
		}
		return featureGraphic;
	}

	public AbstractInputStreamContent getPromoGraphic(LocaleListing localeListing) {
		AbstractInputStreamContent promoGraphic = localeListing.getPromoGraphic();
		if (promoGraphic == null) {
			promoGraphic = defaultLocaleListing.getPromoGraphic();
		}
		if (promoGraphic == null && appContext.isPromoGraphicRequired()) {
			throw new UnexpectedException("images/promoGraphic.png was not found for locale " + localeListing.getLocale().toLanguageTag());
		}
		return promoGraphic;
	}

	public AbstractInputStreamContent getHighResolutionIcon(LocaleListing localeListing) {
		AbstractInputStreamContent highResolutionIcon = localeListing.getHighResolutionIcon();
		if (highResolutionIcon == null) {
			highResolutionIcon = defaultLocaleListing.getHighResolutionIcon();
		}
		if (highResolutionIcon == null) {
			throw new UnexpectedException("images/icon.png was not found for locale " + localeListing.getLocale().toLanguageTag());
		}
		return highResolutionIcon;
	}

	public List<AbstractInputStreamContent> getPhoneScreenshots(LocaleListing localeListing) {
		List<AbstractInputStreamContent> phoneScreenshots = localeListing.getPhoneScreenshots();
		if (phoneScreenshots.isEmpty()) {
			phoneScreenshots = defaultLocaleListing.getPhoneScreenshots();
		}
		if (phoneScreenshots.isEmpty() && appContext.isPhoneScreenshotsRequired()) {
			throw new UnexpectedException("Phone screenshots were not found for locale " + localeListing.getLocale().toLanguageTag());
		}
		return phoneScreenshots;
	}

	public List<AbstractInputStreamContent> getSevenInchScreenshots(LocaleListing localeListing) {
		List<AbstractInputStreamContent> sevenInchScreenshots = localeListing.getSevenInchScreenshots();
		if (sevenInchScreenshots.isEmpty()) {
			sevenInchScreenshots = defaultLocaleListing.getSevenInchScreenshots();
		}
		if (sevenInchScreenshots.isEmpty() && appContext.isSevenInchScreenshotsRequired()) {
			throw new UnexpectedException("7 Inch screenshots were not found for locale " + localeListing.getLocale().toLanguageTag());
		}
		return sevenInchScreenshots;
	}

	public List<AbstractInputStreamContent> getTenInchScreenshots(LocaleListing localeListing) {
		List<AbstractInputStreamContent> tenInchScreenshots = localeListing.getTenInchScreenshots();
		if (tenInchScreenshots.isEmpty()) {
			tenInchScreenshots = defaultLocaleListing.getTenInchScreenshots();
		}
		if (tenInchScreenshots.isEmpty() && appContext.isTenInchScreenshotsRequired()) {
			throw new UnexpectedException("10 Inch screenshots were not found for locale " + localeListing.getLocale().toLanguageTag());
		}
		return tenInchScreenshots;
	}

	public List<LocaleListing> getLocaleListings() {
		return localeListings;
	}

	public LocaleListing getDefaultLocaleListing() {
		return defaultLocaleListing;
	}

	public AppContext getAppContext() {
		return appContext;
	}
}
