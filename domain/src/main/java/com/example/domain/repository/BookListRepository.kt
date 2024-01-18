package com.example.domain.repository

import com.example.domain.model.BookListModel
import kotlinx.coroutines.flow.Flow

interface BookListRepository {
    fun getBookList(): Flow<BookListModel>
    fun searchBookList(): Flow<BookListModel>
}