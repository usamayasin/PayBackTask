package com.example.androidtask.usecase

import com.example.androidtask.MockTestUtil
import com.example.androidtask.data.local.repository.LocalRepository
import com.example.androidtask.data.remote.DataState
import com.example.androidtask.data.repository.Repository
import com.example.androidtask.data.usecase.FetchImagesUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FetchImagesUsecaseTest {

    @MockK
    private lateinit var repository: Repository

    @MockK
    private lateinit var localRepo: LocalRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test invoking FetchimageInfoimagecase gives list of image`() = runBlocking {
        // Given
        val fetchImagesInfoUseCase = FetchImagesUseCase(repository, localRepo)
        val givenImages = MockTestUtil.getMockImages(1)

        // When
        coEvery { repository.getImages(page = any() , keyword = any()) }
            .returns(flowOf(DataState.Success(givenImages)))

        // Invoke
        val imagesListFlow = fetchImagesInfoUseCase(1,"fake")

        // Then
        MatcherAssert.assertThat(imagesListFlow, CoreMatchers.notNullValue())

        val imageListDataState = imagesListFlow.first()
        MatcherAssert.assertThat(imageListDataState, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(
            imageListDataState,
            CoreMatchers.instanceOf(DataState.Success::class.java)
        )

        val imagesList = (imageListDataState as DataState.Success<*>).data
        MatcherAssert.assertThat(imagesList, CoreMatchers.notNullValue())
    }
}