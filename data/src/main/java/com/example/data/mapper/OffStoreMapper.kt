package com.example.data.mapper

import com.example.data.dto.OffStoreData
import com.example.domain.model.OffStoreModel

object OffStoreMapper {
    fun toDomain(data: OffStoreData) : OffStoreModel {
        return OffStoreModel(
            offCode = data.offCode,
            offName = data.offName,
            link = data.link,
        )
    }
}

fun OffStoreData.toDomain(): OffStoreModel {
    return OffStoreMapper.toDomain(this)
}