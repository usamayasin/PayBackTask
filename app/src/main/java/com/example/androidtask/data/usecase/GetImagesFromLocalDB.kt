package com.example.androidtask.data.usecase

import com.example.androidtask.data.local.repository.LocalRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class GetImagesFromLocalDB @Inject constructor(
    private val localRepo: LocalRepository
) {
    @ExperimentalCoroutinesApi
    suspend operator fun invoke()= localRepo.getImages
}
