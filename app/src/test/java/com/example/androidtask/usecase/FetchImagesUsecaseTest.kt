package com.example.androidtask.usecase

import android.util.Log
import com.example.androidtask.MockTestUtil
import com.example.androidtask.data.local.entity.ImagesEntity
import com.example.androidtask.data.local.entity.toDomainModel
import com.example.androidtask.data.local.repository.AbstractLocalRepo
import com.example.androidtask.data.local.repository.LocalRepository
import com.example.androidtask.data.model.Image
import com.example.androidtask.data.model.ImageList
import com.example.androidtask.data.remote.DataState
import com.example.androidtask.data.repository.Repository
import com.example.androidtask.data.usecase.FetchImagesUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FetchImagesUsecaseTest {

    @MockK
    private lateinit var repository: Repository

    @RelaxedMockK
    private lateinit var localRepo: AbstractLocalRepo

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test invoking FetchimageInfoimagecase gives error`() = runBlocking {
        // Given
        val fetchImagesInfoUseCase = FetchImagesUseCase(repository, localRepo)
        val givenImages = MockTestUtil.getMockImages()

        // When
        coEvery { repository.getImages(page = any() , keyword = any()) }
            .returns(flowOf(DataState.Error<ImageList>(DataState.CustomMessages.BadRequest)))

        // Invoke
         fetchImagesInfoUseCase(1,"fake"){
            // Then
            Assert.assertEquals(givenImages.error, it)
        }





    }
/*
    @Test
    fun `test invoking FetchimageInfoimagecase gives imageList`() = runBlocking {
        // Given
        val fetchImagesInfoUseCase = FetchImagesUseCase(repository, localRepo)
        val givenImages = MockTestUtil.getMockImages(1)

        // When
        coEvery { repository.getImages(page = any() , keyword = any()) }
            .returns(flowOf(DataState.Error<ImageList>(DataState.CustomMessages.BadRequest)))

        fetchImagesInfoUseCase.invoke(9,"rice"){

        }

        localRepo.getImages.collectLatest {
          Assert.assertEquals(givenImages.hits, it)
        }

    }*/





}