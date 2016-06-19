package com.jdroid.android.googleplay.publisher;

public enum TrackType {
	
	ALPHA("alpha"),
	BETA("beta"),
	PRODUCTION("production"),
	ROLLOUT("rollout");
	
	private String key;
	
	TrackType(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	
	public static TrackType findByKey(String key) {
		for (TrackType each : TrackType.values()) {
			if (each.getKey().equals(key)) {
				return each;
			}
		}
		return null;
	}
}
