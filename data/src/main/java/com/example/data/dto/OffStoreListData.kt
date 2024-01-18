package com.example.data.dto

import com.google.gson.annotations.SerializedName

data class OffStoreListData(
    @SerializedName("link")
    val link: String,
    @SerializedName("pubDate")
    val pubDate: String,
    @SerializedName("query")
    val query: String,
    @SerializedName("version")
    val version: String,
    @SerializedName("itemOffStoreList")
    val itemOffStoreList: List<OffStoreData>,
)
