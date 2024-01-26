package com.example.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.datasource.BookListDataSource
import com.example.data.mapper.toDomain
import com.example.domain.model.BookModel
import okio.IOException
import retrofit2.HttpException

class BookListPagingSource(
    private val query: String = "",
    private val bookListDataSource: BookListDataSource,
    private val apiType: String
) : PagingSource<Int, BookModel>() {
    override fun getRefreshKey(state: PagingState<Int, BookModel>): Int? {
        return state.anchorPosition/*?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }*/
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookModel> {
        return try {
            val currentPage = params.key ?: 1
            val bookList = if(apiType == "Search") {
                bookListDataSource.searchBookList(
                    Query = query,
                    start = currentPage
                ).toDomain()
            } else {
                bookListDataSource.getBookList(
                    QueryType = query,
                    start = currentPage
                ).toDomain()
            }
            if(bookList.bookList.isNotEmpty()){
                LoadResult.Page(
                    data = bookList.bookList,
                    prevKey = if(currentPage == 1) null else currentPage - 1,
                    nextKey = if(bookList.bookList.isEmpty()) null else currentPage + 1
                )
            } else {
                Log.e("","페이징 결과 null")
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}