package com.example.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.room.entity.WishBookEntity
import io.reactivex.Single

@Dao
interface WishBookDao {
    @Query("SELECT * FROM WishBookInfo")
    fun getWishBookList(): Single<List<WishBookEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWishBook(book : WishBookEntity)
    @Query("DELETE FROM WishBookInfo WHERE itemId = :bookId")
    fun delete(bookId: Long)
}