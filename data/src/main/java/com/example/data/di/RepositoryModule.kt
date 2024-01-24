package com.example.data.di

import com.example.data.repository.BookListRepositoryImpl
import com.example.data.repository.MyBookRepositoryImpl
import com.example.data.repository.OffStoreRepositoryImpl
import com.example.domain.repository.BookListRepository
import com.example.domain.repository.MyBookRepository
import com.example.domain.repository.OffStoreRepository
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

    @Binds
    abstract fun bindsOffStoreRepository(
        offStoreRepositoryImpl: OffStoreRepositoryImpl
    ): OffStoreRepository

    @Binds
    abstract fun bindsMyBookRepository(
        myBookRepositoryImpl: MyBookRepositoryImpl
    ): MyBookRepository
}