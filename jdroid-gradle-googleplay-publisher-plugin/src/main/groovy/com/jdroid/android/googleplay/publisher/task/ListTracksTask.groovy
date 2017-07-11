package com.jdroid.android.googleplay.publisher.task

import com.google.api.services.androidpublisher.model.Track
import com.google.api.services.androidpublisher.model.TracksListResponse
import com.jdroid.android.googleplay.publisher.App
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher

public class ListTracksTask extends AbstractTask {

	public ListTracksTask() {
		description = "List all the assigned APKs for each release track"
	}

	@Override
	protected void onExecute(App app) {
		TracksListResponse tracksListResponse = GooglePlayPublisher.getTracks(app)
		println "-------------------------------"
		tracksListResponse.getTracks().forEach() { Track it ->
			println "Track: " + it.getTrack()
			println "Version codes: " + it.getVersionCodes()
			println "User fraction: " + it.getUserFraction()
			println "-------------------------------"
		}
	}
}
