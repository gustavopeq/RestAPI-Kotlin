package com.projects.moviemanager.features.details.domain

import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.database.repository.DatabaseRepository
import com.projects.moviemanager.domain.models.content.ContentCast
import com.projects.moviemanager.domain.models.content.DetailedMediaInfo
import com.projects.moviemanager.domain.models.content.MediaContent
import com.projects.moviemanager.domain.models.content.Videos
import com.projects.moviemanager.domain.models.content.toContentCast
import com.projects.moviemanager.domain.models.content.toMediaContent
import com.projects.moviemanager.domain.models.content.toMediaContentList
import com.projects.moviemanager.domain.models.content.toMovieDetailsInfo
import com.projects.moviemanager.domain.models.content.toPersonDetailsInfo
import com.projects.moviemanager.domain.models.content.toShowDetailsInfo
import com.projects.moviemanager.domain.models.content.toVideos
import com.projects.moviemanager.domain.models.person.PersonImage
import com.projects.moviemanager.domain.models.person.toPersonImage
import com.projects.moviemanager.features.watchlist.model.DefaultLists
import com.projects.moviemanager.network.repository.movie.MovieRepository
import com.projects.moviemanager.network.repository.person.PersonRepository
import com.projects.moviemanager.network.repository.show.ShowRepository
import com.projects.moviemanager.network.response.content.movie.MovieApiResponse
import com.projects.moviemanager.network.response.content.show.ShowApiResponse
import com.projects.moviemanager.network.response.person.PersonDetailsResponse
import com.projects.moviemanager.network.util.Left
import com.projects.moviemanager.network.util.Right
import javax.inject.Inject
import timber.log.Timber

class DetailsInteractor @Inject constructor(
    private val movieRepository: MovieRepository,
    private val showRepository: ShowRepository,
    private val personRepository: PersonRepository,
    private val databaseRepository: DatabaseRepository
) {
    suspend fun getContentDetailsById(
        contentId: Int,
        mediaType: MediaType
    ): DetailedMediaInfo? {
        val result = when (mediaType) {
            MediaType.MOVIE -> movieRepository.getMovieDetailsById(contentId)
            MediaType.SHOW -> showRepository.getShowDetailsById(contentId)
            MediaType.PERSON -> personRepository.getPersonDetailsById(contentId)
        }

        var contentDetails: DetailedMediaInfo? = null
        result.collect { response ->
            when (response) {
                is Right -> {
                    Timber.e("getContentDetailsById failed with error: ${response.error}")
                }
                is Left -> {
                    contentDetails = when (mediaType) {
                        MediaType.MOVIE -> (response.value as MovieApiResponse).toMovieDetailsInfo()
                        MediaType.SHOW -> (response.value as ShowApiResponse).toShowDetailsInfo()
                        MediaType.PERSON -> {
                            (response.value as PersonDetailsResponse).toPersonDetailsInfo()
                        }
                    }
                }
            }
        }
        return contentDetails
    }

    suspend fun getContentCreditsById(
        contentId: Int,
        mediaType: MediaType
    ): List<ContentCast> {
        val result = when (mediaType) {
            MediaType.MOVIE -> movieRepository.getMovieCreditsById(contentId)
            MediaType.SHOW -> showRepository.getShowCreditsById(contentId)
            else -> return emptyList()
        }

        var contentCastList: List<ContentCast> = emptyList()
        result.collect { response ->
            when (response) {
                is Right -> {
                    Timber.e("getContentCreditsById failed with error: ${response.error}")
                }
                is Left -> {
                    contentCastList = response.value.cast.map {
                        it.toContentCast()
                    }
                }
            }
        }
        return contentCastList
    }

    suspend fun getContentVideosById(
        contentId: Int,
        mediaType: MediaType
    ): List<Videos> {
        val result = when (mediaType) {
            MediaType.MOVIE -> movieRepository.getMovieVideosById(contentId)
            MediaType.SHOW -> showRepository.getShowVideosById(contentId)
            else -> return emptyList()
        }

        var videoList: List<Videos> = emptyList()
        result.collect { response ->
            when (response) {
                is Right -> {
                    Timber.e("getContentVideosById failed with error: ${response.error}")
                }
                is Left -> {
                    videoList = response.value.results.map {
                        it.toVideos()
                    }
                }
            }
        }

        return videoList
    }

    suspend fun getSimilarContentById(
        contentId: Int,
        mediaType: MediaType
    ): List<MediaContent> {
        val result = when (mediaType) {
            MediaType.MOVIE -> movieRepository.getSimilarMoviesById(contentId)
            MediaType.SHOW -> showRepository.getSimilarShowsById(contentId)
            else -> return emptyList()
        }

        var listOfSimilar: List<MediaContent> = emptyList()
        result.collect { response ->
            when (response) {
                is Right -> {
                    Timber.e("getSimilarContentById failed with error: ${response.error}")
                }
                is Left -> {
                    listOfSimilar = response.value.results
                        .filter { it.poster_path?.isNotEmpty() == true }
                        .map {
                            it.toMediaContent()
                        }
                }
            }
        }
        return listOfSimilar
    }

    suspend fun getPersonCreditsById(
        personId: Int
    ): List<MediaContent> {
        val result = personRepository.getPersonCreditsById(personId)

        var mediaContentList: List<MediaContent> = emptyList()
        result.collect { response ->
            when (response) {
                is Right -> {
                    Timber.e("getPersonCreditsById failed with error: ${response.error}")
                }
                is Left -> {
                    mediaContentList = response.value.cast.toMediaContentList()
                }
            }
        }
        return mediaContentList
    }

    suspend fun getPersonImages(
        personId: Int
    ): List<PersonImage> {
        val result = personRepository.getPersonImagesById(personId)

        var imageList: List<PersonImage> = emptyList()
        result.collect { response ->
            when (response) {
                is Right -> {
                    Timber.e("getPersonImages failed with error: ${response.error}")
                }
                is Left -> {
                    imageList = response.value.profiles?.filter {
                        it?.file_path?.isNotEmpty() == true
                    }?.mapNotNull {
                        it?.toPersonImage()
                    } ?: emptyList()
                }
            }
        }
        return imageList
    }

    suspend fun verifyContentInLists(
        contentId: Int,
        mediaType: MediaType
    ): Map<String, Boolean> {
        val result = databaseRepository.searchItems(
            contentId = contentId,
            mediaType = mediaType
        )
        val contentInListMap = mutableMapOf(
            DefaultLists.WATCHLIST.listId to false,
            DefaultLists.WATCHED.listId to false
        )

        result.forEach { content ->
            contentInListMap[content.listId] = true
        }

        return contentInListMap
    }

    suspend fun addToWatchlist(
        contentId: Int,
        mediaType: MediaType,
        listId: String
    ) {
        databaseRepository.insertItem(
            contentId = contentId,
            mediaType = mediaType,
            listId = listId
        )
    }

    suspend fun removeFromWatchlist(
        contentId: Int,
        mediaType: MediaType,
        listId: String
    ) {
        databaseRepository.deleteItem(
            contentId = contentId,
            mediaType = mediaType,
            listId = listId
        )
    }
}