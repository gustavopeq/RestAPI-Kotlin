package com.projects.moviemanager.network.di

import com.projects.moviemanager.network.ApiClient
import com.projects.moviemanager.network.services.MovieDbService
import com.projects.moviemanager.network.services.movie.MovieService
import com.projects.moviemanager.network.services.person.PersonService
import com.projects.moviemanager.network.services.show.ShowService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideMovieApiService(retrofit: Retrofit): ApiClient {
        val movieDbService = retrofit.create(MovieDbService::class.java)

        return ApiClient(movieDbService)
    }

    @Singleton
    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }

    @Singleton
    @Provides
    fun provideShowService(retrofit: Retrofit): ShowService {
        return retrofit.create(ShowService::class.java)
    }

    @Singleton
    @Provides
    fun providePersonService(retrofit: Retrofit): PersonService {
        return retrofit.create(PersonService::class.java)
    }
}
