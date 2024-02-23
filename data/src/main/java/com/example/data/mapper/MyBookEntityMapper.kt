package com.example.data.mapper

import com.example.data.room.entity.MyBookEntity
import com.example.domain.model.MyBookModel

fun MyBookModel.toEntity() = MyBookEntity(
    itemId = itemId,
    imageUrl = imageUrl,
    title = title,
    author = author,
    link = link,
    myReview = myReview,
    period = period
)

interface Mapper<I,O> { fun map(input: I): O }
interface ListMapper<I, O> : Mapper<List<I>, List<O>>
class MyBookEntityMapper : ListMapper<MyBookEntity, MyBookModel>{
    override fun map(input: List<MyBookEntity>): List<MyBookModel> {
        return input.map {
            MyBookModel(it.itemId, it.imageUrl, it.title, it.author, it.link, it.myReview, it.period)
        }
    }
}