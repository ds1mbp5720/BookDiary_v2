package com.example.domain.repository

import android.content.Context

interface SearchHistoryRepository {
    suspend fun setSearchHistory(context: Context, values: List<String>)
    suspend fun getSearchHistory(context: Context): List<String>
}