# Java Google Play Publisher
Java Library to publish android APKs and listings on Google Play

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
    
    // List of supported locales on Google Play. This property is required
    List<String> locales = new ArrayList<String>();
    locales.add("en-US");
    locales.add("es-419");
    appContext.setLocales(locales);
    
    // Path to the directory where the private key json file is located. This property is required.
    // The private key json file path will generated as follows: ${PRIVATE_KEY_JSON_DIR}/${APPLICATION_ID}.json
    // In this sample, the private key json file should be at /credentials/googleplay/com.sample.json
    appContext.setPrivateKeyJsonFileDirectory("/credentials/googleplay");
    
## Usage

#### Verify Metadata

Verify that the metadata to upload to Google Play is valid.

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


#### Get APKs

Get all the historical APKs uploaded.

    GooglePlayPublisher.getApks(new App(appContext));
    
#### Publish APK

Upload new APK for your app and assign it to a release track.

    // The path to the APK to publish
    appContext.setApkPath("/path/to/apk/file.apk");
    
    // The release track that you're assigning APKs to. 
    // Acceptable values are: TrackType.ALPHA, TrackType.BETA, TrackType.ROLLOUT or TrackType.PRODUCTION
    appContext.setTrackType(TrackType.ALPHA);
    
    // Portion of the users who should get the staged rollout version of the APK. 
    // The maximum rollout fraction is 0.5 (50% of users). Only used if trackType is TrackType.ROLLOUT. Default value: 0.005 (0.5%)  
    appContext.setUserFraction(0.1);
    
    // Whether the task should fail if the uploaded APK specifies a version code that has already been used. Default value: true
    appContext.setFailOnApkUpgradeVersionConflict(false);

    GooglePlayPublisher.publishApk(new App(appContext));
    
###### Release Notes

Create the following files with the release notes:

    {METADATA_PATH}/googleplay/{LOCALE_1}/changelogs/{VERSION_CODE}.txt
    {METADATA_PATH}/googleplay/{LOCALE_2}/changelogs/{VERSION_CODE}.txt
    
#### Promote from Alpha to Beta

Promote a current alpha to beta

    GooglePlayPublisher.promoteFromAlphaToBeta(new App(appContext));
        
#### Promote from Alpha to Rollout

Promote a current alpha to staged rollout

    // Portion of the users who should get the staged rollout version of the APK. 
    // The maximum rollout fraction is 0.5 (50% of users). Default value: 0.005 (0.5%)
    appContext.setUserFraction(0.2);
    
    GooglePlayPublisher.promoteFromAlphaToRollout(new App(appContext));
    
#### Promote from Alpha to Beta

Promote a current alpha to beta

    GooglePlayPublisher.promoteFromAlphaToBeta(new App(appContext));
        
#### Promote from Beta to Rollout

Promote a current beta to rollout

    // Portion of the users who should get the staged rollout version of the APK. 
    // The maximum rollout fraction is 0.5 (50% of users). Default value: 0.005 (0.5%)
    appContext.setUserFraction(0.2);
    
    GooglePlayPublisher.promoteFromBetaToRollout(new App(appContext));
    
#### Increase Staged Rollout

Increase the fraction of users who should get the current staged rollout APK

    // Portion of the users who should get the staged rollout version of the APK. 
    // The maximum rollout fraction is 0.5 (50% of users).
    appContext.setUserFraction(0.2);
    
    GooglePlayPublisher.increaseStagedRollout(new App(appContext));
    
#### Promote from Rollout to Production

Promote a current staged rollout to production

    GooglePlayPublisher.promoteFromRolloutToProduction(new App(appContext));

#### Clean Track

Remove all the APKs assigned to a track

    // The release track that you're cleaning. Acceptable values are: TrackType.ALPHA, TrackType.BETA
    appContext.setTrackType(TrackType.ALPHA)
    
    GooglePlayPublisher.cleanTrack(new App(appContext));

#### Get Tracks

Get all the assigned APKs for each release track

    GooglePlayPublisher.getTracks(new App(appContext));

## Donations
Help us to continue with this project:

[![Donate](https://www.paypalobjects.com/en_US/i/btn/btn_donate_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=2UEBTRTSCYA9L)

<a href='https://pledgie.com/campaigns/30030'><img alt='Click here to lend your support to: Jdroid and make a donation at pledgie.com !' src='https://pledgie.com/campaigns/30030.png?skin_name=chrome' border='0' ></a>
