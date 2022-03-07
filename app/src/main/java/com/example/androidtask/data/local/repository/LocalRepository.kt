package com.example.androidtask.data.local.repository

import android.util.Log
import com.example.androidtask.data.local.dao.ImagesDao
import com.example.androidtask.data.local.entity.ImagesEntity
import com.example.androidtask.utils.Constants
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalRepository @Inject constructor(var imagesDao: ImagesDao) {

    var keyword: MutableStateFlow<String> = MutableStateFlow(Constants.DEFAULT_KEYWORD_FRUIT)

    fun insertImages(imageEntities: List<ImagesEntity>, word:String) {
        keyword.value= word
        imagesDao.insertImages(imageEntities)
    }

   var getImages= keyword.flatMapLatest {
        imagesDao.getAllImages(it)
    }
}