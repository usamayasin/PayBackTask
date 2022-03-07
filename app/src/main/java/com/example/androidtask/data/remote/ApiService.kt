package com.example.androidtask.data.remote

import com.example.androidtask.BuildConfig
import com.example.androidtask.data.model.ImageList
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/")
    suspend fun getImages(
        @Query("key") key: String = BuildConfig.API_KEY,
        @Query("page") page: Int,
        @Query("q") keyword: String,
        @Query("image_type") type: String = "photo"
    ): ApiResponse<ImageList>
}
