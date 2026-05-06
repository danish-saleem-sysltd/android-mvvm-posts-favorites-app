package com.example.mvvm.postsfavorites.feature.posts.data.mapper

import com.example.mvvm.postsfavorites.feature.posts.data.dto.PostDto
import com.example.mvvm.postsfavorites.feature.posts.data.local.PostEntity
import com.example.mvvm.postsfavorites.feature.posts.domain.model.Post

fun PostDto.toEntity(): PostEntity = PostEntity(
    id = id,
    userId = userId,
    title = title,
    body = body,
    isFavorite = false,
)

fun List<PostDto>.toEntities(): List<PostEntity> = map { it.toEntity() }

fun PostEntity.toDomain(): Post = Post(
    id = id,
    userId = userId,
    title = title,
    body = body,
    isFavorite = isFavorite,
)

fun List<PostEntity>.toDomain(): List<Post> = map { it.toDomain() }
