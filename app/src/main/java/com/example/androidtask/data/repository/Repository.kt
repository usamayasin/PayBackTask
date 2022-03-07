package com.example.androidtask.data.repository

import com.example.androidtask.data.model.ImageList
import com.example.androidtask.data.remote.DataState
import kotlinx.coroutines.flow.Flow

/**
 * Repository is an interface data layer to handle communication with any data source such as Server or local database.
 * @see [RepositoryImpl] for implementation of this class to utilize APIService.
 */
interface Repository {

    suspend fun getImages(page: Int, keyword: String): Flow<DataState<ImageList>>
}
