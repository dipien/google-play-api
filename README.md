[![Dipien](https://raw.githubusercontent.com/dipien/dipien-component-builder/master/.github/dipien_logo.png)](http://www.dipien.com)

# Google Play API
Gradle Plugin & Kotlin Library for Google Play API

## Continuous Integration
|Branch|Status|Workflows|Insights|
| ------------- | ------------- | ------------- | ------------- |
|master|[![CircleCI](https://circleci.com/gh/dipien/google-play-api/tree/master.svg?style=svg)](https://circleci.com/gh/dipien/google-play-api/tree/master)|[Workflows](https://circleci.com/gh/dipien/workflows/google-play-api/tree/master)|[Insights](https://circleci.com/build-insights/gh/dipien/google-play-api/master)|
|production|[![CircleCI](https://circleci.com/gh/dipien/google-play-api/tree/production.svg?style=svg)](https://circleci.com/gh/dipien/google-play-api/tree/production)|[Workflows](https://circleci.com/gh/dipien/workflows/google-play-api/tree/production)|[Insights](https://circleci.com/build-insights/gh/dipien/google-play-api/production)|

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
* [google-play-api-plugin](/google-play-api-plugin)

#### Kotlin Client
* [google-play-api-kotlin](/google-play-api-kotlin)

## Resources

* https://support.google.com/googleplay/android-developer/answer/1078870
* https://developer.android.com/distribute/best-practices/launch/store-listing.html
* https://developer.android.com/distribute/best-practices/launch/feature-graphic.html

## Donations

Donations are greatly appreciated. You can help us to pay for our domain and this project development.

* [Donate cryptocurrency](http://coinbase.dipien.com/)
* [Donate with PayPal](http://paypal.dipien.com/)
* [Donate on Patreon](http://patreon.dipien.com/)

## Follow us
* [Twitter](http://twitter.dipien.com)
* [Medium](http://medium.dipien.com)
* [Instagram](http://instagram.dipien.com)
* [Pinterest](http://pinterest.dipien.com)
* [GitHub](http://github.dipien.com)
* [Blog](http://blog.dipien.com)
