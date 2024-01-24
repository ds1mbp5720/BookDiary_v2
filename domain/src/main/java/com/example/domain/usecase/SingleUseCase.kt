package com.example.domain.usecase

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class SingleUseCase<T> {
    protected var lastDisposable: Disposable? = null
    protected val compositeDisposable = CompositeDisposable()

    internal abstract fun buildUseCaseSingle(): Single<T>

    fun execute(
        onSuccess: ((it:T) -> Unit),
        onError: ((t: Throwable) -> Unit),
        onFinished: () -> Unit = {}
    ) {
        lastDisposable?.let {
            if (!it.isDisposed) {
                compositeDisposable.clear()
            }
        }
        lastDisposable = buildUseCaseSingle()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate(onFinished)
            .subscribe(onSuccess, onError)

        lastDisposable?.let {
            compositeDisposable.add(it)
        }
    }
}