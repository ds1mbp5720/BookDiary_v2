package com.example.domain.usecase

import com.example.domain.model.WishBookModel
import io.reactivex.Single

interface WishBookUseCase {
    fun getWishBookList(): Single<List<WishBookModel>>
    fun insertWishBook(book: WishBookModel)
    fun delete(bookId: Long)
    fun execute(
        onSuccess: ((t: List<WishBookModel>) -> Unit),
        onError: ((t: Throwable) -> Unit),
        onFinished: () -> Unit = {}
    )
}