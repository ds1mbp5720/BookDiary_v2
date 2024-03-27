package com.example.domain.repository

import android.content.Context

/**
 * 검색 기록 저장 으로 mutable set 만 저장
 */
interface SearchHistoryRepository {
    suspend fun setSearchHistory(context: Context, values: List<String>)
    suspend fun getSearchHistory(context: Context): List<String>
}