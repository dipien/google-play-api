[![Dipien](https://raw.githubusercontent.com/dipien/dipien-component-builder/master/.github/dipien_logo.png)](https://medium.com/dipien)

# Google Play API Gradle Plugin
Gradle Plugin to publish android APKs/bundles and listings on Google Play

## Setup

Add the following configuration to your root `build.gradle`, replacing X.Y.Z by the [latest version](https://github.com/dipien/google-play-api/releases/latest)


    buildscript {
      repositories {
        mavenCentral()
      }
      dependencies {
        classpath 'com.dipien:google-play-api-plugin:X.Y.Z'
      }
    }
    
    apply plugin: 'com.dipien.googleplay.api'

All the plugin configuration properties can be added using any of the following ways:

* Using the **googlePlay** extension on the build.gradle. For example:

      googlePlay {
          applicationId 'com.sample'
      }

* As a command line parameter. For example:

      ./gradlew googlePlayVerifyMetadata -PapplicationId=com.sample

* As a property on a gradle.properties file

      applicationId = 'com.sample'

* As an extra property on the build.gradle

      ext.applicationId = 'com.sample'

* As a System Environment property


#### Common Properties

###### Application Id

The application id of your app. This property is required
    
    applicationId = 'com.sample'
    
###### Private Key Json File Path

Path to the private key json file. This property is required.

For example:

    privateKeyJsonFilePath = '/credentials/googleplay/key.json'

###### Connect Timeout

The connect timeout (in milliseconds) when executing requests against the Google API. The default value is 100 seconds

For example:

    connectTimeout = 200000

###### Read Timeout

The read timeout (in milliseconds) when executing requests against the Google API. The default value is 100 seconds

    readTimeout = 200000

###### Dry Run

Whether the dry run mode is enabled or not. When enabled, the plugin disables all the transactions commits,
so the state on Google Play is never changed. Dry run is disabled by default.

For example:

    dryRun = true

## Usage

#### Verify Metadata

Verify that the metadata to upload to Google Play is valid.

    ./gradlew googlePlayVerifyMetadata

###### Locales

List of supported locales on Google Play. This property is required

    locales = 'en-US,es-419'
    
###### Video Required

If the video url is required. The default value is **false**

    videoRequired = true
    
###### Promo Graphic Required

If the promo graphic is required. The default value is **true**

    promoGraphicRequired = false

###### Tv Banner Required

If the tv banner is required. The default value is **false**

    tvBannerRequired = true
    
###### Phone Screenshots Required

If the phone screenshots are required. The default value is **true**

    phoneScreenshotsRequired = false
    
###### 7 inches Screenshots Required

If the 7 inches screenshots are required. The default value is **false**

    sevenInchScreenshotsRequired = true
    
###### 10 inches Screenshots Required

If the 10 inches screenshots are required. The default value is **false**

    tenInchScreenshotsRequired = true
    
###### Tv Screenshots Required

If the tv screenshots are required. The default value is **false**

    tvScreenshotsRequired = true
    
###### Wear Screenshots Required

If the wear screenshots are required. The default value is **false**

    wearScreenshotsRequired = true

#### Publish Metadata

Publish metadata (feature/promo graphics, High resolution icon, screenshots, title, short description, full description and video url) on Google Play. The listings are published for each locales defined on the **LOCALES** property. If some asset is not available for any locale, the resources inside the **default** directory will be used

    ./gradlew googlePlayPublishMetadata
    
Create the following directories:

    {METADATA_PATH}/googleplay/{LOCALE_1}/
    {METADATA_PATH}/googleplay/{LOCALE_2}/
    {METADATA_PATH}/googleplay/default/
    
 
The **{metadataPath}/googleplay/default/** directory is optional. It is only used when a asset is not found on it's locale directory. 
You can use it to host your shared assets between locales and avoid to duplicate it.

Add your assets to each locale or default directory:
    
|Asset                |Required|Location                                              |
| ------------------- | ------ | -----------------------------------------------------|
|Title                |true    |title.txt                                             |
|Short Description    |true    |short_description.txt                                 |
|Full Description     |true    |full_description.txt                                  |
|Video                |false   |video.txt                                             |
|Feature Graphic      |true    |images/feature_graphic.png                            |
|High Resolution Icon |true    |images/high_resolution_icon.png                       |
|Tv Banner            |false   |images/tv_banner.png                                  |
|Promo Graphic        |true    |images/promo_graphic.png                              |
|Phone Screenshots    |true    |images/phone_screenshots/screenshot[1 ... 8].png      |
|7-inch Screenshots   |false   |images/seven_inch_screenshots/screenshot[1 ... 8].png |
|10-inch Screenshots  |false   |images/ten_inch_screenshots/screenshot[1 ... 8].png   |
|Tv Screenshots       |false   |images/tv_screenshots/screenshot[1 ... 8].png         |
|Wear Screenshots     |false   |images/wear_screenshots/screenshot[1 ... 8].png       |

###### Locales

List of supported locales on Google Play. This property is required

    locales = 'en-US,es-419'

###### Metadata path

The path where all the listings directories will be located. The default value is the path of the gradle project where the plugin is applied

    metadataPath = /path/to/the/metadata
    
#### List APKs

List all the historical APKs uploaded.

    ./gradlew googlePlayListAPKs

#### List Bundles

List all the historical Bundles uploaded.

    ./gradlew googlePlayListBundles

#### Publish Bundle

Upload new Bundle for your app and assign it to a release track.

    ./gradlew googlePlayPublishBundle

###### Locales

List of supported locales on Google Play. This property is required if you need to upload release notes

    locales = 'en-US,es-419'

###### Release Name

The name to identify release in the Play Console only, such as an internal code name or build version. Default value: the version name

    releaseName = 'My release'

###### Draft

Whether the release should be created on draft mode. Default value: false

    draft = true
    
###### Bundle path/dir

The path to the Bundle to publish.
The default value for the Bundle dir is the '/build/outputs/bundle/release' directory of the gradle project where the plugin is applied.

    bundlePath = /path/to/bundle/file.aab
    bundleDir = /path/to/bundle/

###### Track

The release track that you're assigning APKs to. Acceptable values are: **internal**, **alpha**, **beta** or **production**

    track = production
    
###### User Percentage

Percentage of users who are eligible to receive the release.
Only used if **track** is **production**. 0 < percentage <= 100. Default value: the previous rollout user percentage (if any) or 100%
    
    userPercentage = 10

###### Metadata path

The path where the release notes will be located. The default value is the path of the gradle project where the plugin is applied

    metadataPath = /path/to/the/metadata

###### Release Notes

Create files with the release notes. For example:

    // Release notes for LOCALE_1 and VERSION_CODE_1
    {metadataPath}/googleplay/{LOCALE_1}/release_notes/{VERSION_CODE_1}.txt

    // Release notes for LOCALE_2 and VERSION_CODE_1
    {metadataPath}/googleplay/{LOCALE_2}/release_notes/{VERSION_CODE_1}.txt

    // Release notes for LOCALE_2 and any version code
    {metadataPath}/googleplay/{LOCALE_2}/release_notes/default_release_notes.txt

    // Release notes for any locale and VERSION_CODE_2
    {metadataPath}/googleplay/default/release_notes/{VERSION_CODE_2}.txt

    // Release notes for any locale and version code
    {metadataPath}/googleplay/default/release_notes/default_release_notes.txt

###### Fail on APK upgrade version conflict

Whether the task should fail if the uploaded APK specifies a version code that has already been used. Default value: **true**
    
    failOnApkUpgradeVersionConflict = false

###### Deobfuscation File Upload

Whether the obfuscation file should be uploaded or not. Default value: **false**

    deobfuscationFileUploadEnabled = true

###### Deobfuscation File Path

The path to the deobfuscation file. Default value: '/build/outputs/mapping/release/mapping.txt'

    deobfuscationFilePath = /path/to/deobfuscation/file
    
#### Upload Bundle to Internal App Sharing

Upload new Bundle to Internal App Sharing

    ./gradlew googlePlayUploadBundleToInternalAppSharing    
    
###### Bundle path/dir

The path to the Bundle to upload.
The default value for the Bundle dir is the '/build/outputs/bundle/release' directory of the gradle project where the plugin is applied.

    bundlePath = /path/to/bundle/file.aab
    // bundleDir = /path/to/bundle/
    
#### Promote from Internal to Alpha

Promote a current internal to alpha

    ./gradlew googlePlayPromoteFromInternalToAlpha

#### Promote from Internal to Beta

Promote a current internal to beta

    ./gradlew googlePlayPromoteFromInternalToBeta

#### Promote from Internal to Production

Promote a current internal to production

    ./gradlew googlePlayPromoteFromInternalToProduction

###### User Percentage

Percentage of users who are eligible to receive the release. 0 < percentage <= 100. Default value: the previous rollout user percentage (if any) or 100%

    userPercentage = 20

#### Promote from Alpha to Beta

Promote a current alpha to beta

    ./gradlew googlePlayPromoteFromAlphaToBeta

###### Release Name

Set a release name if you have more than one alpha track and you need to promote just one

    releaseName = 'My release'
    
#### Promote from Alpha to Production

Promote a current alpha to production

    ./gradlew googlePlayPromoteFromAlphaToProduction

###### User Percentage

Percentage of users who are eligible to receive the release. 0 < percentage <= 100. Default value: the previous rollout user percentage (if any) or 100%

    userPercentage = 20

###### Release Name

Set a release name if you have more than one alpha track and you need to promote just one

    releaseName = 'My release'

#### Promote from Beta to Production

Promote a current beta to production

    ./gradlew googlePlayPromoteFromBetaToProduction

###### User Percentage

Percentage of users who are eligible to receive the release. 0 < percentage <= 100. Default value: the previous rollout user percentage (if any) or 100%

    userPercentage = 20
    
#### Increase Staged Rollout

Increase the fraction of users who should get the current staged rollout

    ./gradlew googlePlayIncreaseStagedRollout

###### User Percentage

Percentage of users who are eligible to receive the release. 0 < percentage <= 100
    
    userPercentage = 20

#### Halt Staged Rollout

Halt the current staged rollout

    ./gradlew googlePlayHaltStagedRollout

#### Resume Staged Rollout

Resume the current staged rollout

    ./gradlew googlePlayResumeStagedRollout

#### Complete Staged Rollout

Rollout the release to 100% of users

    ./gradlew googlePlayCompleteStagedRollout

#### List Tracks

List all the assigned APKs for each release track

    ./gradlew googlePlayListTracks
