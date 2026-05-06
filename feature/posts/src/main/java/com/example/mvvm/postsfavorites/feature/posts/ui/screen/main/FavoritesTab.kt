package com.example.mvvm.postsfavorites.feature.posts.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mvvm.postsfavorites.core.ui.components.AppText
import com.example.mvvm.postsfavorites.core.ui.theme.AppTheme
import com.example.mvvm.postsfavorites.core.ui.theme.spacing
import com.example.mvvm.postsfavorites.feature.posts.R
import com.example.mvvm.postsfavorites.feature.posts.domain.model.Post
import com.example.mvvm.postsfavorites.feature.posts.ui.fake.fakeFavorites
import com.example.mvvm.postsfavorites.feature.posts.ui.reusable.PostItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun FavoritesTab(
    favorites: ImmutableList<Post>,
    onRemoveFavorite: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (favorites.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            AppText(
                text = stringResource(R.string.posts_favorites_empty),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        return
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(MaterialTheme.spacing.md),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm),
    ) {
        items(items = favorites, key = { it.id }) { post ->
            SwipeToDismissFavoriteRow(
                post = post,
                onRemove = { onRemoveFavorite(post.id) },
            )
        }
    }
}

@Composable
private fun SwipeToDismissFavoriteRow(
    post: Post,
    onRemove: () -> Unit,
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            value == SwipeToDismissBoxValue.EndToStart || value == SwipeToDismissBoxValue.StartToEnd
        },
    )

    LaunchedEffect(dismissState.currentValue) {
        if (dismissState.currentValue != SwipeToDismissBoxValue.Settled) {
            onRemove()
        }
    }

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = { DismissBackground() },
    ) {
        PostItem(post = post, onClick = {})
    }
}

@Composable
private fun DismissBackground() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.errorContainer, MaterialTheme.shapes.medium)
            .padding(MaterialTheme.spacing.md),
        contentAlignment = Alignment.CenterEnd,
    ) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(R.string.posts_favorites_swipe_delete),
            tint = MaterialTheme.colorScheme.onErrorContainer,
        )
    }
}

@Preview
@Composable
private fun PreviewFavoritesTab() {
    AppTheme {
        FavoritesTab(favorites = fakeFavorites(), onRemoveFavorite = {})
    }
}

@Preview
@Composable
private fun PreviewFavoritesTabEmpty() {
    AppTheme {
        FavoritesTab(favorites = persistentListOf(), onRemoveFavorite = {})
    }
}
