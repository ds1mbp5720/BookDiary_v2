package com.example.data.datasource

import retrofit2.http.GET
import retrofit2.http.Query

interface BookListDataSource {
    /**
     * ItemNewAll : 신간 전체 리스트
     * ItemNewSpecial : 주목할 만한 신간 리스트
     * ItemEditorChoice : 편집자 추천 리스트
     * (카테고리로만 조회 가능 - 국내도서/음반/외서만 지원)
     * Bestseller : 베스트셀러
     * BlogBest : 블로거 베스트셀러 (국내도서만 조회 가능)
     *
     */
    @GET("ItemList.aspx")
    suspend fun getBookList(
        @Query("ttbkey") ttbkey: String,
        @Query("QueryType") QueryType: String, // 리스트 종류
        @Query("output") output: String = "js",
        @Query("Version") Version: String = "20131101"
    )
}