package com.jdroid.android.googleplay.publisher.task;

import com.google.api.services.androidpublisher.model.Track;
import com.google.api.services.androidpublisher.model.TrackRelease;
import com.jdroid.android.googleplay.publisher.App;
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher;

import org.gradle.api.logging.LogLevel;

public class ListTracksTask extends BaseTask {
	
	private LogLevel logLevel = LogLevel.LIFECYCLE;
	
	public ListTracksTask() {
		setDescription("List all the tracks and its releases");
	}

	@Override
	protected void onExecute(App app) {
		for (Track track : GooglePlayPublisher.getTracks(app)) {
			if (track.getReleases() != null) {
				getLogger().log(logLevel, "Track: " + track.getTrack());
				getLogger().log(logLevel, "-------------------------------");
				for (TrackRelease trackRelease : track.getReleases()) {
					getLogger().log(logLevel, "  Release name: " + trackRelease.getName());
					getLogger().log(logLevel, "  Version codes: " + trackRelease.getVersionCodes());
					getLogger().log(logLevel, "  User fraction: " + trackRelease.getUserFraction());
					getLogger().log(logLevel, "  Status: " + trackRelease.getStatus());
					getLogger().log(logLevel, "  Release Notes: " + trackRelease.getReleaseNotes());
					getLogger().log(logLevel, "");
				}
				getLogger().log(logLevel, "");
			}
		}
	}

}
