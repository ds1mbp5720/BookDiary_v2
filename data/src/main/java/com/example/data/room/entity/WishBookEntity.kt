package com.example.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WishBookInfo")
data class WishBookEntity (
    @PrimaryKey
    val itemId: Long,
    val imageUrl: String, // 표지 이미지
    val title: String, // 상품명
)

