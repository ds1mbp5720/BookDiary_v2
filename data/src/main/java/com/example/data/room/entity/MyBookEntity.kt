package com.example.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BookInfo")
data class MyBookEntity(
    @PrimaryKey
    val itemId: Long,
    val title: String, // 상품명
    val link: String?, // 상품 링크 Url
    val myReview: String? // 독후감
)
