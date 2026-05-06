package com.example.mvvm.postsfavorites.feature.posts.ui.fake

import com.example.mvvm.postsfavorites.feature.posts.domain.model.Post
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

fun fakePosts(): ImmutableList<Post> = persistentListOf(
    Post(id = 1, userId = 1, title = "Quis ut nam facilis", body = "Suscipit assumenda dolor pariatur.", isFavorite = false),
    Post(id = 2, userId = 1, title = "Sunt aut facere", body = "Quia et suscipit recusandae consequuntur.", isFavorite = true),
    Post(id = 3, userId = 2, title = "Ea molestias quasi", body = "Et iusto sed quo iure.", isFavorite = false),
)

fun fakeFavorites(): ImmutableList<Post> = persistentListOf(
    Post(id = 2, userId = 1, title = "Sunt aut facere", body = "Quia et suscipit recusandae consequuntur.", isFavorite = true),
    Post(id = 5, userId = 3, title = "Nesciunt quas odio", body = "Repudiandae veniam quaerat sunt sed.", isFavorite = true),
)
