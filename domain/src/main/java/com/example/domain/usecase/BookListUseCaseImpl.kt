package com.example.domain.usecase

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

    override fun getBookListPaging(queryType: String, size: Int): Flow<PagingData<BookModel>> = flow{
        bookListRepository.getBookListPaging(queryType, size).collect{
            emit(it)
        }
    }

    override fun searchBookList(): Flow<BookListModel> = flow {
        bookListRepository.searchBookList().collect{
            emit(it)
        }
    }

    override fun getBookDetail(itemId: Long): Flow<BookModel> = flow {
        bookListRepository.getBookDetail(itemId).collect{
            emit(it)
        }
    }
}