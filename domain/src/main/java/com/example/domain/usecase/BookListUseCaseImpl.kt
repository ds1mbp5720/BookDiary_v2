package com.example.domain.usecase

import android.util.Log
import com.example.domain.model.BookListModel
import com.example.domain.repository.BookListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookListUseCaseImpl @Inject constructor(
    private val bookListRepository: BookListRepository
): BookListUseCase {
    override fun getBookList(): Flow<BookListModel> = flow{
        bookListRepository.getBookList().collect{
            emit(it)
        }
    }
    override fun searchBookList(): Flow<BookListModel> = flow {
        bookListRepository.searchBookList().collect{
            emit(it)
        }
    }
}