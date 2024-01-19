package com.example.domain.di

import com.example.domain.repository.BookListRepository
import com.example.domain.repository.OffStoreRepository
import com.example.domain.usecase.BookListUseCase
import com.example.domain.usecase.BookListUseCaseImpl
import com.example.domain.usecase.OffStoreUseCase
import com.example.domain.usecase.OffStoreUseCaseImpl
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
    @Singleton
    @Provides
    fun provideOffStoreUseCase(repository: OffStoreRepository) : OffStoreUseCase {
        return OffStoreUseCaseImpl(repository)
    }
}