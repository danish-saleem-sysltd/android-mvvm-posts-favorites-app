package com.example.mvvm.postsfavorites.feature.posts.data.repository

import com.example.mvvm.postsfavorites.core.common.result.Result
import com.example.mvvm.postsfavorites.core.network.handler.RequestHandler
import com.example.mvvm.postsfavorites.feature.posts.data.api.PostsApiService
import com.example.mvvm.postsfavorites.feature.posts.data.local.PostDao
import com.example.mvvm.postsfavorites.feature.posts.data.mapper.toDomain
import com.example.mvvm.postsfavorites.feature.posts.data.mapper.toEntities
import com.example.mvvm.postsfavorites.feature.posts.domain.model.Post
import com.example.mvvm.postsfavorites.feature.posts.domain.repository.ReadPostsRepository
import com.example.mvvm.postsfavorites.feature.posts.domain.repository.WritePostsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class PostsRepositoryImpl @Inject constructor(
    private val api: PostsApiService,
    private val postDao: PostDao,
    private val requestHandler: RequestHandler,
) : ReadPostsRepository, WritePostsRepository {

    override fun observePosts(): Flow<List<Post>> =
        postDao.observeAll().map { it.toDomain() }

    override fun observeFavorites(): Flow<List<Post>> =
        postDao.observeFavorites().map { it.toDomain() }

    override suspend fun refreshPosts(): Result<Unit> = requestHandler.handle {
        val remote = api.getPosts()
        postDao.upsertPreservingFavorites(remote.toEntities())
    }

    override suspend fun toggleFavorite(postId: Int) {
        val current = postDao.isFavorite(postId) ?: return
        postDao.setFavorite(postId, !current)
    }

    override suspend fun removeFavorite(postId: Int) {
        postDao.setFavorite(postId, false)
    }
}
