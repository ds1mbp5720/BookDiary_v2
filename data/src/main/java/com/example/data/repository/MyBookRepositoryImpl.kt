package com.example.data.repository

import com.example.data.mapper.MyBookEntityMapper
import com.example.data.mapper.toEntity
import com.example.data.room.AppDataBase
import com.example.domain.model.MyBookModel
import com.example.domain.repository.MyBookRepository
import io.reactivex.Single
import javax.inject.Inject

class MyBookRepositoryImpl @Inject constructor(
    private val database: AppDataBase,
): MyBookRepository{
    override fun getMyBookList(): Single<List<MyBookModel>> {
        return database.getMyBookDao().getMyBookList()
            .map {
                MyBookEntityMapper().map(it)
            }
    }


    override fun insertMyBook(book: MyBookModel) {
        database.getMyBookDao().insertMyBook(book = book.toEntity())
    }

    override fun update(book: MyBookModel) {
        database.getMyBookDao().update(book = book.toEntity())
    }

    override fun delete(bookId: Long) {
        database.getMyBookDao().delete(bookId = bookId)
    }

}