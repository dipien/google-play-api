# Google Play Publisher Gradle Plugin
Gradle Plugin to publish android APKs and listings on Google Play

## Setup

Add the following configuration to your `build.gradle`, replacing X.Y.Z by the [latest version](https://github.com/maxirosson/jdroid-googleplay-publisher/releases/latest)


    buildscript {
      repositories {
        jcenter()
      }
      dependencies {
        classpath 'com.jdroidframework:jdroid-googleplay-publisher-plugin:X.Y.Z'
      }
    }
    
    apply plugin: 'com.jdroid.googleplay.publisher'
    
Define the following gradle properties:

###### Application Id

The application id of your app. This property is required
    
    APPLICATION_ID = 'com.sample'
    
###### Private Key Json File Directory

Path to the directory where the private key json file is located. This property is required.
The private key json file path will generated as follows: **${PRIVATE_KEY_JSON_DIR}/${APPLICATION_ID}.json**

For example:
    
    PRIVATE_KEY_JSON_FILE_DIR = /credentials/googleplay
    APPLICATION_ID = 'com.sample'
    // The private key json file should be at /credentials/googleplay/com.sample.json

###### Locales

List of supported locales on Google Play. This property is required
    
    LOCALES = 'en-US,es-419'

## Usage

#### Verify Metadata

Verify that the metadata to upload to Google Play is valid.

    ./gradlew googlePlayVerifyMetadata
    
###### Video Required

If the video url is required. The default value is **false**

    VIDEO_REQUIRED = true
    
###### Promo Graphic Required

If the promo graphic is required. The default value is **true**

    PROMO_GRAPHIC_REQUIRED = false

###### Tv Banner Required

If the tv banner is required. The default value is **false**

    TV_BANNER_REQUIRED = true
    
###### Phone Screenshots Required

If the phone screenshots are required. The default value is **true**

    PHONE_SCREENSHOTS_REQUIRED = false
    
###### 7 inches Screenshots Required

If the 7 inches screenshots are required. The default value is **false**

    7_INCH_SCREENSHOTS_REQUIRED = true
    
###### 10 inches Screenshots Required

If the 10 inches screenshots are required. The default value is **false**

    10_INCH_SCREENSHOTS_REQUIRED = true
    
###### Tv Screenshots Required

If the tv screenshots are required. The default value is **false**

    TV_SCREENSHOTS_REQUIRED = true
    
###### Wear Screenshots Required

If the wear screenshots are required. The default value is **false**

    WEAR_SCREENSHOTS_REQUIRED = true

#### Publish Metadata

Publish metadata (feature/promo graphics, High resolution icon, screenshots, title, short description, full description and video url) on Google Play. The listings are published for each locales defined on the **LOCALES** property. If some asset is not available for any locale, the resources inside the **default** directory will be used

    ./gradlew googlePlayPublishMetadata
    
Create the following directories:

    {METADATA_PATH}/googleplay/{LOCALE_1}/
    {METADATA_PATH}/googleplay/{LOCALE_2}/
    {METADATA_PATH}/googleplay/default/
    
 
The **{METADATA_PATH}/googleplay/default/** directory is optional. It is only used when a asset is not found on it's locale directory. 
You can use it to host your shared assets between locales and avoid to duplicate it.

Add your assets to each locale or default directory:
    
|Asset                |Required|Location                                     |
| ------------------- | ------ | --------------------------------------------|
|Title                |true    |title.txt                                    |
|Short Description    |true    |short_description.txt                        |
|Full Description     |true    |full_description.txt                         |
|Video                |false   |video.txt                                    |
|Feature Graphic      |true    |images/featureGraphic.png                    |
|High Resolution Icon |true    |images/highResolutionIcon.png                |
|Tv Banner            |false   |images/tvBanner.png                          |
|Promo Graphic        |true    |images/promoGraphic.png                      |
|Phone Screenshots    |true    |phoneScreenshots/screenshot[1 ... 8].png     |
|7-inch Screenshots   |false   |sevenInchScreenshots/screenshot[1 ... 8].png |
|10-inch Screenshots  |false   |tenInchScreenshots/screenshot[1 ... 8].png   |
|Tv Screenshots       |false   |tvScreenshots/screenshot[1 ... 8].png        |
|Wear Screenshots     |false   |wearScreenshots/screenshot[1 ... 8].png      |


###### Metadata path

The path where all the listings directories will be located. The default value is the path of the gradle project where the plugin is applied

    METADATA_PATH = /path/to/the/metadata
    
#### List APKs

List all the historical APKs uploaded.

    ./gradlew googlePlayListAPKs
    
#### Publish APK

Upload new APK for your app and assign it to a release track.

    ./gradlew googlePlayPublishAPK
    
###### APK path

The path to the APK to publish

    APK_PATH = /path/to/apk/file.apk

###### Track

The release track that you're assigning APKs to. Acceptable values are: **alpha**, **beta**, **rollout** or **production**

    TRACK = alpha
    
###### User Fraction

Portion of the users who should get the staged rollout version of the APK. 
The maximum rollout fraction is 0.5 (50% of users). Only used if **TRACK** is **rollout**. Default value: 0.005 (0.5%)  
    
    USER_FRACTION = 0.1
    
###### Release Notes

Create the following files with the release notes:

    {METADATA_PATH}/googleplay/{LOCALE_1}/changelogs/{VERSION_CODE}.txt
    {METADATA_PATH}/googleplay/{LOCALE_2}/changelogs/{VERSION_CODE}.txt
    
###### Fail on APK upgrade version conflict

Whether the task should fail if the uploaded APK specifies a version code that has already been used. Default value: **true**
    
    FAIL_ON_APK_UPGRADE_VERSION_CONFLICT = false
    
#### Promote from Alpha to Beta

Promote a current alpha to beta

    ./gradlew googlePlayPromoteFromAlphaToBeta
    
#### Promote from Alpha to Rollout

Promote a current alpha to staged rollout

    ./gradlew googlePlayPromoteFromAlphaToRollout
    
###### User Fraction

Portion of the users who should get the staged rollout version of the APK. 
The maximum rollout fraction is 0.5 (50% of users). Default value: 0.005 (0.5%)
    
    USER_FRACTION = 0.2
    
#### Promote from Alpha to Beta

Promote a current alpha to beta

    ./gradlew googlePlayPromoteFromAlphaToBeta
    
#### Promote from Beta to Rollout

Promote a current beta to rollout

    ./gradlew googlePlayPromoteFromBetaToRollout

###### User Fraction

Portion of the users who should get the staged rollout version of the APK. 
The maximum rollout fraction is 0.5 (50% of users). Default value: 0.005 (0.5%)
    
    USER_FRACTION = 0.2
    
#### Increase Staged Rollout

Increase the fraction of users who should get the current staged rollout APK

    ./gradlew googlePlayIncreaseStagedRollout

###### User Fraction

Portion of the users who should get the staged rollout version of the APK. 
The maximum rollout fraction is 0.5 (50% of users).
    
    USER_FRACTION = 0.2
    
#### Promote from Rollout to Production

Promote a current staged rollout to production

    ./gradlew googlePlayPromoteFromRolloutToProduction

#### Clean Track

Remove all the APKs assigned to a track

    ./gradlew googlePlayCleanTrack

###### Track

The release track that you're cleaning. Acceptable values are: **alpha**, **beta**

    TRACK = alpha
    
#### List Tracks

List all the assigned APKs for each release track

    ./gradlew googlePlayListTracks

## Donations
Help us to continue with this project:

[![Donate](https://www.paypalobjects.com/en_US/i/btn/btn_donate_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=2UEBTRTSCYA9L)

<a href='https://pledgie.com/campaigns/30030'><img alt='Click here to lend your support to: Jdroid and make a donation at pledgie.com !' src='https://pledgie.com/campaigns/30030.png?skin_name=chrome' border='0' ></a>
