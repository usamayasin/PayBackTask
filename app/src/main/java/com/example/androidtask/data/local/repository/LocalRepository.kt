package com.example.androidtask.data.local.repository

import com.example.androidtask.data.local.dao.ImagesDao
import com.example.androidtask.data.local.entity.ImagesEntity
import com.example.androidtask.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class LocalRepository @Inject constructor(var imagesDao: ImagesDao) {

    var keyword: MutableStateFlow<String> = MutableStateFlow(Constants.DEFAULT_KEYWORD_FRUIT)

    fun insertImages(imageEntities: List<ImagesEntity>, word:String) {

        imagesDao.insertImages(imageEntities)
        keyword.value= word
    }

   var getImages= keyword.flatMapLatest {
        imagesDao.getAllImages(it)
    }
}