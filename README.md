# Google Play Publisher Plugin
Gradle Plugin to publish android APKs and listings on Google Play

## Continuous Integration
|Branch|Status|
| ------------- | ------------- |
|Master|[![Build Status](https://travis-ci.org/maxirosson/jdroid-googleplay-publisher-plugin.svg?branch=master)](https://travis-ci.org/maxirosson/jdroid-googleplay-publisher-plugin)|
|Staging|[![Build Status](https://api.travis-ci.org/maxirosson/jdroid-googleplay-publisher-plugin.svg?branch=staging)](https://travis-ci.org/maxirosson/jdroid-googleplay-publisher-plugin)|
|Production|[![Build Status](https://api.travis-ci.org/maxirosson/jdroid-googleplay-publisher-plugin.svg?branch=production)](https://travis-ci.org/maxirosson/jdroid-googleplay-publisher-plugin)|

## Setup

Setup up your Google Developers Service Account

1. Open the [Google Play Console](https://play.google.com/apps/publish/)
1. Select **Settings** tab, followed by the **API access** tab
1. Click the **Create Service Account** button and follow the **Google API Console** link in the dialog
1. Click the **Create Service account** button at the top of the developers console screen
1. Provide a name for the service account
1. Click **Select a role** and choose **Project > Service Account Actor**
1. Check the **Furnish a new private key** checkbox
1. Select **JSON** as the Key type
1. Click **Create** to close the dialog
1. Make a note of the file name of the JSON file downloaded to your computer
1. Back on the Google Play developer console, click **Done** to close the dialog
1. Click on **Grant Access** for the newly added service account
1. Choose **Release Manager** from the **Role** dropdown
1. Click **Add user** to close the dialog



Create a gradle project with the following structure:

    build.gradle
    googleplay/{LOCALE_1}/...
    googleplay/{LOCALE_2}/...
    googleplay/default/...
    
|Asset                |Required|Location                                                                     |
| ------------------- | ------ | ----------------------------------------------------------------------------|
|Title                |true    |googleplay/{LOCALE}/title.txt                                    |
|Short Description    |true    |googleplay/{LOCALE}/short_description.txt                        |
|Full Description     |true    |googleplay/{LOCALE}/full_description.txt                         |
|Video                |false   |googleplay/{LOCALE}/video.txt                                    |
|Feature Graphic      |true    |googleplay/{LOCALE}/images/featureGraphic.png                    |
|High Resolution Icon |true    |googleplay/{LOCALE}/images/highResolutionIcon.png                |
|Promo Graphic        |true    |googleplay/{LOCALE}/images/promoGraphic.png                      |
|Phone Screenshots    |true    |googleplay/{LOCALE}/phoneScreenshots/screenshot[1 ... 8].png     |
|7-inch Screenshots   |false   |googleplay/{LOCALE}/sevenInchScreenshots/screenshot[1 ... 8].png |
|10-inch Screenshots  |false   |googleplay/{LOCALE}/tenInchScreenshots/screenshot[1 ... 8].png   |


Add the following configuration to your `build.gradle`, replacing X.Y.Z by the [latest version](https://github.com/maxirosson/jdroid-googleplay-publisher-plugin/releases/latest)


    buildscript {
      repositories {
        jcenter()
      }
      dependencies {
        classpath 'com.jdroidframework:jdroid-googleplay-publisher-plugin:X.Y.Z'
      }
    }
    
    ext.APPLICATION_ID = 'com.sample' // Replace by the application id of your app
    ext.LOCALES = 'en-US,es-419' // Replace by a list of supported locales on Google Play
    ext.VIDEO_REQUIRED = false // Override required validation for the vide url
    ext.PROMO_GRAPHIC_REQUIRED = false // Override required validation for the promo graphic
    ext.PHONE_SCREENSHOTS_REQUIRED = false // Override required validation for the phone screenshots
    ext.7_INCH_SCREENSHOTS_REQUIRED = true // Override required validation for the 7 inch screenshots
    ext.10_INCH_SCREENSHOTS_REQUIRED = true // Override required validation for the 10 inch screenshots

    apply plugin: 'com.jdroid.googleplay.publisher'
    
You need to create a gradle.properties file on the root of the project with the following properties or send them as parameters when executing a task

###### Private Key Json File

Path to the private key json file. This property is required
    
    PRIVATE_KEY_JSON_FILE = /path/to/json/file

## Usage

#### List APKs task

List all the historical APKs uploaded.

    ./gradlew googlePlayListAPKs
    
#### Publish Listings task

Publish listings (feature/promo graphics, High resolution icon, screenshots, title, short description, full description and video url) on Google Play. The listings are published for each locales defined on the **LOCALES** property. If some asset is not available for any locale, the resources inside the **default** directory will be used

    ./gradlew googlePlayPublishListings

#### Verify Metadata Task

Verify that the metadata to upload to Google Play is valid.

    ./gradlew googlePlayVerifyMetadata

## Donations
Help us to continue with this project:

[![Donate](https://www.paypalobjects.com/en_US/i/btn/btn_donate_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=2UEBTRTSCYA9L)

<a href='https://pledgie.com/campaigns/30030'><img alt='Click here to lend your support to: Jdroid and make a donation at pledgie.com !' src='https://pledgie.com/campaigns/30030.png?skin_name=chrome' border='0' ></a>
