package com.example.mvvm.postsfavorites.feature.posts.ui.screen.main.entity

import com.example.mvvm.postsfavorites.feature.posts.domain.model.Post
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MainState(
    val posts: ImmutableList<Post> = persistentListOf(),
    val favorites: ImmutableList<Post> = persistentListOf(),
    val isRefreshing: Boolean = false,
    val errorVisible: Boolean = false,
)
