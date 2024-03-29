package com.example.domain.usecase

import com.example.domain.model.OffStoreListModel
import com.example.domain.repository.OffStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OffStoreUseCaseImpl @Inject constructor(
    private val offStoreRepository: OffStoreRepository
) : OffStoreUseCase {
    override fun getOffStoreInfo(itemId: String): Flow<OffStoreListModel> = flow {
        offStoreRepository.getOffStoreInfo(itemId = itemId).collect {
            emit(it)
        }
    }
}