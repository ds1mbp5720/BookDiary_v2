package com.example.data.datasource

import com.example.data.BuildConfig
import com.example.data.dto.OffStoreListData
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 해당 책의 보유 매장 정보
 */
interface OffStoreDataSource {
    @GET("ItemOffStoreList.aspx")
    suspend fun getOffStoreInfo(
        @Query("ttbkey") ttbkey: String = BuildConfig.TTB_KEY,
        @Query("ItemId") itemId: String, // 리스트 종류
        @Query("ItemIdType") itemIdType: String,
        @Query("output") output: String = "js"
    ): OffStoreListData
}