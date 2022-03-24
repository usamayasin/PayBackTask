package com.example.androidtask.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidtask.data.local.entity.ImagesEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ImagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertImages(imagesList: List<ImagesEntity>)

    @Query("Select * FROM images Where keyword LIKE :word ")
    abstract fun getAllImages(word:String): Flow<List<ImagesEntity>>

}