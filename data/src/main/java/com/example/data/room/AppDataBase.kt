package com.example.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.room.dao.MyBookDAO
import com.example.data.room.entity.MyBookEntity

@Database(entities = [MyBookEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {
    abstract fun getMyBookDao(): MyBookDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        private fun buildDataBase(context: Context): AppDataBase =
            Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                "record-book"
            ).build()

        fun getInstance(context: Context): AppDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDataBase(context).also { INSTANCE = it }
            }
    }
}