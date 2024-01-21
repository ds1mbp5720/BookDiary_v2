package com.example.presentation.bookdetail

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BookDetail(
    bookId: Long,
    upPress: () -> Unit,
    bookDetailViewModel: BookDetailViewModel
){
    // todo bookId. 활용 api 호출 추가
    bookDetailViewModel.getBookDetail(itemId = bookId)
    Box(
       modifier = Modifier.fillMaxSize()
    ){
        Log.e("","조회 id $bookId")
        val scroll = rememberScrollState(0)
    }
}