package com.example.test

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.presentation.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestActivity: AppCompatActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
}