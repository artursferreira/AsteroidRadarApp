package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.udacity.asteroidradar.domain.PictureOfDay

@Entity
data class PictureOfDayLocal(
    val mediaType: String, val title: String,
    @PrimaryKey val url: String
)


fun PictureOfDayLocal.asDomainModel(): PictureOfDay {
    return PictureOfDay(mediaType = this.mediaType, title = this.title, url = this.url)
}