package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.model.BookListModel
import com.example.domain.model.BookModel
import kotlinx.coroutines.flow.Flow

interface BookListRepository {
    fun getBookList(queryType: String, start: Int): Flow<BookListModel>
    fun getBookListPaging(queryType: String, size: Int): Flow<PagingData<BookModel>>
    fun searchBookList(): Flow<BookListModel>
    fun getBookDetail(itemId: Long): Flow<BookListModel>
}