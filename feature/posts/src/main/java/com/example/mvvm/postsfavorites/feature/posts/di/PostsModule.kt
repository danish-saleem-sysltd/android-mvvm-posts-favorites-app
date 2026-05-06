package com.example.mvvm.postsfavorites.feature.posts.di

import android.content.Context
import androidx.room.Room
import com.example.mvvm.postsfavorites.feature.posts.data.api.PostsApiService
import com.example.mvvm.postsfavorites.feature.posts.data.local.PostDao
import com.example.mvvm.postsfavorites.feature.posts.data.local.PostsDatabase
import com.example.mvvm.postsfavorites.feature.posts.data.repository.PostsRepositoryImpl
import com.example.mvvm.postsfavorites.feature.posts.domain.repository.ReadPostsRepository
import com.example.mvvm.postsfavorites.feature.posts.domain.repository.WritePostsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostsProvidesModule {

    @Provides
    @Singleton
    fun providePostsApiService(retrofit: Retrofit): PostsApiService =
        retrofit.create(PostsApiService::class.java)

    @Provides
    @Singleton
    fun providePostsDatabase(
        @ApplicationContext context: Context,
    ): PostsDatabase = Room
        .databaseBuilder(context, PostsDatabase::class.java, "posts.db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun providePostDao(database: PostsDatabase): PostDao = database.postDao()
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class PostsBindsModule {

    @Binds
    @Singleton
    abstract fun bindReadPostsRepository(impl: PostsRepositoryImpl): ReadPostsRepository

    @Binds
    @Singleton
    abstract fun bindWritePostsRepository(impl: PostsRepositoryImpl): WritePostsRepository
}
