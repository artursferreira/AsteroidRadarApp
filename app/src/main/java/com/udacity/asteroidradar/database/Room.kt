package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*



@Database(entities = [AsteroidLocal::class, PictureOfDayLocal::class], version = 1)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
    abstract val pictureOfDayDao: PictureOfDayDao
}

private lateinit var INSTANCE: AsteroidDatabase

fun getDatabase(context: Context): AsteroidDatabase {
    synchronized(AsteroidDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AsteroidDatabase::class.java,
                "asteroids"
            ).build()
        }
    }
    return INSTANCE
}