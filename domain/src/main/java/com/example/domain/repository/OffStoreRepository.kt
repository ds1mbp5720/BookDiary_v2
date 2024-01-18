package com.example.domain.repository

import com.example.domain.model.OffStoreListModel
import kotlinx.coroutines.flow.Flow

interface OffStoreRepository {
    fun getOffStoreInfo(): Flow<OffStoreListModel>
}