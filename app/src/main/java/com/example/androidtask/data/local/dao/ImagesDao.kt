package com.example.androidtask.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidtask.data.local.entity.ImagesEntity
import com.example.androidtask.utils.Constants.TABLE_IMAGES
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ImagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertImages(imagesList: List<ImagesEntity>)

    @Query("Select * FROM $TABLE_IMAGES")
    abstract fun getAllImages(): Flow<List<ImagesEntity>>

}