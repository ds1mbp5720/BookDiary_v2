package com.example.data.dto

import com.google.gson.annotations.SerializedName

data class OffStoreData(
    @SerializedName("offCode")
    val offCode: String,
    @SerializedName("offName")
    val offName: String,
    @SerializedName("link")
    val link: String,
)
