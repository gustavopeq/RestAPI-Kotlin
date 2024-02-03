package com.projects.moviemanager.features.browse.events

import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.common.domain.models.util.SortTypeItem

sealed class BrowseEvent {
    data class UpdateSortType(
        val movieListType: SortTypeItem,
        val mediaType: MediaType
    ) : BrowseEvent()
    data class UpdateMediaType(
        val mediaType: MediaType
    ) : BrowseEvent()
    data object OnError : BrowseEvent()
}
