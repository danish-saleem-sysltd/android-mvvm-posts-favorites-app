package com.example.mvvm.postsfavorites.feature.auth.di

import com.example.mvvm.postsfavorites.feature.auth.data.repository.AuthRepositoryImpl
import com.example.mvvm.postsfavorites.feature.auth.data.usecase.ValidateEmailUseCaseImpl
import com.example.mvvm.postsfavorites.feature.auth.data.usecase.ValidatePasswordUseCaseImpl
import com.example.mvvm.postsfavorites.feature.auth.domain.repository.ReadAuthRepository
import com.example.mvvm.postsfavorites.feature.auth.domain.repository.WriteAuthRepository
import com.example.mvvm.postsfavorites.feature.auth.domain.usecase.ValidateEmailUseCase
import com.example.mvvm.postsfavorites.feature.auth.domain.usecase.ValidatePasswordUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindReadAuthRepository(impl: AuthRepositoryImpl): ReadAuthRepository

    @Binds
    @Singleton
    abstract fun bindWriteAuthRepository(impl: AuthRepositoryImpl): WriteAuthRepository

    @Binds
    abstract fun bindValidateEmailUseCase(impl: ValidateEmailUseCaseImpl): ValidateEmailUseCase

    @Binds
    abstract fun bindValidatePasswordUseCase(impl: ValidatePasswordUseCaseImpl): ValidatePasswordUseCase
}
