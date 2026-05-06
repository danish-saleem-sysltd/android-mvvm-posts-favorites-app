package com.example.mvvm.postsfavorites.feature.posts.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Query("SELECT * FROM posts ORDER BY id ASC")
    fun observeAll(): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE isFavorite = 1 ORDER BY id ASC")
    fun observeFavorites(): Flow<List<PostEntity>>

    @Query("SELECT isFavorite FROM posts WHERE id = :postId LIMIT 1")
    suspend fun isFavorite(postId: Int): Boolean?

    @Query("UPDATE posts SET isFavorite = :favorite WHERE id = :postId")
    suspend fun setFavorite(postId: Int, favorite: Boolean)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIfMissing(posts: List<PostEntity>)

    @Transaction
    suspend fun upsertPreservingFavorites(posts: List<PostEntity>) {
        insertIfMissing(posts)
        for (post in posts) {
            updateContent(
                id = post.id,
                userId = post.userId,
                title = post.title,
                body = post.body,
            )
        }
    }

    @Query("UPDATE posts SET userId = :userId, title = :title, body = :body WHERE id = :id")
    suspend fun updateContent(id: Int, userId: Int, title: String, body: String)
}
