package com.example.data.mapper

import com.example.data.dto.BookData
import com.example.domain.model.BookModel

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
            //subInfo = data.subInfo
        )
    }
}
fun BookData.toDomain(): BookModel {
    return BookMapper.toDomain(this)
}