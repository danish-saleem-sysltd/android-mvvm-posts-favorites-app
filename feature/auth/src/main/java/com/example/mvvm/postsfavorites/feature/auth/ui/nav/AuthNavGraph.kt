package com.example.mvvm.postsfavorites.feature.auth.ui.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.example.mvvm.postsfavorites.feature.auth.ui.screen.login.LoginScreen

fun NavGraphBuilder.authNavGraph(
    onLoginSuccess: () -> Unit,
) {
    composable<AuthRoute.Login>(
        deepLinks = listOf(
            navDeepLink { uriPattern = "${AuthRoute.DEEPLINK_BASE}/${AuthRoute.SCREEN_ID_LOGIN}" },
        ),
    ) {
        LoginScreen(onLoginSuccess = onLoginSuccess)
    }
}
