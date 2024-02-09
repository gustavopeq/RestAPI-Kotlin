package com.projects.moviemanager.network.repository.show

import com.projects.moviemanager.network.models.ApiError
import com.projects.moviemanager.network.models.content.common.ContentCreditsResponse
import com.projects.moviemanager.network.models.content.common.ShowResponse
import com.projects.moviemanager.network.models.content.common.VideosByIdResponse
import com.projects.moviemanager.network.models.search.ContentPagingResponse
import com.projects.moviemanager.network.services.show.ShowService
import com.projects.moviemanager.network.util.Either
import com.projects.moviemanager.network.util.asFlow
import com.projects.moviemanager.network.util.toApiResult
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ShowRepositoryImpl @Inject constructor(
    private val showService: ShowService
) : ShowRepository {
    override suspend fun getShowList(
        contentListType: String,
        pageIndex: Int
    ): Flow<Either<ContentPagingResponse<ShowResponse>, ApiError>> {
        return toApiResult {
            showService.getShowList(
                contentListType = contentListType,
                pageIndex = pageIndex,
                language = "en-US"
            )
        }.asFlow()
    }

    override suspend fun getShowDetailsById(showId: Int): Flow<Either<ShowResponse, ApiError>> {
        return toApiResult {
            showService.getShowDetailsById(
                showId = showId
            )
        }.asFlow()
    }

    override suspend fun getShowCreditsById(
        showId: Int
    ): Flow<Either<ContentCreditsResponse, ApiError>> {
        return toApiResult {
            showService.getShowCreditsById(
                showId = showId
            )
        }.asFlow()
    }

    override suspend fun getShowVideosById(
        showId: Int
    ): Flow<Either<VideosByIdResponse, ApiError>> {
        return toApiResult {
            showService.getShowVideosById(
                showId = showId
            )
        }.asFlow()
    }

    override suspend fun getRecommendationsShowsById(
        showId: Int
    ): Flow<Either<ContentPagingResponse<ShowResponse>, ApiError>> {
        return toApiResult {
            showService.getRecommendationsShowsById(
                showId = showId
            )
        }.asFlow()
    }

    override suspend fun getSimilarShowsById(
        showId: Int
    ): Flow<Either<ContentPagingResponse<ShowResponse>, ApiError>> {
        return toApiResult {
            showService.getSimilarShowsById(
                showId = showId
            )
        }.asFlow()
    }
}
