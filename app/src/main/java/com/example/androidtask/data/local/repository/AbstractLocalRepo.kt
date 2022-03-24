package com.example.androidtask.data.local.repository

import com.example.androidtask.data.local.entity.ImagesEntity
import kotlinx.coroutines.flow.Flow

abstract class AbstractLocalRepo {
    abstract fun insertImages(imageEntities: List<ImagesEntity>, word: String)
    open lateinit var  getImages: Flow<List<ImagesEntity>>
}