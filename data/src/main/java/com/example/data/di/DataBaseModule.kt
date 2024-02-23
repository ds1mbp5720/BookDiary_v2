package com.example.data.di

import android.content.Context
import com.example.data.room.dao.MyBookDAO
import com.example.data.room.dao.WishBookDao
import com.example.data.room.database.MyBookDataBase
import com.example.data.room.database.WishBookDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataBaseModule {

    @Provides
    @Singleton
    fun provideMyBookDataBase(
        @ApplicationContext context: Context
    ): MyBookDataBase = MyBookDataBase.getInstance(context)

    @Provides
    @Singleton
    fun provideMyBookDao(myBookDataBase: MyBookDataBase): MyBookDAO = myBookDataBase.getMyBookDao()

    @Provides
    @Singleton
    fun provideWishBookDataBase(
        @ApplicationContext context: Context
    ): WishBookDataBase = WishBookDataBase.getInstance(context)

    @Provides
    @Singleton
    fun provideWishBookDao(wishBookDataBase: WishBookDataBase): WishBookDao = wishBookDataBase.getWishBookDao()
}