package com.example.mvvm.postsfavorites.feature.posts.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PostEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class PostsDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}
