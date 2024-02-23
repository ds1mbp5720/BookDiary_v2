package com.example.domain.di

import com.example.domain.repository.BookListRepository
import com.example.domain.repository.MyBookRepository
import com.example.domain.repository.OffStoreRepository
import com.example.domain.repository.WishBookRepository
import com.example.domain.usecase.BookListUseCase
import com.example.domain.usecase.BookListUseCaseImpl
import com.example.domain.usecase.MyBookUseCase
import com.example.domain.usecase.MyBookUseCaseImpl
import com.example.domain.usecase.OffStoreUseCase
import com.example.domain.usecase.OffStoreUseCaseImpl
import com.example.domain.usecase.WishBookUseCase
import com.example.domain.usecase.WishBookUseCaseImpl
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

    @Singleton
    @Provides
    fun provideMyBookUseCase(repository: MyBookRepository) : MyBookUseCase {
        return MyBookUseCaseImpl(repository)
    }

    @Singleton
    @Provides
    fun provideWishBookUseCase(repository: WishBookRepository) : WishBookUseCase {
        return WishBookUseCaseImpl(repository)
    }
}