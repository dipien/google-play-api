# Google Play Publisher
Gradle Plugin & Java Library for Google Play API

## Continuous Integration
|Branch|Status|Workflows|Insights|
| ------------- | ------------- | ------------- | ------------- |
|master|[![CircleCI](https://circleci.com/gh/maxirosson/jdroid-googleplay-publisher/tree/master.svg?style=svg)](https://circleci.com/gh/maxirosson/jdroid-googleplay-publisher/tree/master)|[Workflows](https://circleci.com/gh/maxirosson/workflows/jdroid-googleplay-publisher/tree/master)|[Insights](https://circleci.com/build-insights/gh/maxirosson/jdroid-googleplay-publisher/master)|
|production|[![CircleCI](https://circleci.com/gh/maxirosson/jdroid-googleplay-publisher/tree/production.svg?style=svg)](https://circleci.com/gh/maxirosson/jdroid-googleplay-publisher/tree/production)|[Workflows](https://circleci.com/gh/maxirosson/workflows/jdroid-googleplay-publisher/tree/production)|[Insights](https://circleci.com/build-insights/gh/maxirosson/jdroid-googleplay-publisher/production)|

## Setup

Setup up your Google Developers Service Account

1. Open the [Google Play Console](https://play.google.com/apps/publish/)
1. Select **Settings**, followed by **API access**
1. If your account is not linked to a Google Developer Project, then click the **Create new project** button
1. Click the **Create Service Account** button and follow the **Google API Console** link in the dialog
1. Click the **Create Service account** button at the top of the developers console screen
1. Provide a name for the service account
1. Click **Select a role** and choose **Project -> Editor**
1. Check the **Furnish a new private key** checkbox
1. Select **JSON** as the Key type
1. Click **Create** to close the dialog
1. Make a note of the file name of the JSON file downloaded to your computer
1. Back on the Google Play developer console, click **Done** to close the dialog
1. Click on **Grant Access** for the newly added service account
1. Choose **Release Manager** from the **Role** dropdown
1. Click **Add user** to close the dialog

## Components

#### Gradle Plugin
* [jdroid-gradle-googleplay-publisher-plugin](/jdroid-gradle-googleplay-publisher-plugin)

#### Java Library
* [jdroid-java-googleplay-publisher](/jdroid-java-googleplay-publisher)

## Resources

* https://support.google.com/googleplay/android-developer/answer/1078870
* https://developer.android.com/distribute/best-practices/launch/store-listing.html
* https://developer.android.com/distribute/best-practices/launch/feature-graphic.html

## Donations
Help us to continue with this project:

[![Donate](https://www.paypalobjects.com/en_US/i/btn/btn_donate_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=2UEBTRTSCYA9L)
