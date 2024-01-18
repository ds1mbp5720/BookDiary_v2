package com.example.domain.usecase

import com.example.domain.model.BookListModel
import kotlinx.coroutines.flow.Flow

interface BookListUseCase {
    fun getBookList(): Flow<BookListModel>
    fun searchBookList(): Flow<BookListModel>
}