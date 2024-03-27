package com.example.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.presentation.home.HomeViewModel
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this)
        observeUiState()
        homeViewModel.getBookListToItemNewAll("ItemNewAll", 20)
        homeViewModel.getBookListToItemNewSpecial("ItemNewSpecial", 20)
        homeViewModel.getBookListToItemBestseller("Bestseller", 20)
        homeViewModel.getBookListToItemBlogBest("BlogBest", 20)
        setContent {
            BookDiaryApp()
        }
    }

    private fun observeUiState() {
        // flow 사용으로 해당 부분 미사용
        /*lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                homeViewModel.bookListData.collect{
                    when(it){
                        is BookListState.Success -> {
                                Log.e("","통신 activity ${it.data}")
                        }
                        is BookListState.Loading -> {}
                        is BookListState.Error -> {
                            Log.e("","통신 activity error ${it.message} / ${it.resultCode}")
                        }

                        else -> {}
                    }
                }
            }
        }*/
    }
}