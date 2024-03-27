package com.example.data.mapper

import com.example.data.dto.BookListData
import com.example.domain.model.BaseModel
import com.example.domain.model.BookListModel
import com.example.domain.model.BookModel

object BookListMapper {
    fun toDomain(data: BookListData): BookListModel {
        val bookList = mutableListOf<BookModel>()
        data.item?.forEach {
            bookList.add(it.toDomain())
        }
        return BookListModel(
            baseModel = BaseModel(
                title = data.title,
                link = data.link,
                logo = data.logo,
                pubDate = data.pubDate,
                totalResults = data.totalResults,
                startIndex = data.startIndex,
                itemsPerPage = data.itemsPerPage,
                query = data.query,
                version = data.version,
                searchCategoryId = data.searchCategoryId,
                searchCategoryName = data.searchCategoryName
            ),
            bookList = bookList
        )
    }
}

fun BookListData.toDomain(): BookListModel {
    return BookListMapper.toDomain(this)
}