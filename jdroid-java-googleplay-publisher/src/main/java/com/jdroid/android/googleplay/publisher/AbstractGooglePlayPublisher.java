package com.jdroid.android.googleplay.publisher;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.AndroidPublisherScopes;
import com.jdroid.java.utils.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;


public class AbstractGooglePlayPublisher {

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;

	/**
	 * Performs all necessary setup steps for running requests against the API.
	 *
	 * @param appContext the {@link AppContext}
	 * @return the {@link AndroidPublisher} service
	 */
	protected static AndroidPublisher init(AppContext appContext) {

		if (StringUtils.isEmpty(appContext.getApplicationId())) {
			throw new RuntimeException("The application id is required");
		}

		try {

			if (HTTP_TRANSPORT == null) {
				HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			}

			// Authorization.
			HttpRequestInitializer credential = authorizeWithServiceAccount(appContext);
			credential = setHttpTimeout(appContext, credential);

			// Set up and return API client.
			AndroidPublisher.Builder builder = new AndroidPublisher.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential);
			builder.setApplicationName(appContext.getApplicationId());
			return builder.build();
		} catch (GeneralSecurityException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static HttpRequestInitializer setHttpTimeout(AppContext appContext, HttpRequestInitializer requestInitializer) {
		return new HttpRequestInitializer() {
			@Override
			public void initialize(HttpRequest httpRequest) throws IOException {
				requestInitializer.initialize(httpRequest);
				httpRequest.setConnectTimeout(appContext.getConnectTimeout());
				httpRequest.setReadTimeout(appContext.getReadTimeout());
			}
		};
	}

	private static Credential authorizeWithServiceAccount(AppContext appContext) {

		if (StringUtils.isEmpty(appContext.getPrivateKeyJsonFilePath())) {
			throw new RuntimeException("The private key json file path is required");
		}

		try {
			InputStream serviceAccountStream = new FileInputStream(appContext.getPrivateKeyJsonFilePath());
			GoogleCredential credential = GoogleCredential.fromStream(serviceAccountStream, HTTP_TRANSPORT, JSON_FACTORY);
			return credential.createScoped(Collections.singleton(AndroidPublisherScopes.ANDROIDPUBLISHER));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected static void log(String message) {
		// TODO Use a logger
		System.out.println(message);
	}
}
