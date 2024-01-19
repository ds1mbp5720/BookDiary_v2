package com.example.domain.usecase

import android.util.Log
import androidx.paging.PagingData
import com.example.domain.model.BookListModel
import com.example.domain.model.BookModel
import com.example.domain.repository.BookListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookListUseCaseImpl @Inject constructor(
    private val bookListRepository: BookListRepository
): BookListUseCase {
    override fun getBookList(queryType: String, start: Int): Flow<BookListModel> = flow{
        bookListRepository.getBookList(queryType, start).collect{
            emit(it)
        }
    }

    override fun getBookListPaging(queryType: String): Flow<PagingData<BookModel>> = flow{
        bookListRepository.getBookListPaging(queryType).collect{
            emit(it)
        }
    }

    override fun searchBookList(): Flow<BookListModel> = flow {
        bookListRepository.searchBookList().collect{
            emit(it)
        }
    }
}