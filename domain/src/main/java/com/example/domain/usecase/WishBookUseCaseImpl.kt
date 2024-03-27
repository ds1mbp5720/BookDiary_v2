package com.example.domain.usecase

import android.annotation.SuppressLint
import com.example.domain.model.WishBookModel
import com.example.domain.repository.WishBookRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WishBookUseCaseImpl @Inject constructor(
    private val wishBookRepository: WishBookRepository
) : WishBookUseCase {
    override fun getWishBookList(): Single<List<WishBookModel>> {
        return wishBookRepository.getWishBookList()
    }

    override fun insertWishBook(book: WishBookModel) {
        wishBookRepository.insertWishBook(book = book)
    }

    override fun delete(bookId: Long) {
        wishBookRepository.delete(bookId = bookId)
    }

    @SuppressLint("CheckResult")
    override fun execute(
        onSuccess: (t: List<WishBookModel>) -> Unit,
        onError: (t: Throwable) -> Unit,
        onFinished: () -> Unit
    ) {
        getWishBookList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate(onFinished)
            .subscribe(onSuccess, onError)
    }
}