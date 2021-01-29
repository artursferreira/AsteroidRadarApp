package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PictureOfDayDao {

    @Query("select * from pictureofdaylocal")
    fun getPictureOfDay(): LiveData<PictureOfDayLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPictureOfDay(pictureOfDay: PictureOfDayLocal)

}