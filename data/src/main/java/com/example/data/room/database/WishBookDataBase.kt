package com.example.data.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.room.dao.WishBookDao
import com.example.data.room.entity.WishBookEntity

@Database(entities = [WishBookEntity::class], version = 1, exportSchema = false)
abstract class WishBookDataBase: RoomDatabase() {
    abstract fun getWishBookDao(): WishBookDao

    companion object {
        @Volatile
        private var INSTANCE: WishBookDataBase? = null

        private fun buildDataBase(context: Context): WishBookDataBase =
            Room.databaseBuilder(
                context.applicationContext,
                WishBookDataBase::class.java,
                "wish-book"
            ).fallbackToDestructiveMigration()
                .build()

        fun getInstance(context: Context): WishBookDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDataBase(context).also { INSTANCE = it }
            }
    }
}