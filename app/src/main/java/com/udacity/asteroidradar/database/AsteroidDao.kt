package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AsteroidDao {
    @Query("SELECT * FROM asteroidlocal ORDER BY closeApproachDate")
    fun getAsteroids(): LiveData<List<AsteroidLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: AsteroidLocal)

    @Query("DELETE FROM asteroidlocal WHERE closeApproachDate = :closeApproachDate")
    fun delete(closeApproachDate: String)

}