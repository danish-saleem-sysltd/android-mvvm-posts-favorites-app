package com.example.mvvm.postsfavorites.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
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
    val session by viewModel.sessionState.collectAsStateWithLifecycle()

    when (session) {
        SessionUiState.Loading -> SplashContent()
        else -> AuthenticatedNavHost(session = session, onLogout = viewModel::logout)
    }
}

@Composable
private fun AuthenticatedNavHost(
    session: SessionUiState,
    onLogout: () -> Unit,
) {
    val navController = rememberNavController()
    val startDestination: Any = if (session == SessionUiState.LoggedIn) PostsRoute.Main else AuthRoute.Login

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        authNavGraph(
            onLoginSuccess = {
                navController.navigate(PostsRoute.Main) {
                    popUpTo(AuthRoute.Login) { inclusive = true }
                }
            },
        )
        postsNavGraph(onLogout = onLogout)
    }

    HandleSessionTransitions(session = session, navController = navController)
}

@Composable
private fun HandleSessionTransitions(
    session: SessionUiState,
    navController: NavHostController,
) {
    LaunchedEffect(session) {
        if (session == SessionUiState.LoggedOut) {
            navController.navigate(AuthRoute.Login) {
                popUpTo(navController.graph.id) { inclusive = true }
            }
        }
    }
}

@Composable
private fun SplashContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}
