package com.example.data.mapper

import com.example.data.room.entity.WishBookEntity
import com.example.domain.model.WishBookModel

fun WishBookModel.toEntity() = WishBookEntity(
    itemId = itemId,
    imageUrl = imageUrl,
    title = title
)

class WishBookEntityMapper: ListMapper<WishBookEntity, WishBookModel>{
    override fun map(input: List<WishBookEntity>): List<WishBookModel> {
        return input.map {
            WishBookModel(it.itemId, it.imageUrl, it.title)
        }
    }
}