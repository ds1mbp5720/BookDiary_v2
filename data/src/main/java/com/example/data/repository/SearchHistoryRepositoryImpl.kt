package com.example.data.repository

import android.content.Context
import com.example.domain.repository.SearchHistoryRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

/**
 * 검색 기록 저장 으로 SharedPreferences 로 mutable set 만 저장
 */
class SearchHistoryRepositoryImpl @Inject constructor() : SearchHistoryRepository {
    override suspend fun setSearchHistory(context: Context, values: List<String>) {
        val gson = Gson()
        val json = gson.toJson(values)
        val prefs = context.getSharedPreferences("HISTORY", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("history", json)
        editor.apply()
    }

    override suspend fun getSearchHistory(context: Context): List<String> {
        val prefs = context.getSharedPreferences("HISTORY", Context.MODE_PRIVATE)
        val json = prefs.getString("history", "[]") // 최초 null 일경우 NPE 발생으로 초기값 수정 "[]"
        val gson = Gson()
        return gson.fromJson(
            json,
            object : TypeToken<ArrayList<String?>>() {}.type
        )
    }
}