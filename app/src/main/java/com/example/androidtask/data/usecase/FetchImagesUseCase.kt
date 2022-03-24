package com.example.androidtask.data.usecase

import com.example.androidtask.data.local.repository.AbstractLocalRepo
import com.example.androidtask.data.local.repository.LocalRepository
import com.example.androidtask.data.model.toDataBaseModel
import com.example.androidtask.data.remote.DataState
import com.example.androidtask.data.repository.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class FetchImagesUseCase @Inject constructor(
    private val repository: Repository,
    private val localRepo: AbstractLocalRepo
) {

    @ExperimentalCoroutinesApi
    suspend operator fun invoke(
        page: Int,
        keyword: String,
        onResponseComplete: (error: DataState.CustomMessages) -> Unit
    ) {
        repository.getImages(page = page, keyword = keyword).collect { response ->
            when (response) {
                is DataState.Success -> {
                    val imagesList =
                        response.data?.toDataBaseModel(keyword = keyword) ?: emptyList()
                    localRepo.insertImages(imagesList,keyword)
                }
                is DataState.Error -> {
                    onResponseComplete(response.error)
                    localRepo.insertImages(emptyList(),keyword)
                }
                else -> {
                    onResponseComplete(response.error)
                }
            }
        }
    }
}

