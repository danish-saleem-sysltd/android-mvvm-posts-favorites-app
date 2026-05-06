package com.example.mvvm.postsfavorites.feature.posts.data.repository

import android.util.Log
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
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

internal class PostsRepositoryImpl @Inject constructor(
    private val api: PostsApiService,
    private val postDao: PostDao,
    private val requestHandler: RequestHandler,
) : ReadPostsRepository, WritePostsRepository {

    override fun observePosts(): Flow<List<Post>> =
        postDao.observeAll()
            .onEach { Log.d(TAG, "observePosts emit: ${it.size} rows") }
            .map { it.toDomain() }

    override fun observeFavorites(): Flow<List<Post>> =
        postDao.observeFavorites()
            .onEach { Log.d(TAG, "observeFavorites emit: ${it.size} rows") }
            .map { it.toDomain() }

    override suspend fun refreshPosts(): Result<Unit> = requestHandler.handle {
        Log.d(TAG, "refreshPosts: fetching /posts")
        val remote = api.getPosts()
        Log.d(TAG, "refreshPosts: received ${remote.size} posts, upserting")
        postDao.upsertPreservingFavorites(remote.toEntities())
        Log.d(TAG, "refreshPosts: upsert complete")
    }

    override suspend fun toggleFavorite(postId: Int) {
        val current = postDao.isFavorite(postId)
        if (current == null) {
            Log.d(TAG, "toggleFavorite: postId=$postId not found, no-op")
            return
        }
        Log.d(TAG, "toggleFavorite: postId=$postId $current -> ${!current}")
        postDao.setFavorite(postId, !current)
    }

    override suspend fun removeFavorite(postId: Int) {
        Log.d(TAG, "removeFavorite: postId=$postId")
        postDao.setFavorite(postId, false)
    }

    private companion object {
        const val TAG = "Posts.Repo"
    }
}
