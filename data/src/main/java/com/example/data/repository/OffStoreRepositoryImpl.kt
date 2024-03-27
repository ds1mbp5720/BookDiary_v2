package com.example.data.repository

import com.example.data.datasource.OffStoreDataSource
import com.example.data.mapper.toDomain
import com.example.domain.model.OffStoreListModel
import com.example.domain.repository.OffStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import retrofit2.HttpException
import javax.inject.Inject

class OffStoreRepositoryImpl @Inject constructor(
    private val offStoreDataSource: OffStoreDataSource
) : OffStoreRepository {
    override fun getOffStoreInfo(itemId: String): Flow<OffStoreListModel> = flow {
        val response = offStoreDataSource.getOffStoreInfo(
            itemId = itemId,
            itemIdType = "itemId",
        )
        emit(response.toDomain())
    }.retry {
        it is IllegalAccessException
    }.catch { e ->
        if (e is HttpException)
            throw e
    }
}