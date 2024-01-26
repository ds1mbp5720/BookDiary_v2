package com.example.domain.usecase

import androidx.paging.PagingData
import com.example.domain.model.BookListModel
import com.example.domain.model.BookModel
import kotlinx.coroutines.flow.Flow

interface BookListUseCase {
    fun getBookList(queryType: String, start: Int): Flow<BookListModel>
    fun getBookListPaging(queryType: String, size: Int): Flow<PagingData<BookModel>>
    fun searchBookList(query: String): Flow<BookListModel>
    fun getSearchBookListPaging(query: String, size: Int) : Flow<PagingData<BookModel>>
    fun getBookDetail(itemId: Long): Flow<BookListModel>
}