package com.example.data.di

import com.example.data.repository.BookListRepositoryImpl
import com.example.domain.repository.BookListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsBookRepository(
        bookListRepositoryImpl: BookListRepositoryImpl
    ): BookListRepository
}