package com.example.data.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.room.dao.MyBookDAO
import com.example.data.room.entity.MyBookEntity

@Database(entities = [MyBookEntity::class], version = 2, exportSchema = false)
abstract class MyBookDataBase: RoomDatabase() {
    abstract fun getMyBookDao(): MyBookDAO

    companion object {
        @Volatile
        private var INSTANCE: MyBookDataBase? = null

        private fun buildDataBase(context: Context): MyBookDataBase =
            Room.databaseBuilder(
                context.applicationContext,
                MyBookDataBase::class.java,
                "record-book"
            ).fallbackToDestructiveMigration()
                .build()

        fun getInstance(context: Context): MyBookDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDataBase(context).also { INSTANCE = it }
            }
    }
}