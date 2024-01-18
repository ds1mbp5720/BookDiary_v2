package com.example.domain.model

data class BookModel(
    val itemId: String?,
    val title: String?,
    val link: String?,
    val author: String?,
    val pubDate: String?,
    val description: String?,
    val isbn: String?,
    val isbn13: String?,
    val priceSales: String?,
    val priceStandard: String?,
    val mallType: String?,
    val stockStatus: String?,
    val mileage: String?,
    val cover: String?,
    val categoryId: String?,
    val categoryName: String?,
    val publisher: String?,
    val salesPoint: String?,
    val adult: Boolean?,
    val fixedPrice: Boolean?,
    val customerReviewRank: String?,
    //val subInfo: String?
)
