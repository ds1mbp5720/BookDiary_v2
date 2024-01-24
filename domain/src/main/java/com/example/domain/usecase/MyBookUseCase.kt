package com.example.domain.usecase

import com.example.domain.model.MyBookModel
import io.reactivex.Single

interface MyBookUseCase {
    fun getMyBookList() : Single<List<MyBookModel>>
    fun insertMyBook(book : MyBookModel)
    fun update(book: MyBookModel)
    fun delete(bookId: Long)
    fun execute(
        onSuccess: ((t: List<MyBookModel>) -> Unit),
        onError: ((t: Throwable) -> Unit),
        onFinished: () -> Unit = {}
    )
}