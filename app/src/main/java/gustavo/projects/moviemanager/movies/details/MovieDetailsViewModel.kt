package gustavo.projects.moviemanager.movies.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gustavo.projects.moviemanager.config.AppConfiguration
import gustavo.projects.moviemanager.domain.models.Movie
import gustavo.projects.moviemanager.domain.models.MovieDetails
import gustavo.projects.moviemanager.network.ApiClient
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val apiClient: ApiClient
): ViewModel() {

    private val repository = MovieDetailsRepository(apiClient)

    private val _getMovieByIdLiveData = MutableLiveData<MovieDetails?>()
    val getMovieDetailsByIdLiveData: LiveData<MovieDetails?> = _getMovieByIdLiveData

    //Instance of displayed movie that can be saved into to watch list
    private var _movieDisplayed: Movie? = null
    val movieDisplayed: Movie
        get() = _movieDisplayed!!

    fun fetchMovie(movie_ID: Int){

        viewModelScope.launch {
            val response = repository.getMovieDetailsById(movie_ID, AppConfiguration.appLanguage)

            response?.let {
                _movieDisplayed = Movie(response.vote_average, response.id, response.poster_path, response.title)
            }

            _getMovieByIdLiveData.postValue(response)
        }
    }
}