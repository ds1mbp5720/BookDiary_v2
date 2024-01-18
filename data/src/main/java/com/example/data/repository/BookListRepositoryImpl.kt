package com.example.data.repository

import android.util.Log
import com.example.data.datasource.BookListDataSource
import com.example.data.mapper.toDomain
import com.example.domain.model.BookListModel
import com.example.domain.repository.BookListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import retrofit2.HttpException
import javax.inject.Inject

class BookListRepositoryImpl @Inject constructor(
    private val bookListDataSource: BookListDataSource
): BookListRepository {
    override fun getBookList(): Flow<BookListModel> = flow {
        val response = bookListDataSource.getBookList(
            QueryType = "ItemNewAll",
            start = 1,
        )
        emit(response.toDomain())
    }.retry {
        it is IllegalAccessException
    }.catch {e->
        if(e is HttpException)
            throw e
    }

    override fun searchBookList(): Flow<BookListModel> = flow {
        val response = bookListDataSource.searchBookList(
            Query = "셜록홈즈",
            start = 1,
        )
        emit(response.toDomain())
    }.retry {
        it is IllegalAccessException
    }.catch {e->
        if(e is HttpException)
            throw e
    }
}