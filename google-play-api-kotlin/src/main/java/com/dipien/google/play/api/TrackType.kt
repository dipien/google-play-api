package com.dipien.google.play.api

enum class TrackType(val key: String) {

    INTERNAL("internal"),
    ALPHA("alpha"),
    BETA("beta"),
    PRODUCTION("production");

    companion object {
        fun findByKey(key: String?, defaulTrackType: TrackType? = null): TrackType? {
            for (each in values()) {
                if (each.key == key) {
                    return each
                }
            }
            return defaulTrackType
        }
    }
}
