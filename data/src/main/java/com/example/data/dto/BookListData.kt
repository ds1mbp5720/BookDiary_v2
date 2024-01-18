package com.example.data.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

class BookListData: BaseData(){
    @SerializedName("item")
    val item: List<BookData>? = null
}
