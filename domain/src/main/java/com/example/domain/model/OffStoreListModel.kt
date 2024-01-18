package com.example.domain.model

data class OffStoreListModel(
    val link: String?,
    val pubDate: String?,
    val query: String?,
    val version: String?,
    val itemOffStoreList: List<OffStoreModel>?
)
