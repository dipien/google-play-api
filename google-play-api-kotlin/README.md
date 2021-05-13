[![Dipien](https://raw.githubusercontent.com/dipien/dipien-component-builder/master/.github/dipien_logo.png)](http://www.dipien.com)

# Kotlin Client fot Google Play API
Koltin Library to publish android APKs/bundles and listings on Google Play

## Setup

Add the following configuration to your `build.gradle`, replacing X.Y.Z by the [latest version](https://github.com/dipien/google-play-api/releases/latest)

    repositories {
        mavenCentral()
    }
    
    dependencies {
       implementation 'com.dipien:google-play-api-kotlin:X.Y.Z'
    }
    
Create an **AppContext** instance:

    val app = App()
    
    // The application id of your app. This property is required
    app.setApplicationId("com.sample")
    
    // Path to the private key json file. This property is required.
    app.setPrivateKeyJsonFilePath("/credentials/googleplay/key.json")

    // The connect timeout (in milliseconds) when executing requests against the Google API. The default value is 100 seconds
    app.setConnectTimeout(200000)

    // The read timeout (in milliseconds) when executing requests against the Google API. The default value is 100 seconds
    app.setReadTimeout(200000)

    // Whether the dry run mode is enabled or not. When enabled, the library disables all the transactions commits,
    // so the state on Google Play is never changed. Dry run is disabled by default.
    app.setDryRun(true)

## Usage

#### Verify Metadata

Verify that the metadata to upload to Google Play is valid.

    // List of supported locales on Google Play. This property is required
    val locales = mutableList()
    locales.add("en-US")
    locales.add("es-419")
    app.setLocales(locales)

    // If the video url is required. The default value is false
    app.setVideoRequired(true)
    
    // If the promo graphic is required. The default value is true
    app.setPromoGraphicRequired(false)
    
    // If the tv banner is required. The default value is false
    app.setTvBannerRequired(true)
    
    // If the phone screenshots are required. The default value is true
    app.setPhoneScreenshotsRequired(false)
    
    // If the 7 inches screenshots are required. The default value is false
    app.setSevenInchScreenshotsRequired(true)
    
    // If the 10 inches screenshots are required. The default value is false
    app.setTenInchScreenshotsRequired(true)
    
    // If the tv screenshots are required. The default value is false
    app.setTvScreenshotsRequired(true)
    
    // If the wear screenshots are required. The default value is false
    app.setWearScreenshotsRequired(true)
    
    PublishingService().verifyMetadata(app)
    
#### Publish Metadata

Publish metadata (feature/promo graphics, High resolution icon, screenshots, title, short description, full description and video url) on Google Play. The listings are published for each locales defined on the **LOCALES** property. If some asset is not available for any locale, the resources inside the **default** directory will be used

    // List of supported locales on Google Play. This property is required
    val locales = mutableList()
    locales.add("en-US")
    locales.add("es-419")
    app.setLocales(locales)

    // The path where all the listings directories will be located. This property is required
    app.setMetadataPath("/path/to/the/metadata")
    
    PublishingService().publishMetadata(app)
    
Create the following directories:

    {METADATA_PATH}/googleplay/{LOCALE_1}/
    {METADATA_PATH}/googleplay/{LOCALE_2}/
    {METADATA_PATH}/googleplay/default/
    
 
The **{METADATA_PATH}/googleplay/default/** directory is optional. It is only used when a asset is not found on it's locale directory. 
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


#### Get APKs

Get all the historical APKs uploaded.

    PublishingService().getApks(app)

#### Get Bundles

Get all the historical bundles uploaded.

    PublishingService().getBundles(app)

#### Publish Bundle

Upload new Bundle for your app and assign it to a release track.

    // List of supported locales on Google Play. This property is required if you need to upload release notes
    val locales = mutableList()
    locales.add("en-US")
    locales.add("es-419")
    app.setLocales(locales)

    // The name to identify release in the Play Console only, such as an internal code name or build version. Default value: the version name
    app.setReleaseName("My release")

    // Whether the release should be created on draft mode. Default value: false
    app.setDraft(true)

    // The path to the Bundle to publish
    app.setBundlePath("/path/to/bundle/file.aab")
    app.setBundleDir("/path/to/bundle/")

    // The release track that you're assigning APKs to. 
    // Acceptable values are: TrackType.INTERNAL, TrackType.ALPHA, TrackType.BETA or TrackType.PRODUCTION
    app.setTrackType(TrackType.PRODUCTION)
    
    // Percentage of users who are eligible to receive the release.
    // Only used if trackType is TrackType.PRODUCTION. 0 < percentage <= 100. Default value: the previous rollout user percentage (if any) or 100%
    app.setUserPercentage(20)
    
    // Whether the task should fail if the uploaded APK/bundle specifies a version code that has already been used. Default value: true
    app.setFailOnApkUpgradeVersionConflict(false)

    // Whether the obfuscation file should be uploaded or not. Default value: false
    app.setDeobfuscationFileUploadEnabled(true)

    // The path to the deobfuscation file. If setDeobfuscationFileUploadEnabled(true), this property is required
    app.setDeobfuscationFilePath("/path/to/deobfuscation/file")

    // The path where the release notes will be located. This property is optional
    app.setMetadataPath("/path/to/the/metadata")

    PublishingService().publishBundle(app)

###### Release Notes

Create files with the release notes. For example:

    // Release notes for LOCALE_1 and VERSION_CODE_1
    {METADATA_PATH}/googleplay/{LOCALE_1}/release_notes/{VERSION_CODE_1}.txt

    // Release notes for LOCALE_2 and VERSION_CODE_1
    {METADATA_PATH}/googleplay/{LOCALE_2}/release_notes/{VERSION_CODE_1}.txt

    // Release notes for LOCALE_2 and any version code
    {METADATA_PATH}/googleplay/{LOCALE_2}/release_notes/default_release_notes.txt

    // Release notes for any locale and VERSION_CODE_2
    {METADATA_PATH}/googleplay/default/release_notes/{VERSION_CODE_2}.txt

    // Release notes for any locale and version code (used if there isn't any other match)
    {METADATA_PATH}/googleplay/default/release_notes/default_release_notes.txt

#### Upload Bundle to Internal App Sharing

Upload new Bundle to Internal App Sharing

    // The path to the Bundle to publish
    app.setBundlePath("/path/to/bundle/file.aab")
    // app.setBundleDir("/path/to/bundle/")

    PublishingService().uploadBundleToInternalAppSharing(app)


#### Promote from Internal to Alpha

Promote a current internal to alpha

    PublishingService().promoteFromInternalToAlpha(app)
    
#### Promote from Internal to Beta

Promote a current internal to beta

    PublishingService().promoteFromInternalToBeta(app)
    
#### Promote from Internal to Production

Promote a current internal to production

    // Percentage of users who are eligible to receive the release. 0 < percentage <= 100. Default value: the previous rollout user percentage (if any) or 100%
    app.setUserPercentage(20);

    PublishingService().promoteFromInternalToProduction(app)
    
#### Promote from Alpha to Beta

Promote a current alpha to beta

    // Set a release name if you have more than one alpha track and you need to promote just one
    app.setReleaseName("My release")

    PublishingService().promoteFromAlphaToBeta(app)
    
#### Promote from Alpha to Production

Promote a current alpha to production

    // Set a release name if you have more than one alpha track and you need to promote just one
    app.setReleaseName("My release")

    // Percentage of users who are eligible to receive the release. 0 < percentage <= 100. Default value: the previous rollout user percentage (if any) or 100%
    app.setUserPercentage(20)

    PublishingService().promoteFromAlphaToProduction(app)

#### Promote from Beta to Production

Promote a current beta to production

    // Percentage of users who are eligible to receive the release. 0 < percentage <= 100. Default value: the previous rollout user percentage (if any) or 100%
    app.setUserPercentage(20)

    PublishingService().promoteFromBetaToProduction(app);
    
#### Increase Staged Rollout

Increase the percentage of users who should get the current staged rollout

    // Percentage of users who are eligible to receive the release. 0 < percentage <= 100. Default value: 0.005 (0.5%)
    app.setUserPercentage(20)

    PublishingService().increaseStagedRollout(app)

#### Halt Staged Rollout

Halt the current staged rollout

    PublishingService().haltStagedRollout(app)

#### Resume Staged Rollout

Resume the current staged rollout

    PublishingService().resumeStagedRollout(app)

#### Complete Staged Rollout

Rollout the release to 100% of users

    PublishingService().completeStagedRollout(app)

#### Get Tracks

Get all the assigned APKs for each release track

    PublishingService().getTracks(app)

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