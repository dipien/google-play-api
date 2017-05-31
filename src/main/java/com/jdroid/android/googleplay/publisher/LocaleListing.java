package com.jdroid.android.googleplay.publisher;

import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.FileContent;
import com.google.api.client.util.Lists;
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
	
	public Locale getLocale() {
		return locale;
	}
	
	public String getTitle() {
		return getDetailsContent("title");
	}
	
	public String getShortDescription() {
		return getDetailsContent("short_description");
	}
	
	public String getFullDescription() {
		return getDetailsContent("full_description");
	}
	
	public String getVideo() {
		return getDetailsContent("video");
	}
	
	public String getRecentChanges() {
		return getDetailsContent("recentChanges");
	}
	
	public AbstractInputStreamContent getFeatureGraphic() {
		return getImagesContent("featureGraphic");
	}
	
	public AbstractInputStreamContent getPromoGraphic() {
		return getImagesContent("promoGraphic");
	}
	
	public AbstractInputStreamContent getHighResolutionIcon() {
		return getImagesContent("icon");
	}
	
	public List<AbstractInputStreamContent> getPhoneScreenshots() {
		return getScreenshots("phoneScreenshots");
	}
	
	public List<AbstractInputStreamContent> getSevenInchScreenshots() {
		return getScreenshots("sevenInchScreenshots");
	}
	
	public List<AbstractInputStreamContent> getTenInchScreenshots() {
		return getScreenshots("tenInchScreenshots");
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
	
	private String getDetailsContent(String item) {
		File file = new File(basePath + item + ".txt");
		return file.exists() ? FileUtils.toString(file) : null;
	}
	
	private AbstractInputStreamContent getImagesContent(String item) {
		File file = new File(basePath + "images" + java.io.File.separator + item + ".png");
		return file.exists() ? new FileContent("image/png", file) : null;
	}
}
