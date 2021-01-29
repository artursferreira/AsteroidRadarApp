package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Constants.MEDIA_TYPE_IMAGE
import com.udacity.asteroidradar.api.getYesterday
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.domain.asDatabaseModel
import com.udacity.asteroidradar.network.Network
import com.udacity.asteroidradar.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.Exception


class AsteroidRepository(private val database: AsteroidDatabase) {

    val pictureOfDay: LiveData<PictureOfDay> =
        Transformations.map(database.pictureOfDayDao.getPictureOfDay()) {
            it?.asDomainModel()
        }

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()) {
            it.asDomainModel()
        }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                val asteroids =
                    parseAsteroidsJsonResult(JSONObject(Network.asteroidService.getAsteroids()))

                database.asteroidDao.insertAll(*asteroids.asDatabaseModel())
            } catch (e: Exception) {
                Log.d("exception", e.toString())
            }
        }
    }

    suspend fun refreshPictureOfDay() {
        withContext(Dispatchers.IO) {
            try {
                val picture = Network.asteroidService.getPictureOfDay()

                if (picture.mediaType == MEDIA_TYPE_IMAGE)
                    database.pictureOfDayDao.insertPictureOfDay(picture.asDatabaseModel())
                else {
                    //Ignore videos
                }
            } catch (e: Exception) {
                Log.d("exception", e.toString())
            }
        }
    }

    suspend fun deleteOldAsteroids() {
        withContext(Dispatchers.IO) {
            database.asteroidDao.delete(getYesterday())
        }
    }
}