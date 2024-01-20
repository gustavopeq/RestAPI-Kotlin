package com.projects.moviemanager.features.watchlist.events

import com.projects.moviemanager.common.domain.MediaType

sealed class WatchlistEvent {
    data class RemoveItem(
        val contentId: Int,
        val mediaType: MediaType
    ) : WatchlistEvent()
    data class SelectList(
        val list: String
    ) : WatchlistEvent()
}