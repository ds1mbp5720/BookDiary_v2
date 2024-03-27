package com.example.data.dto

import com.google.gson.annotations.SerializedName

class BookListData : BaseData() {
    @SerializedName("item")
    val item: List<BookData>? = null
}
