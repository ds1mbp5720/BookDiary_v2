package com.example.data.mapper

import com.example.data.dto.BookData
import com.example.data.dto.RatingInfo
import com.example.data.dto.SubInfo
import com.example.domain.model.BookModel
import com.example.domain.model.RatingInfoModel
import com.example.domain.model.SubInfoModel

object BookMapper {
    fun toDomain(data: BookData): BookModel {
        return BookModel(
            itemId = data.itemId,
            title = data.title,
            link = data.link,
            author = data.author,
            pubDate =  data.pubDate,
            description = data.description,
            isbn = data.isbn,
            isbn13 = data.isbn13,
            priceSales = data.priceSales,
            priceStandard = data.priceStandard,
            mallType = data.mallType,
            stockStatus = data.stockStatus,
            mileage = data.mileage,
            cover = data.cover,
            categoryId = data.categoryId,
            categoryName = data.categoryName,
            publisher = data.publisher,
            salesPoint = data.salesPoint,
            adult = data.adult,
            fixedPrice = data.fixedPrice,
            customerReviewRank = data.customerReviewRank,
            subInfo = data.subInfo.toDomain()
        )
    }
}
fun BookData.toDomain(): BookModel {
    return BookMapper.toDomain(this)
}

object SubInfoMapper{
    fun toDomain(data: SubInfo): SubInfoModel{
        return SubInfoModel(
            subTitle = data.subTitle,
            originalTitle = data.originalTitle,
            itemPage = data.itemPage,
            subbarcode = data.subbarcode,
            cardReviewImgList = data.cardReviewImgList ?: listOf(""),
            ratingInfo = data.ratingInfo?.toDomain(),
            bestSellerRank = data.bestSellerRank
        )
    }
}

fun SubInfo.toDomain(): SubInfoModel{
    return SubInfoMapper.toDomain(this)
}

object RatingInfoMapper{
    fun toDomain(data: RatingInfo): RatingInfoModel {
        return RatingInfoModel(
            ratingScore = data.ratingScore,
            ratingCount = data.ratingCount,
            commentReviewCount = data.commentReviewCount,
            myReviewCount = data.myReviewCount
        )
    }
}

fun RatingInfo.toDomain(): RatingInfoModel{
    return RatingInfoMapper.toDomain(this)
}