package com.example.mvvm.postsfavorites.feature.posts.domain.model

data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    val isFavorite: Boolean,
)
