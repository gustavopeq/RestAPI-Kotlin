package com.projects.moviemanager.features.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.moviemanager.common.domain.models.content.GenericContent
import com.projects.moviemanager.common.domain.models.person.PersonDetails
import com.projects.moviemanager.common.domain.models.util.DataLoadStatus
import com.projects.moviemanager.features.home.domain.HomeInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeInteractor: HomeInteractor
) : ViewModel() {
    private val _loadState: MutableStateFlow<DataLoadStatus> = MutableStateFlow(
        DataLoadStatus.Loading
    )
    val loadState: StateFlow<DataLoadStatus> get() = _loadState

    private val _trendingMulti: MutableStateFlow<List<GenericContent>> = MutableStateFlow(
        emptyList()
    )
    val trendingMulti: StateFlow<List<GenericContent>> get() = _trendingMulti

    private val _myWatchlist: MutableStateFlow<List<GenericContent>> = MutableStateFlow(
        emptyList()
    )
    val myWatchlist: StateFlow<List<GenericContent>> get() = _myWatchlist

    private val _trendingPerson: MutableStateFlow<List<PersonDetails>> = MutableStateFlow(
        emptyList()
    )
    val trendingPerson: StateFlow<List<PersonDetails>> get() = _trendingPerson

    private val _moviesComingSoon: MutableStateFlow<List<GenericContent>> = MutableStateFlow(
        emptyList()
    )
    val moviesComingSoon: StateFlow<List<GenericContent>> get() = _moviesComingSoon

    fun loadHomeScreen() {
        viewModelScope.launch {
            _trendingMulti.value = homeInteractor.getTrendingMulti()
            this.launch(Dispatchers.IO) {
                _myWatchlist.value = homeInteractor.getAllWatchlist()
            }
            _trendingPerson.value = homeInteractor.getTrendingPerson()
            _moviesComingSoon.value = homeInteractor.getMoviesComingSoon()
            _loadState.value = DataLoadStatus.Success
        }
    }
}
