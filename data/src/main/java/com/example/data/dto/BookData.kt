package com.example.data.dto

import com.google.gson.annotations.SerializedName

data class BookData (
    @SerializedName("itemId")
    val itemId: String,
    @SerializedName("title")
    val title: String, // 상품명
    @SerializedName("link")
    val link: String?, // 상품 링크 Url
    @SerializedName("author")
    val author: String?, // 저자
    @SerializedName("pubDate")
    val pubDate: String?, // 출간일(출시일)
    @SerializedName("description")
    val description: String?, // 상품설명(요약)
    @SerializedName("isbn")
    val isbn: String?, //도서번호
    @SerializedName("isbn13")
    val isbn13: String?, //도서번호 13자리
    @SerializedName("priceSales")
    val priceSales: String?, //판매가
    @SerializedName("priceStandard")
    val priceStandard: String?, //정가
    @SerializedName("mallType")
    val mallType: String?, //상품 mall 타입(국내도서: BOOK, 외서:FOREIGN 전자책: EBOOK)
    @SerializedName("stockStatus")
    val stockStatus: String?, //재고상태
    @SerializedName("mileage")
    val mileage: String?, //마일리지
    @SerializedName("cover")
    val cover: String?, //커버(표지)
    @SerializedName("categoryId")
    val categoryId: String?, //
    @SerializedName("categoryName")
    val categoryName: String?,
    @SerializedName("publisher")
    val publisher: String?,
    @SerializedName("salesPoint")
    val salesPoint: String?, // 판매지수
    @SerializedName("adult")
    val adult: Boolean?, // 성인 등급 여부
    @SerializedName("fixedPrice")
    val fixedPrice: Boolean?, //정가제 여부
    @SerializedName("customerReviewRank")
    val customerReviewRank: String?, //회원 리뷰 평점
    @SerializedName("subInfo")
    val subInfo: SubInfo
)