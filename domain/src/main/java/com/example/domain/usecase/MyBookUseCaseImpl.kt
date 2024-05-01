package com.example.domain.usecase

import android.annotation.SuppressLint
import com.example.domain.model.MyBookModel
import com.example.domain.repository.MyBookRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MyBookUseCaseImpl @Inject constructor(
    private val myBookRepository: MyBookRepository
) : MyBookUseCase {
    override fun getMyBookList(): Single<List<MyBookModel>> {
        return myBookRepository.getMyBookList()
    }

    override fun insertMyBook(book: MyBookModel) {
        myBookRepository.insertMyBook(book)
    }

    override fun update(book: MyBookModel) {
        myBookRepository.update(book)
    }

    override fun delete(bookId: Long) {
        myBookRepository.delete(bookId)
    }

    override fun findMyBook(bookId: Long): Single<MyBookModel> {
        return myBookRepository.findMyBook(bookId = bookId)
    }

    @SuppressLint("CheckResult")
    override fun execute(
        onSuccess: (t: List<MyBookModel>) -> Unit,
        onError: (t: Throwable) -> Unit,
        onFinished: () -> Unit
    ) {
        getMyBookList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate(onFinished)
            .subscribe(onSuccess, onError)
    }
}