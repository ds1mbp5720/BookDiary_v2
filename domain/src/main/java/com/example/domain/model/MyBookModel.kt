package com.example.domain.model

data class MyBookModel(
    val itemId: Long,
    val title: String, // 상품명
    val link: String?, // 상품 링크 Url
    val myReview: String? // 독후감
)
