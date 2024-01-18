package com.example.data.datasource

import com.example.data.BuildConfig
import com.example.data.dto.OffStoreListData
import retrofit2.http.GET
import retrofit2.http.Query

interface OffStoreDataSource {
    @GET("ItemOffStoreList.aspx")
    suspend fun getOffStoreInfo(
        @Query("ttbkey") ttbkey: String = BuildConfig.TTB_KEY,
        @Query("ItemId") ItemId: String, // 리스트 종류
        @Query("ItemIdType") ItemIdType: String,
        @Query("output") output: String = "js"
    ): OffStoreListData
}