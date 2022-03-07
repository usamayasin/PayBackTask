package com.example.androidtask.data.repository

import androidx.annotation.WorkerThread
import com.example.androidtask.data.remote.*
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * This is an implementation of [Repository] to handle communication with [ApiService] server.
 */

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : Repository {

    @WorkerThread
    override suspend fun getImages(page: Int, keyword: String) = flow {
        apiService.getImages(page = page, keyword =  keyword).apply {
            this.onSuccessSuspend ({
                emit(this)
            },{
                emit(this)
            }).onErrorSuspend {
                emit(error())
            }.onExceptionSuspend {
                emit(error())
            }
        }
    }
}
