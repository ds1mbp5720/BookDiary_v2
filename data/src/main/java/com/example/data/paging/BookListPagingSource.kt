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
    private val queryType: String = "",
    private val bookListDataSource: BookListDataSource
) : PagingSource<Int, BookModel>() {
    override fun getRefreshKey(state: PagingState<Int, BookModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookModel> {
        return try {
            Log.e("","페이징 체크 로드 $queryType / ${params.key} ")
            val currentPage = params.key ?: 1
            val bookList = bookListDataSource.getBookList(
                QueryType = queryType,
                start = currentPage
            ).toDomain()
            if(bookList.bookList.isNotEmpty()){
                Log.e("","페이징 체크1")
                LoadResult.Page(
                    data = bookList.bookList,
                    prevKey = if(currentPage == 1) null else currentPage - 1,
                    nextKey = if(bookList.bookList.isEmpty()) null else currentPage + 1
                )
            } else {
                Log.e("","페이징 체크2")
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }


        } catch (exception: IOException) {
            Log.e("","페이징 체크 에러1")
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Log.e("","페이징 체크 에러2")
            return LoadResult.Error(exception)
        }
    }
}