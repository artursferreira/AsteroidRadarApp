package com.udacity.asteroidradar.network

import com.squareup.moshi.Json
import com.udacity.asteroidradar.database.PictureOfDayLocal
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.domain.asDatabaseModel

data class NetworkPictureOfDay(
    @Json(name = "media_type") val mediaType: String, val title: String,
    val url: String, val id: Int
)

fun NetworkPictureOfDay.asDatabaseModel(): PictureOfDayLocal {
    return PictureOfDayLocal(
        id = this.id,
        mediaType = this.mediaType,
        title = this.title,
        url = this.url
    )
}