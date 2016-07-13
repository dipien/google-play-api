# Google Play Publisher Plugin
Gradle Plugin to publish android APKs and listings on Google Play

## Continuous Integration
|Branch|Status|
| ------------- | ------------- |
|Master|[![Build Status](https://travis-ci.org/maxirosson/jdroid-googleplay-publisher-plugin.svg?branch=master)](https://travis-ci.org/maxirosson/jdroid-googleplay-publisher-plugin)|
|Staging|[![Build Status](https://api.travis-ci.org/maxirosson/jdroid-googleplay-publisher-plugin.svg?branch=staging)](https://travis-ci.org/maxirosson/jdroid-googleplay-publisher-plugin)|
|Production|[![Build Status](https://api.travis-ci.org/maxirosson/jdroid-googleplay-publisher-plugin.svg?branch=production)](https://travis-ci.org/maxirosson/jdroid-googleplay-publisher-plugin)|

## Setup

Create a project with the following structure:

    PROJECT_DIR/build.gradle
    PROJECT_DIR/settings.gradle
    PROJECT_DIR/googleplay/build.gradle
    PROJECT_DIR/googleplay/{LOCALE_1}/...
    PROJECT_DIR/googleplay/{LOCALE_2}/...
    PROJECT_DIR/googleplay/default/...
    
|Asset                |Required|Location                                                                     |
| ------------------- | ------ | ----------------------------------------------------------------------------|
|Title                |true    |PROJECT_DIR/googleplay/{LOCALE}/details/title.txt                            |
|Short Description    |true    |PROJECT_DIR/googleplay/{LOCALE}/details/shortDescription.txt                 |
|Full Description     |true    |PROJECT_DIR/googleplay/{LOCALE}/details/fullDescription.txt                  |
|Feature Graphic      |true    |PROJECT_DIR/googleplay/{LOCALE}/details/featureGraphic.png                   |
|High Resolution Icon |true    |PROJECT_DIR/googleplay/{LOCALE}/details/highResolutionIcon.png               |
|Promo Graphic        |true    |PROJECT_DIR/googleplay/{LOCALE}/details/promoGraphic.png                     |
|Phone Screenshots    |true    |PROJECT_DIR/googleplay/{LOCALE}/screenshots/phone/screenshot[1 ... 8].png    |
|7-inch Screenshots   |false   |PROJECT_DIR/googleplay/{LOCALE}/screenshots/tablet7/screenshot[1 ... 8].png  |
|10-inch Screenshots  |false   |PROJECT_DIR/googleplay/{LOCALE}/screenshots/tablet10/screenshot[1 ... 8].png |


Add the following configuration to your `PROJECT_DIR/build.gradle`, replacing X.Y.Z by the [latest version](https://github.com/maxirosson/jdroid-googleplay-publisher-plugin/releases/latest)


    buildscript {
      repositories {
        mavenCentral()
      }
      dependencies {
        classpath 'com.jdroidframework:jdroid-googleplay-publisher-plugin:X.Y.Z'
      }
    }
    
    ext.APPLICATION_ID = 'com.sample' // Replace by the application id of your app
    ext.LOCALES = 'en-US,es-419' // Replace by a list of supported locales on Google Play
    ext.PROMO_GRAPHIC_REQUIRED = false // Override required validation for the promo graphic
    ext.PHONE_SCREENSHOTS_REQUIRED = false // Override required validation for the phone screenshots
    ext.7_INCH_SCREENSHOTS_REQUIRED = true // Override required validation for the 7 inch screenshots
    ext.10_INCH_SCREENSHOTS_REQUIRED = true // Override required validation for the 10 inch screenshots

    
Add the following configuration to your `PROJECT_DIR/googleplay/build.gradle`:

    apply plugin: 'com.jdroid.googleplay.publisher'
    
Add the following configuration to your `PROJECT_DIR/settings.gradle`:
    
    include ':googleplay'

## Configuration

You need to create a gradle.properties file on the root of the project with the following properties or send them as parameters when executing a task

###### Service Account Email

The service account email used to authenticate on Google Play. This property is required

    SERVICE_ACCOUNT_EMAIL = xxx@developer.gserviceaccount.com
    
###### Private Key File

Path to the private key file (.p12 extension). This property is required
    
    PRIVATE_KEY_FILE = /path/to/.p12/file

## Usage

#### List APKs task

List all the historical APKs uploaded.

    ./gradlew :googleplay:googlePlayListAPKs
    
#### Publish Listings task

Publish listings (feature/promo graphics, High resolution icon, screenshots, title, short and full descriptions) on Google Play. The listings are published for each locales defined on the LOCALES property. If some asset is not available for any locale, the assets inside the `default` directory will be used

    ./gradlew :googleplay:googlePlayPublishListings

#### Verify Content Task

Verify that the content to upload to Google Play is valid.

    ./gradlew :googleplay:googlePlayVerifyContent

## Donations
Help us to continue with this project:

[![Donate](https://www.paypalobjects.com/en_US/i/btn/btn_donate_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=2UEBTRTSCYA9L)

<a href='https://pledgie.com/campaigns/30030'><img alt='Click here to lend your support to: Jdroid and make a donation at pledgie.com !' src='https://pledgie.com/campaigns/30030.png?skin_name=chrome' border='0' ></a>
