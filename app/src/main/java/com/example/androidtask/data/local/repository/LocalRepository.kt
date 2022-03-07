package com.example.androidtask.data.local.repository

import com.example.androidtask.data.local.dao.ImagesDao
import com.example.androidtask.data.local.entity.ImagesEntity
import com.example.androidtask.utils.Constants
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalRepository @Inject constructor(var imagesDao: ImagesDao) {

    var keyword = Constants.DEFAULT_KEYWORD_FRUIT

    fun insertImages(imageEntities: List<ImagesEntity>, word:String) {
        keyword = word
        imagesDao.insertImages(imageEntities)
    }

    var getImages = imagesDao.getAllImages()

//        .map { list ->
//            list.filter {
//                it.keyword == keyword
//            }
//        }
}