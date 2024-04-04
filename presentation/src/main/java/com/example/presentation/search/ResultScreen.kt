package com.example.presentation.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.domain.model.BookModel
import com.example.mylibrary.R
import com.example.presentation.components.book.BookItemList
import com.example.presentation.theme.BookDiaryTheme

@Composable
fun ResultScreen(
    books: LazyPagingItems<BookModel>,
    onBookClick: (Long) -> Unit,
    searchResult: Boolean,
    modifier: Modifier = Modifier
) {
    if (searchResult) {
        Text(
            text = stringResource(id = R.string.str_no_result),
            style = MaterialTheme.typography.titleLarge,
            color = BookDiaryTheme.colors.textPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        )
    } else {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(top = 6.dp, bottom = 6.dp),
            userScrollEnabled = true
        ) {
            items(books.itemCount) {
                BookItemList(
                    book = books[it],
                    onBookClick = onBookClick,
                    showDivider = it != 0
                )
            }
        }
    }
}