package com.example.domain.usecase

import com.example.domain.model.OffStoreListModel
import kotlinx.coroutines.flow.Flow

interface OffStoreUseCase {
    fun getOffStoreInfo(itemId: String): Flow<OffStoreListModel>
}