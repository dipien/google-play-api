# Jdroid Google Play Publisher Plugin
Gradle Plugin to publish android APKs and listings on Google Play

## Setup

Create a project with the following structure:

    PROJECT_NAME/build.gradle
    PROJECT_NAME/googleplay/build.gradle
    PROJECT_NAME/googleplay/{LOCALE_1}/...
    PROJECT_NAME/googleplay/{LOCALE_2}/...
    PROJECT_NAME/googleplay/default/...
    
|Asset                |Required|Location                                              |File Name      |
| ------------------- | ------ | ---------------------------------------------------- | ------------- |
|Title                |true    |PROJECT_NAME/googleplay/{LOCALE}/details/             |title.txt      |
|Short Description    |true    |PROJECT_NAME/googleplay/{LOCALE}/details/             |shortDescription.txt      |
|Full Description     |true    |PROJECT_NAME/googleplay/{LOCALE}/details/             |fullDescription.txt      |
|Feature Graphic      |true    |PROJECT_NAME/googleplay/{LOCALE}/details/             |featureGraphic.png      |
|High Resolution Icon |true    |PROJECT_NAME/googleplay/{LOCALE}/details/             |highResolutionIcon.png      |
|Promo Graphic        |false   |PROJECT_NAME/googleplay/{LOCALE}/details/             |promoGraphic.png      |
|Phone Screenshots    |false   |PROJECT_NAME/googleplay/{LOCALE_1}/screenshots/phone/ |screenshot1.png ... screenshot8.png|
|7-inch Screenshots   |false   |PROJECT_NAME/googleplay/{LOCALE_1}/screenshots/tablet7/ |screenshot1.png ... screenshot8.png|
|10-inch Screenshots   |false   |PROJECT_NAME/googleplay/{LOCALE_1}/screenshots/tablet10/ |screenshot1.png ... screenshot8.png|




Add the following configuration to your root `build.gradle`:

    apply plugin: 'com.jdroid.googleplay.publisher'

    buildscript {
      repositories {
        mavenCentral()
      }
      dependencies {
        classpath 'com.jdroidframework:jdroid-googleplay-publisher-plugin:0.1.0'
      }
    }
    
    ext.APPLICATION_ID = 'com.sample' // Replace by the application id of your app
    ext.LOCALES = 'en-US,es-419' // Replace by a list of supported locales on Google Play

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
    
#### Publish listing task

Publish listings (feature/promo graphics, High resolution icon, screenshots, title, short and full descriptions) on Google Play.

    ./gradlew :googleplay:googlePlayPublishListings

## Donations
Help us to continue with this project:

[![Donate](https://www.paypalobjects.com/en_US/i/btn/btn_donate_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=2UEBTRTSCYA9L)

<a href='https://pledgie.com/campaigns/30030'><img alt='Click here to lend your support to: Jdroid and make a donation at pledgie.com !' src='https://pledgie.com/campaigns/30030.png?skin_name=chrome' border='0' ></a>
