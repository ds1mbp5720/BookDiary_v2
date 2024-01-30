package com.example.presentation
/*
Copyright [yyyy] [name of copyright owner]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
* */

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.domain.model.BookListModel
import com.example.presentation.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private val categoryBookList = mutableListOf<BookListModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeUiState()
        homeViewModel.getBookListToItemNewAll("ItemNewAll",20)
        homeViewModel.getBookListToItemNewSpecial("ItemNewSpecial",20)
        homeViewModel.getBookListToItemBestseller("Bestseller",20)
        homeViewModel.getBookListToItemBlogBest("BlogBest",20)
        setContent {
            BookDiaryApp()
        }

    }
    private fun observeUiState(){
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