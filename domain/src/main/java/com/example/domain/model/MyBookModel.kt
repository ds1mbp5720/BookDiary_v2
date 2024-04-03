package com.example.domain.model

data class MyBookModel(
    val itemId: Long,
    val imageUrl: String, // 표지 이미지
    val title: String, // 상품명
    val author: String, // 저자
    val link: String?, // 상품 링크 Url
    val myReview: String?, // 독후감
    val period: String // 독서 기간
)
