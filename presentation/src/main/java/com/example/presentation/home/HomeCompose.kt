package com.example.presentation.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.domain.model.BookListModel
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
    val bookListDataItemNewAll: LazyPagingItems<BookModel> = viewModel.bookListDataItemNewAll.collectAsLazyPagingItems()
    val bookListDataItemNewSpecial: LazyPagingItems<BookModel> = viewModel.bookListDataItemNewSpecial.collectAsLazyPagingItems()
    val bookListDataBestseller: LazyPagingItems<BookModel> = viewModel.bookListDataBestseller.collectAsLazyPagingItems()
    val bookListDataBlogBest: LazyPagingItems<BookModel> = viewModel.bookListDataBlogBest.collectAsLazyPagingItems()
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
        HomeScreen(
            bookListDataItemNewAll = bookListDataItemNewAll,
            bookListDataItemNewSpecial = bookListDataItemNewSpecial,
            bookListDataBestseller = bookListDataBestseller,
            bookListDataBlogBest = bookListDataBlogBest,
            onBookClick = onBookClick,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun HomeScreen(
    bookListDataItemNewAll: LazyPagingItems<BookModel>,
    bookListDataItemNewSpecial: LazyPagingItems<BookModel>,
    bookListDataBestseller: LazyPagingItems<BookModel>,
    bookListDataBlogBest: LazyPagingItems<BookModel>,
    onBookClick: (Long) -> Unit,
    modifier: Modifier = Modifier
){
    BookDiarySurface(modifier = modifier.fillMaxSize()) {
        Box{
            BookCollectionList(
                bookListDataItemNewAll = bookListDataItemNewAll,
                bookListDataItemNewSpecial = bookListDataItemNewSpecial,
                bookListDataBestseller = bookListDataBestseller,
                bookListDataBlogBest = bookListDataBlogBest,
                contentTitle1 = "신간 전체",
                contentTitle2 = "주목할 만한 신간",
                contentTitle3 = "베스트셀러",
                contentTitle4 = "블로거 베스트셀러(국내 도서)",
                onBookClick =  onBookClick,)
        }
    }
}

@Composable
private fun BookCollectionList(
    bookListDataItemNewAll: LazyPagingItems<BookModel>,
    bookListDataItemNewSpecial: LazyPagingItems<BookModel>,
    bookListDataBestseller: LazyPagingItems<BookModel>,
    bookListDataBlogBest: LazyPagingItems<BookModel>,
    contentTitle1: String,
    contentTitle2: String,
    contentTitle3: String,
    contentTitle4: String,
    onBookClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier){
        LazyColumn{
            item{
                Spacer(
                    modifier = Modifier.windowInsetsTopHeight(
                        WindowInsets.statusBars.add(WindowInsets(top = 56.dp))
                    ))
                Log.e("","페이징 체크 ${bookListDataItemNewAll.itemCount} / " +
                        "${bookListDataItemNewSpecial.itemCount} /${bookListDataBlogBest.itemCount} /${bookListDataBestseller.itemCount} /")
                BookListContent(
                    contentTile = contentTitle1,
                    books = bookListDataItemNewAll,
                    onBookClick = onBookClick,
                )
                Spacer(modifier = Modifier.height(10.dp))
                BookListContent(
                    contentTile = contentTitle2,
                    books = bookListDataItemNewSpecial,
                    onBookClick = onBookClick,
                )
                Spacer(modifier = Modifier.height(10.dp))
                BookListContent(
                    contentTile = contentTitle3,
                    books = bookListDataBestseller,
                    onBookClick = onBookClick,
                )
                Spacer(modifier = Modifier.height(10.dp))
                BookListContent(
                    contentTile = contentTitle4,
                    books = bookListDataBlogBest,
                    onBookClick = onBookClick,
                )
                Spacer(modifier = Modifier.height(10.dp))
            }


        }
    }
    // 해당 카테고리 리스트만 보기 화면 연결
    /*AnimatedVisibility(
        visible =
    ) {

    }*/
}