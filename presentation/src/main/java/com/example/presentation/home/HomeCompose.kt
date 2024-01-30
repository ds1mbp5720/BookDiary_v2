package com.example.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.domain.model.BookModel
import com.example.presentation.components.BookDiaryDivider
import com.example.presentation.components.BookDiaryScaffold
import com.example.presentation.components.BookDiarySurface
import com.example.presentation.components.BookItemList
import com.example.presentation.components.BookListContent
import com.example.presentation.graph.BookDiaryBottomBar
import com.example.presentation.graph.MainSections
import com.example.presentation.theme.BookDiaryTheme

@Composable
fun Home(
    onBookClick: (Long) -> Unit,
    onListClick: (String) -> Unit,
    onNavigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {
    BookDiaryScaffold(
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
            onBookClick = onBookClick,
            onListClick = onListClick,
            modifier = Modifier.padding(paddingValues),
            viewModel = viewModel
        )
    }
}

@Composable
private fun HomeScreen(
    onBookClick: (Long) -> Unit,
    onListClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
){
    BookDiarySurface(modifier = modifier.fillMaxSize()) {
        Box{
            BookCollectionList(
                contentTitle1 = "신간 전체",
                contentTitle2 = "주목할 만한 신간",
                contentTitle3 = "베스트셀러",
                contentTitle4 = "블로거 베스트셀러(국내 도서)",
                viewModel = viewModel,
                onBookClick =  onBookClick,
                onListClick = onListClick)
        }
    }
}

@Composable
private fun BookCollectionList(
    contentTitle1: String,
    contentTitle2: String,
    contentTitle3: String,
    contentTitle4: String,
    viewModel: HomeViewModel,
    onBookClick: (Long) -> Unit,
    onListClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val bookListDataItemNewAll: LazyPagingItems<BookModel> = viewModel.bookListDataItemNewAll.collectAsLazyPagingItems()
    val bookListDataItemNewSpecial: LazyPagingItems<BookModel> = viewModel.bookListDataItemNewSpecial.collectAsLazyPagingItems()
    val bookListDataBestseller: LazyPagingItems<BookModel> = viewModel.bookListDataBestseller.collectAsLazyPagingItems()
    val bookListDataBlogBest: LazyPagingItems<BookModel> = viewModel.bookListDataBlogBest.collectAsLazyPagingItems()
    Box(modifier = modifier){
        LazyColumn{
            item{
                Spacer(
                    modifier = Modifier.windowInsetsTopHeight(
                        WindowInsets.statusBars.add(WindowInsets(top = 56.dp))
                    ))
                BookListContent(
                    contentTitle = contentTitle1,
                    books = bookListDataItemNewAll,
                    onBookClick = onBookClick,
                    onListClick = onListClick
                )
                BookDiaryDivider(thickness = 2.dp)
                BookListContent(
                    contentTitle = contentTitle2,
                    books = bookListDataItemNewSpecial,
                    onBookClick = onBookClick,
                    onListClick = onListClick
                )
                BookDiaryDivider(thickness = 2.dp)
                BookListContent(
                    contentTitle = contentTitle3,
                    books = bookListDataBestseller,
                    onBookClick = onBookClick,
                    onListClick = onListClick
                )
                BookDiaryDivider(thickness = 2.dp)
                BookListContent(
                    contentTitle = contentTitle4,
                    books = bookListDataBlogBest,
                    onBookClick = onBookClick,
                    onListClick = onListClick
                )
            }


        }
    }
}

@Composable
fun SingleCategoryListScreen(
    listType: String,
    books: LazyPagingItems<BookModel>,
    onBookClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column{
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = listType,
            style = MaterialTheme.typography.titleLarge,
            color = BookDiaryTheme.colors.brand,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(start = 20.dp)
                .wrapContentWidth(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(10.dp))
        BookDiaryDivider()
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(top = 6.dp, bottom = 6.dp),
            userScrollEnabled = true
        ) {
            items(books.itemCount) {
                BookItemList(
                    book = books.itemSnapshotList[it],
                    onBookClick = onBookClick,
                    showDivider = it != 0
                )
            }
        }
    }
}