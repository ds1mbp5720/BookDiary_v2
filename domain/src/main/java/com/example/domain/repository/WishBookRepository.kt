package com.example.domain.repository

import com.example.domain.model.WishBookModel
import io.reactivex.Single

interface WishBookRepository {
    fun getWishBookList(): Single<List<WishBookModel>>
    fun insertWishBook(book: WishBookModel)
    fun delete(bookId: Long)
}