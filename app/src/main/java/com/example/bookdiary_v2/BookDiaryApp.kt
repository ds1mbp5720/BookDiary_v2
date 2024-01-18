package com.example.bookdiary_v2

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BookDiaryApp : Application() {
    override fun onCreate() {
        super.onCreate()

    }
}