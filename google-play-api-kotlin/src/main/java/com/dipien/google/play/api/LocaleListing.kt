package com.dipien.google.play.api;

import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.FileContent;
import com.google.api.client.util.Lists;
import com.jdroid.java.exception.UnexpectedException;
import com.jdroid.java.utils.FileUtils;

import java.io.File;
import java.util.List;
import java.util.Locale;


public class LocaleListing {
	
	private Locale locale;
	private String basePath;
	
	public LocaleListing(Locale locale, String listingPath) {
		this.locale = locale;
		this.basePath =  listingPath + java.io.File.separator + "googleplay" + java.io.File.separator + (locale != null ? locale.toLanguageTag() : "default") + java.io.File.separator;
	}
	
	public String getLanguageTag() {
		return locale.toLanguageTag();
	}
	
	public String getTitle(LocaleListing defaultLocaleListing) {
		return getDetailsContent("title", true, defaultLocaleListing);
	}
	
	public String getShortDescription(LocaleListing defaultLocaleListing) {
		return getDetailsContent("short_description", true, defaultLocaleListing);
	}
	
	public String getFullDescription(LocaleListing defaultLocaleListing) {
		return getDetailsContent("full_description", true, defaultLocaleListing);
	}
	
	public String getVideo(LocaleListing defaultLocaleListing, Boolean required) {
		return getDetailsContent("video", required, defaultLocaleListing);
	}
	
	public String getReleaseNotes(Integer versionCode, LocaleListing defaultLocaleListing, Boolean required) {
		String releaseNotes = getReleaseNotes(versionCode);
		if (releaseNotes == null) {
			releaseNotes = defaultLocaleListing.getReleaseNotes(versionCode);
		}
		if (releaseNotes == null && required) {
			throw new UnexpectedException("Release notes for version code " + versionCode + " were not found for locale " + getLanguageTag());
		}
		return releaseNotes;
	}
	
	private String getReleaseNotes(Integer versionCode) {
		File file = new File(basePath + "release_notes" + java.io.File.separator + versionCode + ".txt");
		return file.exists() ? FileUtils.toString(file) : getDefaultReleaseNotes();
	}
	
	private String getDefaultReleaseNotes() {
		File file = new File(basePath + "release_notes" + java.io.File.separator + "default_release_notes.txt");
		return file.exists() ? FileUtils.toString(file) : null;
	}
	
	public AbstractInputStreamContent getFeatureGraphic(LocaleListing defaultLocaleListing) {
		return getImageContent("feature_graphic", true, defaultLocaleListing);
	}
	
	public AbstractInputStreamContent getPromoGraphic(LocaleListing defaultLocaleListing, Boolean required) {
		return getImageContent("promo_graphic", required, defaultLocaleListing);
	}
	
	public AbstractInputStreamContent getHighResolutionIcon(LocaleListing defaultLocaleListing) {
		return getImageContent("high_resolution_icon", true, defaultLocaleListing);
	}
	
	public List<AbstractInputStreamContent> getPhoneScreenshots(LocaleListing defaultLocaleListing, Boolean required) {
		return getScreenshots("phone_screenshots", required, defaultLocaleListing);
	}
	
	public List<AbstractInputStreamContent> getSevenInchScreenshots(LocaleListing defaultLocaleListing, Boolean required) {
		return getScreenshots("seven_inch_screenshots", required, defaultLocaleListing);
	}
	
	public AbstractInputStreamContent getTvBanner(LocaleListing defaultLocaleListing, Boolean required) {
		return getImageContent("tv_banner", required, defaultLocaleListing);
	}
	
	public List<AbstractInputStreamContent> getTenInchScreenshots(LocaleListing defaultLocaleListing, Boolean required) {
		return getScreenshots("ten_inch_screenshots", required, defaultLocaleListing);
	}
	
	public List<AbstractInputStreamContent> getTvScreenshots(LocaleListing defaultLocaleListing, Boolean required) {
		return getScreenshots("tv_screenshots", required, defaultLocaleListing);
	}
	
	public List<AbstractInputStreamContent> getWearScreenshots(LocaleListing defaultLocaleListing, Boolean required) {
		return getScreenshots("wear_screenshots", required, defaultLocaleListing);
	}
	
	private List<AbstractInputStreamContent> getScreenshots(String screenSize, Boolean required, LocaleListing defaultLocaleListing) {
		List<AbstractInputStreamContent> abstractInputStreamContents = getScreenshots(screenSize);
		if (abstractInputStreamContents.isEmpty()) {
			abstractInputStreamContents = defaultLocaleListing.getScreenshots(screenSize);
		}
		if (abstractInputStreamContents.isEmpty() && required) {
			throw new UnexpectedException("images" + java.io.File.separator + screenSize + " was not found for locale " + getLanguageTag());
		}
		return abstractInputStreamContents;
	}
	
	private List<AbstractInputStreamContent> getScreenshots(String screenSize) {
		List<AbstractInputStreamContent> abstractInputStreamContents = Lists.newArrayList();
		for (int i = 1; i < 9; i++) {
			File file = new File(basePath + "images" + java.io.File.separator + screenSize + java.io.File.separator + "screenshot" + i + ".png");
			if (file.exists()) {
				abstractInputStreamContents.add(new FileContent("image/png", file));
			}
		}
		return abstractInputStreamContents;
	}
	
	private String getDetailsContent(String item, Boolean required, LocaleListing defaultLocaleListing) {
		String detailsContent = getDetailsContent(item);
		if (detailsContent == null) {
			detailsContent = defaultLocaleListing.getDetailsContent(item);
		}
		if (detailsContent == null && required) {
			throw new UnexpectedException(item + ".txt was not found for locale " + getLanguageTag());
		}
		return detailsContent;
	}
	
	private String getDetailsContent(String item) {
		File file = new File(basePath + item + ".txt");
		return file.exists() ? FileUtils.toString(file) : null;
	}
	
	private AbstractInputStreamContent getImageContent(String item, Boolean required, LocaleListing defaultLocaleListing) {
		AbstractInputStreamContent imageContent = getImagesContent(item);
		if (imageContent == null) {
			imageContent = defaultLocaleListing.getImagesContent(item);
		}
		if (imageContent == null && required) {
			throw new UnexpectedException("images/" + item + ".png was not found for locale " + getLanguageTag());
		}
		return imageContent;
	}
	
	private AbstractInputStreamContent getImagesContent(String item) {
		File file = new File(basePath + "images" + java.io.File.separator + item + ".png");
		return file.exists() ? new FileContent("image/png", file) : null;
	}
}
