package com.projects.moviemanager.network.services.movie

import com.projects.moviemanager.network.models.content.common.ContentCreditsResponse
import com.projects.moviemanager.network.models.content.common.ContentListPageResponse
import com.projects.moviemanager.network.models.content.movie.MovieApiResponse
import com.projects.moviemanager.network.models.content.common.VideosByIdResponse
import com.projects.moviemanager.common.util.Constants
import com.projects.moviemanager.common.util.Constants.ENGLISH_LANGUAGE_CODE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("movie/{movie_list_type}?api_key=${Constants.API_KEY}")
    suspend fun getMovieList(
        @Path("movie_list_type") movieListType: String,
        @Query("page") pageIndex: Int,
        @Query("language") language: String
    ): Response<ContentListPageResponse<MovieApiResponse>>

    @GET("movie/{movie_ID}?api_key=${Constants.API_KEY}")
    suspend fun getMovieDetailsById(
        @Path("movie_ID") movieId: Int,
        @Query("language") language: String = ENGLISH_LANGUAGE_CODE
    ): Response<MovieApiResponse>

    @GET("movie/{movie_ID}/credits?api_key=${Constants.API_KEY}")
    suspend fun getMovieCreditsById(
        @Path("movie_ID") movieId: Int
    ): Response<ContentCreditsResponse>

    @GET("movie/{movie_ID}/videos?api_key=${Constants.API_KEY}")
    suspend fun getMovieVideosById(
        @Path("movie_ID") movieId: Int
    ): Response<VideosByIdResponse>

    @GET("movie/{movie_ID}/recommendations?api_key=${Constants.API_KEY}")
    suspend fun getSimilarMoviesById(
        @Path("movie_ID") movieId: Int
    ): Response<ContentListPageResponse<MovieApiResponse>>
}
