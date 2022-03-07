package com.example.androidtask.remote

import com.example.androidtask.data.remote.ApiResponse
import retrofit2.Response

object ApiUtil {

    fun <T : Any> successCall(data: T) = createCall(Response.success(data))

    fun <T : Any> createCall(response: Response<T>): ApiResponse<T> =
        ApiResponse.create(200..229, response)
}
