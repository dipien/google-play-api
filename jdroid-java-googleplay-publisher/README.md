# Java Google Play Publisher
Java Library to publish android APKs/bundles and listings on Google Play

## Setup

Add the following configuration to your `build.gradle`, replacing X.Y.Z by the [latest version](https://github.com/maxirosson/jdroid-googleplay-publisher/releases/latest)

    repositories {
        jcenter()
    }
    
    dependencies {
       implementation 'com.jdroidtools:jdroid-java-googleplay-publisher:X.Y.Z'
    }
    
Create an **AppContext** instance:

    AppContext appContext = new AppContext();
    
    // The application id of your app. This property is required
    appContext.setApplicationId("com.sample");
    
    // Path to the private key json file. This property is required.
    appContext.setPrivateKeyJsonFilePath("/credentials/googleplay/key.json");

    // Whether the dry run mode is enabled or not. When enabled, the library disables all the transactions commits,
    // so the state on Google Play is never changed. Dry run is disabled by default.
    appContext.setDryRun(true);

## Usage

#### Verify Metadata

Verify that the metadata to upload to Google Play is valid.

    // List of supported locales on Google Play. This property is required
    List<String> locales = new ArrayList<String>();
    locales.add("en-US");
    locales.add("es-419");
    appContext.setLocales(locales);

    // If the video url is required. The default value is false
    appContext.setVideoRequired(true);
    
    // If the promo graphic is required. The default value is true
    appContext.setPromoGraphicRequired(false);
    
    // If the tv banner is required. The default value is false
    appContext.setTvBannerRequired(true);
    
    // If the phone screenshots are required. The default value is true
    appContext.setPhoneScreenshotsRequired(false);
    
    // If the 7 inches screenshots are required. The default value is false
    appContext.setSevenInchScreenshotsRequired(true);
    
    // If the 10 inches screenshots are required. The default value is false
    appContext.setTenInchScreenshotsRequired(true);
    
    // If the tv screenshots are required. The default value is false
    appContext.setTvScreenshotsRequired(true);
    
    // If the wear screenshots are required. The default value is false
    appContext.setWearScreenshotsRequired(true);
    
    GooglePlayPublisher.verifyMetadata(new App(appContext));
    
#### Publish Metadata

Publish metadata (feature/promo graphics, High resolution icon, screenshots, title, short description, full description and video url) on Google Play. The listings are published for each locales defined on the **LOCALES** property. If some asset is not available for any locale, the resources inside the **default** directory will be used

    // List of supported locales on Google Play. This property is required
    List<String> locales = new ArrayList<String>();
    locales.add("en-US");
    locales.add("es-419");
    appContext.setLocales(locales);

    // The path where all the listings directories will be located. This property is required
    appContext.setMetadataPath("/path/to/the/metadata");
    
    GooglePlayPublisher.publishMetadata(new App(appContext));
    
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

    GooglePlayPublisher.getApks(new App(appContext));

#### Get Bundles

Get all the historical bundles uploaded.

    GooglePlayPublisher.getBundles(new App(appContext));

#### Publish APK / Bundle

Upload new APK for your app and assign it to a release track.

    // List of supported locales on Google Play. This property is required if you need to upload release notes
    List<String> locales = new ArrayList<String>();
    locales.add("en-US");
    locales.add("es-419");
    appContext.setLocales(locales);

    // The name to identify release in the Play Console only, such as an internal code name or build version. Default value: the version name
    appContext.setReleaseName("My release");

    // Whether the release should be created on draft mode. Default value: false
    appContext.setDraft(true);

    // The path to the APK / Bundle to publish
    appContext.setApkPath("/path/to/apk/file.apk");
    // appContext.setApkDir("/path/to/apk/");
    // appContext.setBundlePath("/path/to/bundle/file.aab");
    // appContext.setBundleDir("/path/to/bundle/");

    // The release track that you're assigning APKs to. 
    // Acceptable values are: TrackType.INTERNAL, TrackType.ALPHA, TrackType.BETA or TrackType.PRODUCTION
    appContext.setTrackType(TrackType.PRODUCTION);
    
    // Percentage of users who are eligible to receive the release.
    // Only used if trackType is TrackType.PRODUCTION. 0 < percentage <= 100. Default value: the previous rollout user percentage (if any) or 100%
    appContext.setUserPercentage(20);
    
    // Whether the task should fail if the uploaded APK/bundle specifies a version code that has already been used. Default value: true
    appContext.setFailOnApkUpgradeVersionConflict(false);

    // Whether the obfuscation file should be uploaded or not. Default value: false
    appContext.setDeobfuscationFileUploadEnabled(true);

    // The path to the deobfuscation file. If setDeobfuscationFileUploadEnabled(true), this property is required
    appContext.setDeobfuscationFilePath("/path/to/deobfuscation/file");

    // The path where the release notes will be located. This property is optional
    appContext.setMetadataPath("/path/to/the/metadata");

    GooglePlayPublisher.publishApk(new App(appContext));
    // or
    GooglePlayPublisher.publishBundle(new App(appContext));

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

    // Release notes for any locale and version code
    {METADATA_PATH}/googleplay/default/release_notes/default_release_notes.txt

#### Promote from Internal to Alpha

Promote a current internal to alpha

    GooglePlayPublisher.promoteFromInternalToAlpha(new App(appContext));
    
#### Promote from Internal to Beta

Promote a current internal to beta

    GooglePlayPublisher.promoteFromInternalToBeta(new App(appContext));
    
#### Promote from Internal to Production

Promote a current internal to production

    // Percentage of users who are eligible to receive the release. 0 < percentage <= 100. Default value: the previous rollout user percentage (if any) or 100%
    appContext.setUserPercentage(20);

    GooglePlayPublisher.promoteFromInternalToProduction(new App(appContext));
    
#### Promote from Alpha to Beta

Promote a current alpha to beta

    // Set a release name if you have more than one alpha track and you need to promote just one
    appContext.setReleaseName("My release");

    GooglePlayPublisher.promoteFromAlphaToBeta(new App(appContext));
    
#### Promote from Alpha to Production

Promote a current alpha to production

    // Set a release name if you have more than one alpha track and you need to promote just one
    appContext.setReleaseName("My release");

    // Percentage of users who are eligible to receive the release. 0 < percentage <= 100. Default value: the previous rollout user percentage (if any) or 100%
    appContext.setUserPercentage(20);

    GooglePlayPublisher.promoteFromAlphaToProduction(new App(appContext));

#### Promote from Beta to Production

Promote a current beta to production

    // Percentage of users who are eligible to receive the release. 0 < percentage <= 100. Default value: the previous rollout user percentage (if any) or 100%
    appContext.setUserPercentage(20);

    GooglePlayPublisher.promoteFromBetaToProduction(new App(appContext));
    
#### Increase Staged Rollout

Increase the percentage of users who should get the current staged rollout

    // Percentage of users who are eligible to receive the release. 0 < percentage <= 100. Default value: 0.005 (0.5%)
    appContext.setUserPercentage(20);

    GooglePlayPublisher.increaseStagedRollout(new App(appContext));

#### Halt Staged Rollout

Halt the current staged rollout

    GooglePlayPublisher.haltStagedRollout(new App(appContext));

#### Resume Staged Rollout

Resume the current staged rollout

    GooglePlayPublisher.resumeStagedRollout(new App(appContext));

#### Complete Staged Rollout

Rollout the release to 100% of users

    GooglePlayPublisher.completeStagedRollout(new App(appContext));

#### Get Tracks

Get all the assigned APKs for each release track

    GooglePlayPublisher.getTracks(new App(appContext));

## Donations
Help us to continue with this project:

[![Donate](https://www.paypalobjects.com/en_US/i/btn/btn_donate_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=2UEBTRTSCYA9L)
