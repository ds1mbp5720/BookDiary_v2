package com.example.data.mapper

import com.example.data.room.entity.MyBookEntity
import com.example.domain.model.MyBookModel

fun MyBookModel.toEntity() = MyBookEntity(
    itemId = itemId,
    title = title,
    link = link,
    myReview = myReview
)

interface Mapper<I,O> { fun map(input: I): O }
interface ListMapper<I, O> : Mapper<List<I>, List<O>>
class MyBookEntityMapper : ListMapper<MyBookEntity,MyBookModel>{
    override fun map(input: List<MyBookEntity>): List<MyBookModel> {
        return input.map {
            MyBookModel(it.itemId, it.title, it.link, it.myReview)
        }
    }

}