# Jdroid Google Play Publisher Plugin
Gradle Plugin to publish android APKs and listings on Google Play

## Setup

Add the following configuration to your `build.gradle`:

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

### List APKs task

List all the historical APKs uploaded

### Task Execution

    ./gradlew :googleplay:googlePlayListAPKs
    
### Publish listing task

Publish listings (feature/promo graphics, High resolution icon, screenshots, title, short and full descriptions) on Google Play

### Task Execution

    ./gradlew :googleplay:googlePlayPublishListings

## Donations
Help us to continue with this project:

[![Donate](https://www.paypalobjects.com/en_US/i/btn/btn_donate_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=2UEBTRTSCYA9L)

<a href='https://pledgie.com/campaigns/30030'><img alt='Click here to lend your support to: Jdroid and make a donation at pledgie.com !' src='https://pledgie.com/campaigns/30030.png?skin_name=chrome' border='0' ></a>
