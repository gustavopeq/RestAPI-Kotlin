package com.projects.moviemanager.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.projects.moviemanager.database.dao.ContentEntityDao
import com.projects.moviemanager.database.model.ContentEntity

@Database(
    entities = [ContentEntity::class],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contentEntityDao(): ContentEntityDao
}
