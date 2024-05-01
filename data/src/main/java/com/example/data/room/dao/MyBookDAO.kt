package com.example.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.room.entity.MyBookEntity
import io.reactivex.Single

/**
 * 저장한 책 관리 Dao
 */
@Dao
interface MyBookDAO {
    @Query("SELECT * FROM BookInfo")
    fun getMyBookList(): Single<List<MyBookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMyBook(book: MyBookEntity)

    @Query("SELECT * FROM BookInfo WHERE itemId = :bookId")
    fun findMyBook(bookId: Long): Single<MyBookEntity>

    @Update
    fun update(book: MyBookEntity)

    @Query("DELETE FROM BookInfo WHERE itemId = :bookId")
    fun delete(bookId: Long)
}