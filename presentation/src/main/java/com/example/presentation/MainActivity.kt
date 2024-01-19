package com.example.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.domain.model.BookListModel
import com.example.presentation.home.BookListState
import com.example.presentation.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private val categoryBookList = mutableListOf<BookListModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeUiState()
        homeViewModel.getBookList("ItemNewAll")
        /*homeViewModel.getBookList("ItemNewSpecial")
        homeViewModel.getBookList("Bestseller")
        homeViewModel.getBookList("BlogBest")*/
        homeViewModel.getBookPagingList("ItemNewAll")
        setContent {
            BookDiaryApp()
        }

    }
    private fun observeUiState(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                homeViewModel.bookListData.collect{
                    when(it){
                        is BookListState.Success -> {
                                Log.e("","통신 activity ${it.data}")
                            it.data?.let { it1 ->
                                homeViewModel.addCategoryBookList(it1)
                            }
                        }
                        is BookListState.Loading -> {}
                        is BookListState.Error -> {
                            Log.e("","통신 activity error ${it.message} / ${it.resultCode}")
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}