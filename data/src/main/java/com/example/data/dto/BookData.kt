package com.example.data.dto

import com.google.gson.annotations.SerializedName

data class BookData (
    @SerializedName("itemId")
    val itemId: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("link")
    val link: String?,
    @SerializedName("author")
    val author: String?,
    @SerializedName("pubDate")
    val pubDate: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("isbn")
    val isbn: String?,
    @SerializedName("isbn13")
    val isbn13: String?,
    @SerializedName("priceSales")
    val priceSales: String?,
    @SerializedName("priceStandard")
    val priceStandard: String?,
    @SerializedName("mallType")
    val mallType: String?,
    @SerializedName("stockStatus")
    val stockStatus: String?,
    @SerializedName("mileage")
    val mileage: String?,
    @SerializedName("cover")
    val cover: String?,
    @SerializedName("categoryId")
    val categoryId: String?,
    @SerializedName("categoryName")
    val categoryName: String?,
    @SerializedName("publisher")
    val publisher: String?,
    @SerializedName("salesPoint")
    val salesPoint: String?,
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("fixedPrice")
    val fixedPrice: Boolean?,
    @SerializedName("customerReviewRank")
    val customerReviewRank: String?,
    /*@SerializedName("subInfo")
    val subInfo: String?*/
)