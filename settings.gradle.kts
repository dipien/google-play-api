plugins {
    id("com.gradle.enterprise").version("3.3.4")
}

include(":google-play-api-plugin")
include(":google-play-api-kotlin")

apply(from = java.io.File(settingsDir, "buildCacheSettings.gradle"))
