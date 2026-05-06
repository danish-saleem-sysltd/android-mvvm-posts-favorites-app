package com.example.mvvm.postsfavorites.feature.posts.ui.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.example.mvvm.postsfavorites.feature.posts.ui.screen.main.MainScreen

fun NavGraphBuilder.postsNavGraph(
    onLogout: () -> Unit,
) {
    composable<PostsRoute.Main>(
        deepLinks = listOf(
            navDeepLink { uriPattern = "${PostsRoute.DEEPLINK_BASE}/${PostsRoute.SCREEN_ID_MAIN}" },
        ),
    ) {
        MainScreen(onLogout = onLogout)
    }
}
