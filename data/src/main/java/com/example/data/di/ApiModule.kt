package com.example.data.di

import com.example.data.datasource.BookListDataSource
import com.example.data.datasource.SearchDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideBookDataSource(retrofit: Retrofit): BookListDataSource {
        return retrofit.create(BookListDataSource::class.java)
    }
    @Provides
    @Singleton
    fun provideSearchBookDataSource(retrofit: Retrofit): SearchDataSource {
        return retrofit.create(SearchDataSource::class.java)
    }

}