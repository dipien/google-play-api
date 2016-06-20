package com.jdroid.android.googleplay.publisher;

import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.FileContent;
import com.google.api.client.util.Lists;
import com.jdroid.java.http.MimeType;
import com.jdroid.java.utils.FileUtils;

import java.io.File;
import java.util.List;
import java.util.Locale;

public class LocaleListing {
	
	private Locale locale;
	private String basePath;
	
	public LocaleListing(Locale locale, String listingPath) {
		this.locale = locale;
		this.basePath = listingPath + java.io.File.separator + (locale != null ? locale.getLanguage() : "default") + java.io.File.separator;
	}
	
	public Locale getLocale() {
		return locale;
	}
	
	public String getTitle() {
		return getDetailsContent("title");
	}
	
	public String getShortDescription() {
		return getDetailsContent("shortDescription");
	}
	
	public String getFullDescription() {
		return getDetailsContent("fullDescription");
	}
	
	public String getRecentChanges() {
		return getDetailsContent("recentChanges");
	}
	
	public AbstractInputStreamContent getFeatureGraphic() {
		return getAssetsContent("featureGraphic");
	}
	
	public AbstractInputStreamContent getPromoGraphic() {
		return getAssetsContent("promoGraphic");
	}
	
	public AbstractInputStreamContent getHighResolutionIcon() {
		return getAssetsContent("highResolutionIcon");
	}
	
	public List<AbstractInputStreamContent> getPhoneScreenshots() {
		return getScreenshots("phone");
	}
	
	public List<AbstractInputStreamContent> getSevenInchScreenshots() {
		return getScreenshots("tablet7");
	}
	
	public List<AbstractInputStreamContent> getTenInchScreenshots() {
		return getScreenshots("tablet10");
	}
	
	private List<AbstractInputStreamContent> getScreenshots(String screenSize) {
		List<AbstractInputStreamContent> abstractInputStreamContents = Lists.newArrayList();
		for (int i = 1; i < 9; i++) {
			File file = new File(basePath + "screenshots" + java.io.File.separator + screenSize + java.io.File.separator + "screenshot" + i + ".png");
			if (file.exists()) {
				abstractInputStreamContents.add(new FileContent(MimeType.PNG, file));
			}
		}
		return abstractInputStreamContents;
	}
	
	private String getDetailsContent(String item) {
		File file = new File(basePath+ "details" + java.io.File.separator + item + ".txt");
		return file.exists() ? FileUtils.toString(file) : null;
	}
	
	private AbstractInputStreamContent getAssetsContent(String item) {
		File file = new File(basePath + "assets" + java.io.File.separator + item + ".png");
		return file.exists() ? new FileContent(MimeType.PNG, file) : null;
	}
}
