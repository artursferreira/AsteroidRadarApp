package com.udacity.asteroidradar.domain

import com.squareup.moshi.Json
import com.udacity.asteroidradar.database.PictureOfDayLocal

data class PictureOfDay(
    val mediaType: String, val title: String,
    val url: String
)

fun PictureOfDay.asDatabaseModel(): PictureOfDayLocal {
    return PictureOfDayLocal(
        mediaType = this.mediaType,
        title = this.title,
        url = this.url
    )
}