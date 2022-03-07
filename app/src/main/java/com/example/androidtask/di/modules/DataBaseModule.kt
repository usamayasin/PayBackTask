package com.example.androidtask.di.modules

import android.content.Context
import com.example.androidtask.data.local.AppDatabase
import com.example.androidtask.data.local.dao.ImagesDao
import com.example.androidtask.data.local.repository.LocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Singleton
    @Provides
    fun providesLocalRepository(imagesDao: ImagesDao): LocalRepository {
        return LocalRepository(imagesDao)
    }

    @Singleton
    @Provides
    fun provideCurrenciesDao(appDatabase: AppDatabase): ImagesDao {
        return appDatabase.imagesDao()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

}