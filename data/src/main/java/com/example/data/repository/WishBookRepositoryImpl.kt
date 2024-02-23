package com.example.data.repository

import com.example.data.mapper.WishBookEntityMapper
import com.example.data.mapper.toEntity
import com.example.data.room.database.WishBookDataBase
import com.example.domain.model.WishBookModel
import com.example.domain.repository.WishBookRepository
import io.reactivex.Single
import javax.inject.Inject

class WishBookRepositoryImpl @Inject constructor(
    private val database: WishBookDataBase
):WishBookRepository{
    override fun getWishBookList(): Single<List<WishBookModel>> {
        return database.getWishBookDao().getWishBookList()
            .map {
                WishBookEntityMapper().map(it)
            }
    }

    override fun insertWishBook(book: WishBookModel) {
        database.getWishBookDao().insertWishBook(book = book.toEntity())
    }

    override fun delete(bookId: Long) {
        database.getWishBookDao().delete(bookId = bookId)
    }

}