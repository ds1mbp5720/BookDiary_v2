package com.example.presentation.search

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.domain.model.BookModel
import com.example.presentation.components.BookItemList

@Composable
fun ResultScreen(
    books: LazyPagingItems<BookModel>,
    onBookClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(top = 6.dp, bottom = 6.dp),
        userScrollEnabled = true
    ) {
        Log.e("","검색 결과 위치2 ${books.itemCount}")
        items(books.itemCount){
            BookItemList(
                book = books.itemSnapshotList[it],
                onBookClick = onBookClick,
                showDivider = it != 0)
        }
    }
}

@Composable
fun NoResultScreen(){

}