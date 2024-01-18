package com.example.data.dto

import com.google.gson.annotations.SerializedName

open class BaseData(
    @SerializedName("title")
    val title: String = "",
    @SerializedName("link")
    val link: String = "",
    @SerializedName("logo")
    val logo: String = "",
    @SerializedName("pubDate")
    val pubDate: String = "",
    @SerializedName("totalResults")
    val totalResults: String = "",
    @SerializedName("startIndex")
    val startIndex: String = "",
    @SerializedName("itemsPerPage")
    val itemsPerPage: String = "",
    @SerializedName("query")
    val query: String = "",
    @SerializedName("version")
    val version: String = "",
    @SerializedName("searchCategoryId")
    val searchCategoryId: String = "",
    @SerializedName("searchCategoryName")
    val searchCategoryName: String = ""
)
