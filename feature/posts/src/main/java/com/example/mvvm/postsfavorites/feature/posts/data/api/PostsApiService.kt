package com.example.mvvm.postsfavorites.feature.posts.data.api

import com.example.mvvm.postsfavorites.feature.posts.data.dto.PostDto
import retrofit2.http.GET

interface PostsApiService {
    @GET("posts")
    suspend fun getPosts(): List<PostDto>
}
