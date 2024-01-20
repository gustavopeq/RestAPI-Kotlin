package com.projects.moviemanager.database.repository

import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.database.dao.ContentEntityDao
import com.projects.moviemanager.database.model.ContentEntity

class DatabaseRepositoryImpl(
    private val contentEntityDao: ContentEntityDao
) : DatabaseRepository {

    override suspend fun insertItem(contentId: Int, mediaType: MediaType, listId: String) {
        val item = ContentEntity(
            contentId = contentId,
            mediaType = mediaType.name,
            listId = listId
        )

        contentEntityDao.insert(item)
    }

    override suspend fun deleteItem(
        contentId: Int,
        mediaType: MediaType
    ) {
        contentEntityDao.delete(
            contentId = contentId,
            mediaType = mediaType.name
        )
    }

    override suspend fun getAllItems(): List<ContentEntity> {
        return contentEntityDao.getAllItems()
    }

    override suspend fun searchItem(
        contentId: Int,
        mediaType: MediaType
    ): ContentEntity? {
        return contentEntityDao.searchItem(
            contentId = contentId,
            mediaType = mediaType.name
        )
    }
}
