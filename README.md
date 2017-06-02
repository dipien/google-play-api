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

Add the following configuration to your `build.gradle`, replacing X.Y.Z by the [latest version](https://github.com/maxirosson/jdroid-googleplay-publisher-plugin/releases/latest)


    buildscript {
      repositories {
        jcenter()
      }
      dependencies {
        classpath 'com.jdroidframework:jdroid-googleplay-publisher-plugin:X.Y.Z'
      }
    }
    
    apply plugin: 'com.jdroid.googleplay.publisher'
    
You need to define the following gradle properties:

###### Private Key Json File

Path to the private key json file. This property is required
    
    PRIVATE_KEY_JSON_FILE = /path/to/json/file
    
###### Application Id

The application id of your app. This property is required
    
    APPLICATION_ID = 'com.sample'

###### Locales

List of supported locales on Google Play. This property is required
    
    LOCALES = 'en-US,es-419'

## Usage

#### Verify Metadata Task

Verify that the metadata to upload to Google Play is valid.

    ./gradlew googlePlayVerifyMetadata
    
###### Video Required

If the video url is required. The default value is **false**

    VIDEO_REQUIRED = true
    
###### Promo Graphic Required

If the promo graphic is required. The default value is **true**

    PROMO_GRAPHIC_REQUIRED = false
    
###### Phone Screenshots Required

If the phone screenshots are required. The default value is **true**

    PHONE_SCREENSHOTS_REQUIRED = false
    
###### 7 inches Screenshots Required

If the 7 inches screenshots are required. The default value is **false**

    7_INCH_SCREENSHOTS_REQUIRED = true
    
###### 10 inches Screenshots Required

If the 10 inches screenshots are required. The default value is **false**

    10_INCH_SCREENSHOTS_REQUIRED = true

#### Publish Listings task

Publish listings (feature/promo graphics, High resolution icon, screenshots, title, short description, full description and video url) on Google Play. The listings are published for each locales defined on the **LOCALES** property. If some asset is not available for any locale, the resources inside the **default** directory will be used

    ./gradlew googlePlayPublishListings
    
Create the following directories:

    {LISTING_PATH}/googleplay/{LOCALE_1}/...
    {LISTING_PATH}/googleplay/{LOCALE_2}/...
    {LISTING_PATH}/googleplay/default/...
    
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
    

###### Listing path

The path where all the listings directories will be located. The default value is the path of the gradle project where the plugin is applied

    LISTING_PATH = /path/to/the/listings
    
#### List APKs task

List all the historical APKs uploaded.

    ./gradlew googlePlayListAPKs
    
#### Publish APKs Task

Upload new APKs for your app and assign them to different release tracks.

    ./gradlew googlePlayPublishAPKs
    
###### APK path

The path to the APK to upload

    APK_PATH = /path/to/apk/file.apk

###### Track

The release track that you're assigning APKs to. Acceptable values are: **alpha**, **beta**, **rollout** or **production**

    TRACK = alpha
    
###### User Fraction

Portion of the users who should get the staged rollout version of the APK. Only specified if **TRACK** is **rollout**  
    
    USER_FRACTION = 0.5

## Donations
Help us to continue with this project:

[![Donate](https://www.paypalobjects.com/en_US/i/btn/btn_donate_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=2UEBTRTSCYA9L)

<a href='https://pledgie.com/campaigns/30030'><img alt='Click here to lend your support to: Jdroid and make a donation at pledgie.com !' src='https://pledgie.com/campaigns/30030.png?skin_name=chrome' border='0' ></a>
