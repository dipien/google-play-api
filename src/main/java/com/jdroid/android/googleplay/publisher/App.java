package com.jdroid.android.googleplay.publisher;

import com.google.api.client.http.AbstractInputStreamContent;
import com.jdroid.java.exception.UnexpectedException;

import java.util.List;

public class App {

	private AppContext appContext;
	private List<LocaleListing> localeListings;
	private LocaleListing defaultLocaleListing;

	public App(AppContext appContext, List<LocaleListing> localeListings, LocaleListing defaultLocaleListing) {
		this.appContext = appContext;
		this.localeListings = localeListings;
		this.defaultLocaleListing = defaultLocaleListing;
	}

	public String getTitle(LocaleListing localeListing) {
		String title = localeListing.getTitle();
		if (title == null) {
			title = defaultLocaleListing.getTitle();
		}
		if (title == null) {
			throw new UnexpectedException("The listing title was not found for locale " + localeListing.getLocale().toString());
		}
		return title;
	}

	public String getFullDescription(LocaleListing localeListing) {
		String fullDescription = localeListing.getFullDescription();
		if (fullDescription == null) {
			fullDescription = defaultLocaleListing.getFullDescription();
		}
		if (fullDescription == null) {
			throw new UnexpectedException("The listing full description was not found for locale " + localeListing.getLocale().toString());
		}
		return fullDescription;
	}

	public String getShortDescription(LocaleListing localeListing) {
		String shortDescription = localeListing.getShortDescription();
		if (shortDescription == null) {
			shortDescription = defaultLocaleListing.getShortDescription();
		}
		if (shortDescription == null) {
			throw new UnexpectedException("The listing short description was not found for locale " + localeListing.getLocale().toString());
		}
		return shortDescription;
	}

	public AbstractInputStreamContent getFeatureGraphic(LocaleListing localeListing) {
		AbstractInputStreamContent featureGraphic = localeListing.getFeatureGraphic();
		if (featureGraphic == null) {
			featureGraphic = defaultLocaleListing.getFeatureGraphic();
		}
		if (featureGraphic == null) {
			throw new UnexpectedException("Feature graphic was not found for locale " + localeListing.getLocale().toString());
		}
		return featureGraphic;
	}

	public AbstractInputStreamContent getPromoGraphic(LocaleListing localeListing) {
		AbstractInputStreamContent promoGraphic = localeListing.getFeatureGraphic();
		if (promoGraphic == null) {
			promoGraphic = defaultLocaleListing.getPromoGraphic();
		}
		if (promoGraphic == null && appContext.isPromoGraphicRequired()) {
			throw new UnexpectedException("Promo graphic was not found for locale " + localeListing.getLocale().toString());
		}
		return promoGraphic;
	}

	public AbstractInputStreamContent getHighResolutionIcon(LocaleListing localeListing) {
		AbstractInputStreamContent highResolutionIcon = localeListing.getHighResolutionIcon();
		if (highResolutionIcon == null) {
			highResolutionIcon = defaultLocaleListing.getHighResolutionIcon();
		}
		if (highResolutionIcon == null) {
			throw new UnexpectedException("The high resolution icon was not found for locale " + localeListing.getLocale().toString());
		}
		return highResolutionIcon;
	}

	public List<AbstractInputStreamContent> getPhoneScreenshots(LocaleListing localeListing) {
		List<AbstractInputStreamContent> phoneScreenshots = localeListing.getPhoneScreenshots();
		if (phoneScreenshots.isEmpty()) {
			phoneScreenshots = defaultLocaleListing.getPhoneScreenshots();
		}
		if (phoneScreenshots.isEmpty() && appContext.isPhoneScreenshotsRequired()) {
			throw new UnexpectedException("Phone screenshots were not found for locale " + localeListing.getLocale().toString());
		}
		return phoneScreenshots;
	}

	public List<AbstractInputStreamContent> getSevenInchScreenshots(LocaleListing localeListing) {
		List<AbstractInputStreamContent> sevenInchScreenshots = localeListing.getSevenInchScreenshots();
		if (sevenInchScreenshots.isEmpty()) {
			sevenInchScreenshots = defaultLocaleListing.getSevenInchScreenshots();
		}
		if (sevenInchScreenshots.isEmpty() && appContext.isSevenInchScreenshotsRequired()) {
			throw new UnexpectedException("7 Inch screenshots were not found for locale " + localeListing.getLocale().toString());
		}
		return sevenInchScreenshots;
	}

	public List<AbstractInputStreamContent> getTenInchScreenshots(LocaleListing localeListing) {
		List<AbstractInputStreamContent> tenInchScreenshots = localeListing.getTenInchScreenshots();
		if (tenInchScreenshots.isEmpty()) {
			tenInchScreenshots = defaultLocaleListing.getTenInchScreenshots();
		}
		if (tenInchScreenshots.isEmpty() && appContext.isTenInchScreenshotsRequired()) {
			throw new UnexpectedException("10 Inch screenshots were not found for locale " + localeListing.getLocale().toString());
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
