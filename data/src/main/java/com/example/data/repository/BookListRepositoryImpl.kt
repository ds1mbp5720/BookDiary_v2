package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.datasource.BookListDataSource
import com.example.data.mapper.toDomain
import com.example.data.paging.BookListPagingSource
import com.example.domain.model.BookListModel
import com.example.domain.model.BookModel
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
    override fun getBookList(queryType: String, start: Int): Flow<BookListModel> = flow {
        val response = bookListDataSource.getBookList(
            QueryType = queryType,
            start = start,
        )
        emit(response.toDomain())
    }.retry {
        it is IllegalAccessException
    }.catch {e->
        if(e is HttpException)
            throw e
    }
    override fun getBookListPaging(queryType: String, size: Int): Flow<PagingData<BookModel>> {
        return Pager(
            config = PagingConfig(pageSize = 1, prefetchDistance = size, maxSize = 300),
            pagingSourceFactory = {
                BookListPagingSource(queryType,bookListDataSource,"Normal")
            }
        ).flow
    }

    override fun searchBookList(query: String): Flow<BookListModel> = flow {
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
    override fun getSearchBookListPaging(query: String, size: Int): Flow<PagingData<BookModel>> {
        return Pager(
            config = PagingConfig(pageSize = 1, prefetchDistance = size, maxSize = 300),
            pagingSourceFactory = {
                BookListPagingSource(query,bookListDataSource,"Search")
            }
        ).flow
    }


    override fun getBookDetail(itemId: Long): Flow<BookListModel> = flow {
        val response = bookListDataSource.getBookDetail(
            ItemId = itemId
        )
        emit(response.toDomain())
    }.retry {
        it is IllegalAccessException
    }.catch {e->
        if(e is HttpException)
            throw e
    }
}