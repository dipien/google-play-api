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

## Usage

### Publish listing task

This task publish listings (feature/promo graphics, High resolution icon, screenshots, title, short and full descriptions) on Google Play

### Task Execution

    gradle googlePlayPublishListings

## Donations
Help us to continue with this project:

[![Donate](https://www.paypalobjects.com/en_US/i/btn/btn_donate_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=2UEBTRTSCYA9L)

<a href='https://pledgie.com/campaigns/30030'><img alt='Click here to lend your support to: Jdroid and make a donation at pledgie.com !' src='https://pledgie.com/campaigns/30030.png?skin_name=chrome' border='0' ></a>
