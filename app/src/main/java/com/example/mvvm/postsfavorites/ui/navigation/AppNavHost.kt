package com.example.mvvm.postsfavorites.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.mvvm.postsfavorites.MainViewModel
import com.example.mvvm.postsfavorites.SessionUiState
import com.example.mvvm.postsfavorites.feature.auth.ui.nav.AuthRoute
import com.example.mvvm.postsfavorites.feature.auth.ui.nav.authNavGraph
import com.example.mvvm.postsfavorites.feature.posts.ui.nav.PostsRoute
import com.example.mvvm.postsfavorites.feature.posts.ui.nav.postsNavGraph

@Composable
fun AppNavHost(
    viewModel: MainViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    val session by viewModel.sessionState.collectAsStateWithLifecycle()

    LaunchedEffect(session) {
        when (session) {
            SessionUiState.LoggedIn -> navController.navigate(PostsRoute.Main) {
                popUpTo(navController.graph.id) { inclusive = true }
            }
            SessionUiState.LoggedOut -> navController.navigate(AuthRoute.Login) {
                popUpTo(navController.graph.id) { inclusive = true }
            }
            SessionUiState.Loading -> Unit
        }
    }

    NavHost(
        navController = navController,
        startDestination = AuthRoute.Login,
    ) {
        authNavGraph(
            onLoginSuccess = {
                navController.navigate(PostsRoute.Main) {
                    popUpTo(AuthRoute.Login) { inclusive = true }
                }
            },
        )
        postsNavGraph(
            onLogout = {
                viewModel.logout()
            },
        )
    }
}
