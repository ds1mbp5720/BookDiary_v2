package com.example.presentation.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.domain.model.BookModel
import com.example.presentation.components.BookDiarySurface
import com.example.presentation.components.BookListContent
import com.example.presentation.graph.BookDiaryBottomBar
import com.example.presentation.graph.MainSections

@Composable
fun Home(
    onBookClick: (Long) -> Unit,
    onNavigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {
    val bookListState = viewModel.bookListData.collectAsState()
    Log.e("","통신 결과 compose $bookListState")
    Scaffold(
        bottomBar = {
            BookDiaryBottomBar(
                tabs = MainSections.values(),
                currentRoute = MainSections.HOME.route,
                navigateToRoute = onNavigateToRoute
            )
        },
        modifier = modifier
    ) {paddingValues ->
        bookListState.value.data?.bookList?.let {
            HomeScreen(
                bookList = it,
                onBookClick = onBookClick,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Composable
private fun HomeScreen(
    bookList: List<BookModel>,
    onBookClick: (Long) -> Unit,
    modifier: Modifier = Modifier
){
    BookDiarySurface(modifier = modifier.fillMaxSize()) {
        Box{
            BookCollectionList(bookList, onBookClick,)
        }
    }
}

@Composable
private fun BookCollectionList(
    bookList: List<BookModel>,
    onBookClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier){
        Column{
            Spacer(
                modifier = Modifier.windowInsetsTopHeight(
                    WindowInsets.statusBars.add(WindowInsets(top = 56.dp))
                ))

            BookListContent(
                contentTile = "테스트 카테고리",
                books = bookList,
                onBookClick = onBookClick,

            )


        }
    }
    // 해당 카테고리 리스트만 보기 화면 연결
    /*AnimatedVisibility(
        visible =
    ) {

    }*/
}