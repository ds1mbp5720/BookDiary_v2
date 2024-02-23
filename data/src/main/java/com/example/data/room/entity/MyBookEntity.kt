package com.example.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BookInfo")
data class MyBookEntity(
    @PrimaryKey
    val itemId: Long,
    val imageUrl: String, // 표지 이미지
    val title: String, // 상품명
    val author: String, // 저자
    val link: String?, // 상품 링크 Url
    val myReview: String?, // 독후감
    val period: String // 독서기간 //todo 형 변환 고려
)
