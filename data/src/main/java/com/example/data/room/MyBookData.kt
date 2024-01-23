package com.example.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.dto.SubInfo
import com.google.gson.annotations.SerializedName

@Entity(tableName = "BookInfo")
data class MyBookData(
    @PrimaryKey
    val itemId: Long,
    @ColumnInfo
    val title: String, // 상품명
    @ColumnInfo
    val link: String?, // 상품 링크 Url
    @ColumnInfo
    val author: String?, // 저자
    @ColumnInfo
    val pubDate: String?, // 출간일(출시일)
    @ColumnInfo
    val description: String?, // 상품설명(요약)
    @ColumnInfo
    val priceSales: String?, //판매가
    @ColumnInfo
    val priceStandard: String?, //정가
    @ColumnInfo
    val stockStatus: String?, //재고상태
    @ColumnInfo
    val cover: String?, //커버(표지)
    @ColumnInfo
    val categoryName: String?,
    @ColumnInfo
    val publisher: String?,
    @ColumnInfo
    val customerReviewRank: String?, //회원 리뷰 평점
)
