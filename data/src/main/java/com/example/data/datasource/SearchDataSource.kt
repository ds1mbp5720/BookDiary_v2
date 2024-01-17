package com.example.data.datasource

import retrofit2.http.GET
import retrofit2.http.Query

interface SearchDataSource {
    @GET("ItemSearch.aspx")
    suspend fun searchBook(
        @Query("ttbkey") ttbkey: String,
        @Query("Query") Query: String, // 검색어
        @Query("output") output: String = "js",
        @Query("Version") Version: String = "20131101"
    )
}