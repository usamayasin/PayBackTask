package com.example.androidtask.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import com.example.androidtask.BuildConfig
import com.example.androidtask.data.remote.ApiResponseCallAdapterFactory
import com.example.androidtask.data.remote.ApiService
import com.example.androidtask.utils.Constants

/**
 * The Dagger Module to provide the instances of [OkHttpClient], [Retrofit], and [ApiService] classes.
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkApiModule {

    @Singleton
    @Provides
    fun provideOKHttpClient(): OkHttpClient {
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val apiInterceptor = Interceptor { chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .addHeader(
                        "key", BuildConfig.API_KEY
                    )
                    .build()
            )
        }

        return OkHttpClient.Builder()
            .readTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(apiInterceptor)
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
