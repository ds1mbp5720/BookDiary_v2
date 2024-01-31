package com.example.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.domain.model.BookModel
import com.example.mylibrary.R
import com.example.presentation.components.BasicUpButton
import com.example.presentation.components.BookDiaryDivider
import com.example.presentation.components.BookDiaryScaffold
import com.example.presentation.components.BookDiarySurface
import com.example.presentation.components.BookItemList
import com.example.presentation.components.BookListContent
import com.example.presentation.graph.BookDiaryBottomBar
import com.example.presentation.graph.MainSections
import com.example.presentation.theme.BookDiaryTheme

enum class HomeListType{
    ItemNewAll, ItemNewSpecial, Bestseller, BlogBest
}
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
                viewModel = viewModel,
                onBookClick =  onBookClick,
                onListClick = onListClick)
        }
    }
}

@Composable
private fun BookCollectionList(
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
                    contentTitle = HomeListType.ItemNewAll,
                    books = bookListDataItemNewAll,
                    onBookClick = onBookClick,
                    onListClick = onListClick,
                    viewModel = viewModel
                )
                BookDiaryDivider(thickness = 2.dp)
                BookListContent(
                    contentTitle = HomeListType.ItemNewSpecial,
                    books = bookListDataItemNewSpecial,
                    onBookClick = onBookClick,
                    onListClick = onListClick,
                    viewModel = viewModel
                )
                BookDiaryDivider(thickness = 2.dp)
                BookListContent(
                    contentTitle = HomeListType.Bestseller,
                    books = bookListDataBestseller,
                    onBookClick = onBookClick,
                    onListClick = onListClick,
                    viewModel = viewModel
                )
                BookDiaryDivider(thickness = 2.dp)
                BookListContent(
                    contentTitle = HomeListType.BlogBest,
                    books = bookListDataBlogBest,
                    onBookClick = onBookClick,
                    onListClick = onListClick,
                    viewModel = viewModel
                )
            }


        }
    }
}

@Composable
fun SingleCategoryListScreen(
    listType: HomeListType,
    onBookClick: (Long) -> Unit,
    upPress: () -> Unit,
    viewModel: HomeViewModel = viewModel(),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
) {
    val books = viewModel.singleCategoryBookList.collectAsLazyPagingItems()
    Column{
        Spacer(modifier = Modifier.height(10.dp))
        Row(){
            BasicUpButton(upPress, padding = 2.dp)
            Text(
                text = stringResource(id = when(listType){
                    HomeListType.ItemNewAll-> {
                        R.string.str_new_all_title
                    }
                    HomeListType.ItemNewSpecial -> {
                        R.string.str_new_special_title
                    }
                    HomeListType.Bestseller -> {
                        R.string.str_bestseller_rank
                    }
                    HomeListType.BlogBest -> {
                        R.string.str_bolg_best_title
                    }
                }),
                style = MaterialTheme.typography.headlineLarge,
                color = BookDiaryTheme.colors.brand,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .wrapContentWidth(Alignment.Start)
            )
        }
        
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