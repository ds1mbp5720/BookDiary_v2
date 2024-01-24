package com.example.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.room.entity.MyBookEntity
import com.example.domain.model.MyBookModel
import io.reactivex.Single

@Dao
interface MyBookDAO {
    @Query("SELECT * FROM BookInfo")
    fun getMyBookList(): Single<List<MyBookEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMyBook(book : MyBookEntity)
    @Update
    fun update(book: MyBookEntity)

    @Query("DELETE FROM BookInfo WHERE itemId = :bookId")
    fun delete(bookId: Long)
}