package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
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
            val asteroids =
                parseAsteroidsJsonResult(JSONObject(Network.asteroidService.getAsteroids()))

            database.asteroidDao.insertAll(*asteroids.asDatabaseModel())
        }
    }

    suspend fun refreshPictureOfDay() {
        withContext(Dispatchers.IO) {
            val picture = Network.asteroidService.getPictureOfDay()
            database.pictureOfDayDao.insertPictureOfDay(picture.asDatabaseModel())
        }
    }
}