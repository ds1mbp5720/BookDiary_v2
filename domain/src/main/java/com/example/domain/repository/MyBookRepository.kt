package com.example.domain.repository

import com.example.domain.model.MyBookModel
import io.reactivex.Single

interface MyBookRepository {

    fun getMyBookList(): Single<List<MyBookModel>>

    fun insertMyBook(book: MyBookModel)

    fun update(book: MyBookModel)

    fun delete(bookId: Long)
}