package com.example.mvvm.postsfavorites.feature.posts.ui.screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mvvm.postsfavorites.core.ui.components.AppText
import com.example.mvvm.postsfavorites.core.ui.theme.AppTheme
import com.example.mvvm.postsfavorites.core.ui.theme.spacing
import com.example.mvvm.postsfavorites.feature.posts.R
import com.example.mvvm.postsfavorites.feature.posts.domain.model.Post
import com.example.mvvm.postsfavorites.feature.posts.ui.fake.fakePosts
import com.example.mvvm.postsfavorites.feature.posts.ui.reusable.PostItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun PostsTab(
    posts: ImmutableList<Post>,
    onTogglePostFavorite: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (posts.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            AppText(
                text = stringResource(R.string.posts_tab_empty),
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
        items(items = posts, key = { it.id }) { post ->
            PostItem(post = post, onClick = { onTogglePostFavorite(post.id) })
        }
    }
}

@Preview
@Composable
private fun PreviewPostsTab() {
    AppTheme {
        PostsTab(posts = fakePosts(), onTogglePostFavorite = {})
    }
}

@Preview
@Composable
private fun PreviewPostsTabEmpty() {
    AppTheme {
        PostsTab(posts = persistentListOf(), onTogglePostFavorite = {})
    }
}
