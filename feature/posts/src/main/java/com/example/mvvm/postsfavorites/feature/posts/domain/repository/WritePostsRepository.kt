package com.example.mvvm.postsfavorites.feature.posts.domain.repository

import com.example.mvvm.postsfavorites.core.common.result.Result

interface WritePostsRepository {
    suspend fun refreshPosts(): Result<Unit>
    suspend fun toggleFavorite(postId: Int)
    suspend fun removeFavorite(postId: Int)
}
