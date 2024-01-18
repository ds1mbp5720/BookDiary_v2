package com.example.data.di

import com.example.data.datasource.BookListDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideBookDataSource(retrofit: Retrofit): BookListDataSource {
        return retrofit.create(BookListDataSource::class.java)
    }
}