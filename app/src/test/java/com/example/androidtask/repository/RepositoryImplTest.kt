package com.example.androidtask.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.coyotask.MainCoroutinesRule
import com.example.androidtask.MockTestUtil
import com.example.androidtask.data.remote.ApiService
import com.example.androidtask.data.remote.DataState
import com.example.androidtask.data.repository.RepositoryImpl
import com.example.androidtask.remote.ApiUtil.successCall
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RepositoryImplTest {

    // Subject under test
    private lateinit var repository: RepositoryImpl

    @MockK
    private lateinit var apiService: ApiService

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test loadImages() gives list of images`() = runBlocking {
        // Given
        repository = RepositoryImpl(apiService)
        val givenImagesList = MockTestUtil.getMockImages(5)
        val apiCall = successCall(givenImagesList)

        // When
        coEvery { apiService.getImages(page = any(), keyword =  any()) }
            .returns(apiCall)

        // Invoke
        val apiResponseFlow = repository.getImages(0, "fake")

        // Then
        MatcherAssert.assertThat(apiResponseFlow, CoreMatchers.notNullValue())

        val imagesListDataState = apiResponseFlow.first()
        MatcherAssert.assertThat(imagesListDataState, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(
            imagesListDataState,
            CoreMatchers.instanceOf(DataState.Success::class.java)
        )

        val imagesList = (imagesListDataState as DataState.Success).data
        MatcherAssert.assertThat(imagesList, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(imagesList!!.hits.size, CoreMatchers.`is`(givenImagesList.hits.size))

        coVerify(exactly = 1) { apiService.getImages(page = any(), keyword = any()) }
        confirmVerified(apiService)
    }

    @Test
    fun `test loadImages() gives empty list of images`() = runBlocking {
        // Given
        repository = RepositoryImpl(apiService)
        val givenImagesList = MockTestUtil.getMockImages(0)
        val apiCall = successCall(givenImagesList)

        // When
        coEvery { apiService.getImages(page = any(), keyword = any()) }
            .returns(apiCall)

        // Invoke
        val apiResponseFlow = repository.getImages(0, "fake")

        // Then
        MatcherAssert.assertThat(apiResponseFlow, CoreMatchers.notNullValue())

        val imagesListDataState = apiResponseFlow.first()
        MatcherAssert.assertThat(imagesListDataState, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(
            imagesListDataState,
            CoreMatchers.instanceOf(DataState.Success::class.java)
        )

        val imagesList = (imagesListDataState as DataState.Success).data
        MatcherAssert.assertThat(imagesList, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(imagesList!!.hits.size, CoreMatchers.`is`(givenImagesList.hits.size))

        coVerify(exactly = 1) { apiService.getImages(page = any(),keyword = any()) }
        confirmVerified(apiService)
    }

}