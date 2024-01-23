package com.example.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MyBookDAO {
    @Query("SELECT * FROM BookInfo")
    fun getMyBookList(): List<MyBookData>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMyBook(book : MyBookData)
    @Update
    fun update(book: MyBookData)

    @Query("DELETE FROM BookInfo WHERE itemId = :bookId")
    fun delete(bookId: Long)
}