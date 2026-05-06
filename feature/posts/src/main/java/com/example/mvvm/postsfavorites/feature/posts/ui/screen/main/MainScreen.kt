package com.example.mvvm.postsfavorites.feature.posts.ui.screen.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mvvm.postsfavorites.core.ui.theme.AppTheme
import com.example.mvvm.postsfavorites.feature.posts.R
import com.example.mvvm.postsfavorites.feature.posts.ui.fake.fakeFavorites
import com.example.mvvm.postsfavorites.feature.posts.ui.fake.fakePosts
import com.example.mvvm.postsfavorites.feature.posts.ui.screen.main.entity.MainState
import com.example.mvvm.postsfavorites.feature.posts.ui.screen.main.entity.MainUiEvent

private const val TAB_POSTS = 0
private const val TAB_FAVORITES = 1

@Composable
fun MainScreen(
    onLogout: () -> Unit,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    MainContent(
        state = state,
        onEvent = viewModel::onEvent,
        onLogout = onLogout,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainContent(
    state: MainState,
    onEvent: (MainUiEvent) -> Unit,
    onLogout: () -> Unit,
) {
    var selectedTab by rememberSaveable { mutableIntStateOf(TAB_POSTS) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.posts_main_title)) },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = stringResource(R.string.posts_main_logout),
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == TAB_POSTS,
                    onClick = { selectedTab = TAB_POSTS },
                    text = { Text(stringResource(R.string.posts_main_tab_posts)) },
                )
                Tab(
                    selected = selectedTab == TAB_FAVORITES,
                    onClick = { selectedTab = TAB_FAVORITES },
                    text = { Text(stringResource(R.string.posts_main_tab_favorites)) },
                )
            }
            Box(modifier = Modifier.fillMaxSize()) {
                when (selectedTab) {
                    TAB_POSTS -> PostsTab(
                        posts = state.posts,
                        onTogglePostFavorite = { onEvent(MainUiEvent.TogglePostFavorite(it)) },
                    )
                    else -> FavoritesTab(
                        favorites = state.favorites,
                        onRemoveFavorite = { onEvent(MainUiEvent.RemoveFavorite(it)) },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewMainScreenPosts() {
    AppTheme {
        MainContent(
            state = MainState(posts = fakePosts(), favorites = fakeFavorites()),
            onEvent = {},
            onLogout = {},
        )
    }
}
