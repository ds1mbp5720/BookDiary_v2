package com.example.data.mapper

import com.example.data.dto.OffStoreListData
import com.example.domain.model.OffStoreListModel
import com.example.domain.model.OffStoreModel

object OffStoreListMapper {
    fun toDomain(data: OffStoreListData): OffStoreListModel {
        val offStoreList = mutableListOf<OffStoreModel>()
        data.itemOffStoreList.forEach {
            offStoreList.add(it.toDomain())
        }
        return OffStoreListModel(
            link = data.link,
            pubDate = data.pubDate,
            query = data.query,
            version = data.version,
            itemOffStoreList = offStoreList
        )
    }
}

fun OffStoreListData.toDomain(): OffStoreListModel {
    return OffStoreListMapper.toDomain(this)
}