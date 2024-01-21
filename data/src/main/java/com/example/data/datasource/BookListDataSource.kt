package com.example.data.datasource

import com.example.data.BuildConfig
import com.example.data.dto.BookData
import com.example.data.dto.BookListData
import retrofit2.http.GET
import retrofit2.http.Query

interface BookListDataSource {
    /**
     * ItemNewAll : 신간 전체 리스트
     * ItemNewSpecial : 주목할 만한 신간 리스트
     * Bestseller : 베스트셀러
     * BlogBest : 블로거 베스트셀러 (국내도서만 조회 가능)
     *
     */
    @GET("ItemList.aspx")
    suspend fun getBookList(
        @Query("ttbkey") ttbkey: String = BuildConfig.TTB_KEY,
        @Query("QueryType") QueryType: String, // 리스트 종류
        @Query("start") start: Int,
        @Query("SearchTarget") SearchTarget:String = "Book",
        @Query("output") output: String = "js",
        @Query("Version") Version: String = "20131101"
    ): BookListData

    @GET("ItemSearch.aspx")
    suspend fun searchBookList(
        @Query("ttbkey") ttbkey: String = BuildConfig.TTB_KEY,
        @Query("Query") Query: String, // 검색어
        @Query("start") start: Int,
        @Query("output") output: String = "js",
        @Query("Version") Version: String = "20131101"
    ): BookListData

    @GET("ItemLookUp.aspx")
    suspend fun getBookDetail(
        @Query("ttbkey") ttbkey: String = BuildConfig.TTB_KEY,
        @Query("ItemId") ItemId: Long,
        @Query("itemIdType") itemIdType: String = "ItemId",
        @Query("output") output: String = "js",
        @Query("Version") Version: String = "20131101"
    ): BookData
}