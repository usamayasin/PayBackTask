package com.example.androidtask.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.androidtask.data.local.AppDatabase
import com.example.androidtask.data.local.dao.ImagesDao
import com.example.androidtask.data.local.entity.ImagesEntity
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LocalRepositoryTest {

    private lateinit var imagesDao: ImagesDao
    private lateinit var appDatabase: AppDatabase

    @Before
    fun start() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        imagesDao = appDatabase.imagesDao()
    }

    @Test
    fun insertImagesItem() = runBlocking {
        val item = ImagesEntity(
            id = 1,
            tags = "Money,cash,currency",
            previewURL = "https://cdn.pixabay.com/photo/2016/11/22/23/44/porsche-1851246_150.jpg",
            largeImageURL = "https://pixabay.com/get/g535841b1afc401514beff9625798b2e3347f2b6dbd5e78ae4b463f0ea641cef7f5b3bf40df1bad068f0d58450224470cf25202ac99f321c29d2fbc95c9503f98_1280.jpg",
            downloads = 12,
            comments = 10,
            likes = 2,
            views = 29,
            user = "fakeUser",
            keyword = "fake"
        )
        imagesDao.insertImages(listOf(item))
        val result = imagesDao.getAllImages("fake")
        assertNotNull(result)
    }
}