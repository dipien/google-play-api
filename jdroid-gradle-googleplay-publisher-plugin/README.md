# Google Play Publisher Gradle Plugin
Gradle Plugin to publish android APKs and listings on Google Play

## Setup

Add the following configuration to your `build.gradle`, replacing X.Y.Z by the [latest version](https://github.com/maxirosson/jdroid-googleplay-publisher/releases/latest)


    buildscript {
      repositories {
        jcenter()
      }
      dependencies {
        classpath 'com.jdroidtools:jdroid-gradle-googleplay-publisher-plugin:X.Y.Z'
      }
    }
    
    apply plugin: 'com.jdroid.googleplay.publisher'
    
Define the following gradle properties:

###### Application Id

The application id of your app. This property is required
    
    APPLICATION_ID = 'com.sample'
    
###### Private Key Json File Path

Path to the private key json file. This property is required.

For example:

    PRIVATE_KEY_JSON_FILE_PATH = '/credentials/googleplay/key.json'

## Usage

#### Verify Metadata

Verify that the metadata to upload to Google Play is valid.

    ./gradlew googlePlayVerifyMetadata

###### Locales

List of supported locales on Google Play. This property is required

    LOCALES = 'en-US,es-419'
    
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
    
|Asset                |Required|Location                                            |
| ------------------- | ------ | ---------------------------------------------------|
|Title                |true    |title.txt                                           |
|Short Description    |true    |short_description.txt                               |
|Full Description     |true    |full_description.txt                                |
|Video                |false   |video.txt                                           |
|Feature Graphic      |true    |images/featureGraphic.png                           |
|High Resolution Icon |true    |images/icon.png                                     |
|Tv Banner            |false   |images/tvBanner.png                                 |
|Promo Graphic        |true    |images/promoGraphic.png                             |
|Phone Screenshots    |true    |images/phoneScreenshots/screenshot[1 ... 8].png     |
|7-inch Screenshots   |false   |images/sevenInchScreenshots/screenshot[1 ... 8].png |
|10-inch Screenshots  |false   |images/tenInchScreenshots/screenshot[1 ... 8].png   |
|Tv Screenshots       |false   |images/tvScreenshots/screenshot[1 ... 8].png        |
|Wear Screenshots     |false   |images/wearScreenshots/screenshot[1 ... 8].png      |

###### Locales

List of supported locales on Google Play. This property is required

    LOCALES = 'en-US,es-419'

###### Metadata path

The path where all the listings directories will be located. The default value is the path of the gradle project where the plugin is applied

    METADATA_PATH = /path/to/the/metadata
    
#### List APKs

List all the historical APKs uploaded.

    ./gradlew googlePlayListAPKs

#### List Bundles

List all the historical Bundles uploaded.

    ./gradlew googlePlayListBundles

#### Publish APK / Bundle

Upload new APK / Bundle for your app and assign it to a release track.

    ./gradlew googlePlayPublishAPK

    // or

    ./gradlew googlePlayPublishBundle

###### Locales

List of supported locales on Google Play. This property is required if you need to upload changelogs

    LOCALES = 'en-US,es-419'

###### Release Name

The name to identify release in the Play Console only, such as an internal code name or build version. Default value: the version name

    RELEASE_NAME = 'My release'

###### Draft

Whether the release should be created on draft mode. Default value: false

    DRAFT = true
    
###### APK path / Bundle path

The path to the APK / Bundle to publish

    APK_PATH = /path/to/apk/file.apk
    // or
    BUNDLE_PATH = /path/to/bundle/file.aab

###### Track

The release track that you're assigning APKs to. Acceptable values are: **internal**, **alpha**, **beta** or **production**

    TRACK = production
    
###### User Fraction

Fraction of users who are eligible to receive the release. 0 <= fraction < 1
Only used if **TRACK** is **production**. Default value: the previous rollout user fraction (if any) or 100%
    
    USER_FRACTION = 0.1
    
###### Release Notes

Create the following files with the release notes:

    {METADATA_PATH}/googleplay/{LOCALE_1}/changelogs/{VERSION_CODE}.txt
    {METADATA_PATH}/googleplay/{LOCALE_2}/changelogs/{VERSION_CODE}.txt
    
###### Fail on APK upgrade version conflict

Whether the task should fail if the uploaded APK specifies a version code that has already been used. Default value: **true**
    
    FAIL_ON_APK_UPGRADE_VERSION_CONFLICT = false
    
#### Promote from Internal to Alpha

Promote a current internal to alpha

    ./gradlew googlePlayPromoteFromInternalToAlpha

#### Promote from Internal to Beta

Promote a current internal to beta

    ./gradlew googlePlayPromoteFromInternalToBeta

#### Promote from Internal to Production

Promote a current internal to production

    ./gradlew googlePlayPromoteFromInternalToProduction

###### User Fraction

Fraction of users who are eligible to receive the release. 0 <= fraction < 1. Default value: the previous rollout user fraction (if any) or 100%

    USER_FRACTION = 0.2

#### Promote from Alpha to Beta

Promote a current alpha to beta

    ./gradlew googlePlayPromoteFromAlphaToBeta

###### Release Name

Set a release name if you have more than one alpha track and you need to promote just one

    RELEASE_NAME = 'My release'
    
#### Promote from Alpha to Production

Promote a current alpha to production

    ./gradlew googlePlayPromoteFromAlphaToProduction

###### User Fraction

Fraction of users who are eligible to receive the release. 0 <= fraction < 1. Default value: the previous rollout user fraction (if any) or 100%

    USER_FRACTION = 0.2

###### Release Name

Set a release name if you have more than one alpha track and you need to promote just one

    RELEASE_NAME = 'My release'

#### Promote from Beta to Production

Promote a current beta to production

    ./gradlew googlePlayPromoteFromBetaToProduction

###### User Fraction

Fraction of users who are eligible to receive the release. 0 <= fraction < 1. Default value: the previous rollout user fraction (if any) or 100%

    USER_FRACTION = 0.2
    
#### Increase Staged Rollout

Increase the fraction of users who should get the current staged rollout

    ./gradlew googlePlayIncreaseStagedRollout

###### User Fraction

Fraction of users who are eligible to receive the release. 0 <= fraction < 1.
    
    USER_FRACTION = 0.2

#### Halt Staged Rollout

Halt the current staged rollout

    ./gradlew googlePlayHaltStagedRollout

#### Resume Staged Rollout

Resume the current staged rollout

    ./gradlew googlePlayResumeStagedRollout

#### Complete Staged Rollout

Rollout the release to 100% of users

    ./gradlew googlePlayCompleteStageRollout

#### List Tracks

List all the assigned APKs for each release track

    ./gradlew googlePlayListTracks

## Donations
Help us to continue with this project:

[![Donate](https://www.paypalobjects.com/en_US/i/btn/btn_donate_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=2UEBTRTSCYA9L)
