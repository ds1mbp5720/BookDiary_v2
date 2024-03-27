package com.example.data.dto

import com.google.gson.annotations.SerializedName

/**
 * BookData 의 추가 상세 정보
 * 일부 정보는 알라딘 측에 추가 요청 및 인증이 필요하여 사용 가능 변수만 추가 상태
 */
data class SubInfo(
    @SerializedName("subTitle")
    val subTitle: String?, //부제
    @SerializedName("originalTitle")
    val originalTitle: String?, //원제
    @SerializedName("itemPage")
    val itemPage: String?, //상품 쪽수
    @SerializedName("subbarcode")
    val subbarcode: String?, //부가기호
    @SerializedName("cardReviewImgList")
    val cardReviewImgList : List<String>, //카드리뷰 이미지 경로
    @SerializedName("ratingInfo")
    val ratingInfo: RatingInfo?, //상품의 리뷰, 평점관련 개수
    @SerializedName("bestSellerRank")
    val bestSellerRank: String?, //베스트셀러 순위
)

data class RatingInfo(
    @SerializedName("ratingScore")
    val ratingScore: String?,
    @SerializedName("ratingCount")
    val ratingCount: String?,
    @SerializedName("commentReviewCount")
    val commentReviewCount: String?,
    @SerializedName("myReviewCount")
    val myReviewCount: String?,
)
