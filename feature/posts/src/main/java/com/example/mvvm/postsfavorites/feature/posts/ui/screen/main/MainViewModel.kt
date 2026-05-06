package com.example.mvvm.postsfavorites.feature.posts.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.postsfavorites.core.common.result.Result
import com.example.mvvm.postsfavorites.feature.posts.domain.repository.ReadPostsRepository
import com.example.mvvm.postsfavorites.feature.posts.domain.repository.WritePostsRepository
import com.example.mvvm.postsfavorites.feature.posts.ui.screen.main.entity.MainState
import com.example.mvvm.postsfavorites.feature.posts.ui.screen.main.entity.MainUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val readPostsRepository: ReadPostsRepository,
    private val writePostsRepository: WritePostsRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()

    init {
        observePosts()
        observeFavorites()
        refresh()
    }

    fun onEvent(event: MainUiEvent) {
        when (event) {
            is MainUiEvent.TogglePostFavorite -> toggleFavorite(event.postId)
            is MainUiEvent.RemoveFavorite -> removeFavorite(event.postId)
            MainUiEvent.Refresh -> refresh()
            MainUiEvent.DismissError -> _state.update { it.copy(errorVisible = false) }
        }
    }

    private fun observePosts() {
        viewModelScope.launch {
            readPostsRepository.observePosts().collect { posts ->
                _state.update { it.copy(posts = posts.toImmutableList()) }
            }
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            readPostsRepository.observeFavorites().collect { favorites ->
                _state.update { it.copy(favorites = favorites.toImmutableList()) }
            }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true) }
            val result = writePostsRepository.refreshPosts()
            _state.update {
                it.copy(
                    isRefreshing = false,
                    errorVisible = result is Result.Error && it.posts.isEmpty(),
                )
            }
        }
    }

    private fun toggleFavorite(postId: Int) {
        viewModelScope.launch { writePostsRepository.toggleFavorite(postId) }
    }

    private fun removeFavorite(postId: Int) {
        viewModelScope.launch { writePostsRepository.removeFavorite(postId) }
    }
}
