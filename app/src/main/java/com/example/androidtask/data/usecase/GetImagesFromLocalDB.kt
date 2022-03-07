package com.example.androidtask.data.usecase

import com.example.androidtask.data.local.entity.ImagesEntity
import com.example.androidtask.data.local.repository.LocalRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImagesFromLocalDB @Inject constructor(
    private val localRepo: LocalRepository
) {
    @ExperimentalCoroutinesApi
    suspend operator fun invoke(): Flow<List<ImagesEntity>> {
       return localRepo.getImages
    }
}
