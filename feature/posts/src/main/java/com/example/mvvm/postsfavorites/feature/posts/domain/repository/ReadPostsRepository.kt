package com.example.mvvm.postsfavorites.feature.posts.domain.repository

import com.example.mvvm.postsfavorites.feature.posts.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface ReadPostsRepository {
    fun observePosts(): Flow<List<Post>>
    fun observeFavorites(): Flow<List<Post>>
}
