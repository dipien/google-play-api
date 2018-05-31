package com.jdroid.android.googleplay.publisher;

public enum TrackReleaseStatus {
	
	COMPLETED("completed"),
	DRAFT("draft"),
	HALTED("halted"),
	IN_PROGRESS("inProgress");
	
	private String key;
	
	TrackReleaseStatus(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	
	public static TrackReleaseStatus findByKey(String key) {
		for (TrackReleaseStatus each : TrackReleaseStatus.values()) {
			if (each.getKey().equals(key)) {
				return each;
			}
		}
		return null;
	}
}
