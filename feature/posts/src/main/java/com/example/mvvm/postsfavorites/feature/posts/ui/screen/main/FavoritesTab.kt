package com.example.mvvm.postsfavorites.feature.posts.ui.screen.main

import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
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
            SwipeToRevealDelete(onDelete = { onRemoveFavorite(post.id) }) {
                PostItem(post = post, onClick = {})
            }
        }
    }
}

private enum class SwipeAnchor { Closed, Open }

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SwipeToRevealDelete(
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    val deleteWidth = 88.dp
    val deleteWidthPx = with(density) { deleteWidth.toPx() }

    val state = remember {
        AnchoredDraggableState(
            initialValue = SwipeAnchor.Closed,
            anchors = DraggableAnchors {
                SwipeAnchor.Closed at 0f
                SwipeAnchor.Open at -deleteWidthPx
            },
            positionalThreshold = { totalDistance -> totalDistance * 0.5f },
            velocityThreshold = { with(density) { 200.dp.toPx() } },
            snapAnimationSpec = tween(durationMillis = 300),
            decayAnimationSpec = exponentialDecay(),
        )
    }

    Box(modifier = modifier.fillMaxWidth()) {
        // Invisible content sets the parent Box's height to match the card
        Box(modifier = Modifier.alpha(0f)) { content() }

        // Delete background pinned to the right, full card height
        Row(
            modifier = Modifier.matchParentSize(),
            horizontalArrangement = Arrangement.End,
        ) {
            DeleteAction(width = deleteWidth, onClick = onDelete)
        }

        // Foreground content drags horizontally
        Box(
            modifier = Modifier
                .offset { IntOffset(state.requireOffset().toInt(), 0) }
                .anchoredDraggable(state, Orientation.Horizontal),
        ) {
            content()
        }
    }
}

@Composable
private fun DeleteAction(
    width: androidx.compose.ui.unit.Dp,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .width(width)
            .fillMaxHeight()
            .background(
                color = MaterialTheme.colorScheme.error,
                shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp),
            )
            .clickable { onClick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onError,
            modifier = Modifier.size(24.dp),
        )
        Spacer(Modifier.height(MaterialTheme.spacing.xxs))
        AppText(
            text = stringResource(R.string.posts_favorites_swipe_delete),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onError,
            textAlign = TextAlign.Center,
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
