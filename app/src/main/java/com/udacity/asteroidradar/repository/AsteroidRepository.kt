package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.network.Network
import com.udacity.asteroidradar.network.NetworkAsteroid
import com.udacity.asteroidradar.network.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//TODO add database
class AsteroidRepository {

    suspend fun getAsteroids(): Asteroid? {
        return withContext(Dispatchers.IO) {
            val response = Network.asteroidService.getAsteroids()

            if (response.isSuccessful)
                response.body()?.asDomainModel()
            else null
        }
    }
}