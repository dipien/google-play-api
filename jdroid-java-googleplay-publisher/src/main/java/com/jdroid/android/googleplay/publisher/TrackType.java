package com.jdroid.android.googleplay.publisher;

public enum TrackType {
	
	INTERNAL("internal"),
	ALPHA("alpha"),
	BETA("beta"),
	PRODUCTION("production");
	
	private String key;
	
	TrackType(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	
	public static TrackType findByKey(String key, TrackType defaulTrackType) {
		for (TrackType each : TrackType.values()) {
			if (each.getKey().equals(key)) {
				return each;
			}
		}
		return defaulTrackType;
	}

	public static TrackType findByKey(String key) {
		return findByKey(key, null);
	}
}
