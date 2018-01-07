package com.jdroid.android.googleplay.publisher.task;

import com.google.api.services.androidpublisher.model.Track;
import com.google.api.services.androidpublisher.model.TracksListResponse;
import com.jdroid.android.googleplay.publisher.App;
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher;

import org.gradle.api.logging.LogLevel;

public class ListTracksTask extends BaseTask {
	
	public ListTracksTask() {
		setDescription("List all the assigned APKs for each release track");
	}

	@Override
	protected void onExecute(App app) {
		TracksListResponse tracksListResponse = GooglePlayPublisher.getTracks(app);
		getLogger().log(LogLevel.INFO, "-------------------------------");
		for (Track track : tracksListResponse.getTracks()) {
			getLogger().log(LogLevel.INFO, "Track: " + track.getTrack());
			getLogger().log(LogLevel.INFO, "Version codes: " + track.getVersionCodes());
			getLogger().log(LogLevel.INFO, "User fraction: " + track.getUserFraction());
			getLogger().log(LogLevel.INFO, "-------------------------------");
		}
	}

}
