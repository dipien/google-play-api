package com.dipien.google.play.api

enum class TrackReleaseStatus(val key: String) {

    COMPLETED("completed"),
    DRAFT("draft"),
    HALTED("halted"),
    IN_PROGRESS("inProgress");

    companion object {
        fun findByKey(key: String): TrackReleaseStatus? {
            for (each in values()) {
                if (each.key == key) {
                    return each
                }
            }
            return null
        }
    }
}
