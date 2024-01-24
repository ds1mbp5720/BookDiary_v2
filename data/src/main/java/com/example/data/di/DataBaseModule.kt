package com.example.data.di

import android.content.Context
import com.example.data.room.AppDataBase
import com.example.data.room.dao.MyBookDAO
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
    fun provideAppDataBase(
        @ApplicationContext context: Context
    ): AppDataBase = AppDataBase.getInstance(context)

    @Provides
    @Singleton
    fun provideMyBookDao(appDataBase: AppDataBase): MyBookDAO = appDataBase.getMyBookDao()

}