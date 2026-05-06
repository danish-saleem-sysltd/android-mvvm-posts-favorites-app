package com.example.mvvm.postsfavorites.feature.auth.ui.nav

import kotlinx.serialization.Serializable

object AuthRoute {

    const val DEEPLINK_BASE: String = "postsfavorites://auth"
    const val SCREEN_ID_LOGIN: String = "login"

    @Serializable
    data object Login
}
