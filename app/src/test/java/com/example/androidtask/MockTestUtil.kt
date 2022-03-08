package com.example.androidtask

import com.example.androidtask.data.local.entity.ImagesEntity
import com.example.androidtask.data.model.ImageList

class MockTestUtil {
    companion object {

        fun getMockImages(count: Int): ImageList {
            val images =  (0 until count).map {
                ImagesEntity(
                    id = 1,
                    tags = "Money,cash,currency",
                    previewURL = "https://cdn.pixabay.com/photo/2016/11/22/23/44/porsche-1851246_150.jpg",
                    largeImageURL = "https://pixabay.com/get/g535841b1afc401514beff9625798b2e3347f2b6dbd5e78ae4b463f0ea641cef7f5b3bf40df1bad068f0d58450224470cf25202ac99f321c29d2fbc95c9503f98_1280.jpg",
                    downloads = 12,
                    comments = 10,
                    likes = 2,
                    views = 29,
                    user = "fakeUser",
                    keyword="fake"
                )
            }
            return ImageList(10,10,images)
        }

    }
}
