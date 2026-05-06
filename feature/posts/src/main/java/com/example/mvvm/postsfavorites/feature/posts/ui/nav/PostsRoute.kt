package com.example.mvvm.postsfavorites.feature.posts.ui.nav

import kotlinx.serialization.Serializable

object PostsRoute {

    const val DEEPLINK_BASE: String = "postsfavorites://posts"
    const val SCREEN_ID_MAIN: String = "main"

    @Serializable
    data object Main
}
