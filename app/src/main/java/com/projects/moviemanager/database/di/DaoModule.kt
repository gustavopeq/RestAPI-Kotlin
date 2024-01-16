package com.projects.moviemanager.database.di

import com.projects.moviemanager.database.AppDatabase
import com.projects.moviemanager.database.dao.ItemEntityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    @Singleton
    fun provideItemEntityDao(
        appDatabase: AppDatabase
    ): ItemEntityDao {
        return appDatabase.itemEntityDao()
    }
}
