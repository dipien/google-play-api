package com.jdroid.android.googleplay.publisher;

public enum ImageType {
	
	FEATURE_GRAPHIC("featureGraphic"),
	ICON("icon"),
	PHONE_SCREENSHOTS("phoneScreenshots"),
	PROMO_GRAPHIC("promoGraphic"),
	SEVEN_INCH_SCREENSHOTS("sevenInchScreenshots"),
	TEN_INCH_SCREENSHOTS("tenInchScreenshots"),
	TV_BANNER("tvBanner"),
	TV_SCREENSHOTS("tvScreenshots");
	
	private String key;
	
	ImageType(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	
}
