package com.jdroid.android.googleplay.publisher.task;

import com.google.api.services.androidpublisher.model.Track;
import com.google.api.services.androidpublisher.model.TrackRelease;
import com.google.api.services.androidpublisher.model.TracksListResponse;
import com.jdroid.android.googleplay.publisher.App;
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher;

import org.gradle.api.logging.LogLevel;

public class ListTracksTask extends BaseTask {
	
	public ListTracksTask() {
		setDescription("List all the tracks and its releases");
	}

	@Override
	protected void onExecute(App app) {
		TracksListResponse tracksListResponse = GooglePlayPublisher.getTracks(app);
		
		for (Track track : tracksListResponse.getTracks()) {
			getLogger().log(LogLevel.INFO, "Track: " + track.getTrack());
			getLogger().log(LogLevel.INFO, "-------------------------------");
			for (TrackRelease trackRelease : track.getReleases()) {
				getLogger().log(LogLevel.INFO, "  Release name: " + trackRelease.getName());
				getLogger().log(LogLevel.INFO, "  Version codes: " + trackRelease.getVersionCodes());
				getLogger().log(LogLevel.INFO, "  User fraction: " + trackRelease.getUserFraction());
				getLogger().log(LogLevel.INFO, "  Status: " + trackRelease.getStatus());
				getLogger().log(LogLevel.INFO, "  Release Notes: " + trackRelease.getReleaseNotes());
			}
			getLogger().log(LogLevel.INFO, "/n");
		}
	}

}
