package com.example.mvvm.postsfavorites.feature.posts.ui.screen.main.entity

import androidx.compose.runtime.Immutable

@Immutable
sealed class MainUiEvent {
    data class TogglePostFavorite(val postId: Int) : MainUiEvent()
    data class RemoveFavorite(val postId: Int) : MainUiEvent()
    data object Refresh : MainUiEvent()
    data object DismissError : MainUiEvent()
}
