package com.example.domain.di

import com.example.domain.repository.BookListRepository
import com.example.domain.usecase.BookListUseCase
import com.example.domain.usecase.BookListUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideBookListUseCase(repository: BookListRepository) : BookListUseCase {
        return BookListUseCaseImpl(repository)
    }
}