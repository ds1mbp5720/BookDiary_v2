package com.example.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.domain.model.BookModel
import com.example.presentation.components.BookDiaryDivider
import com.example.presentation.components.BookDiaryScaffold
import com.example.presentation.components.BookDiarySurface
import com.example.presentation.components.SearchBar
import com.example.presentation.graph.BookDiaryBottomBar
import com.example.presentation.graph.MainSections
import com.example.presentation.util.SearchDisplay

@Composable
fun Search(
    onBookClick: (Long) -> Unit,
    onNavigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel()
) {
    val context = LocalContext.current
    val searchBookList: LazyPagingItems<BookModel> = viewModel.searchBookList.collectAsLazyPagingItems()
    BookDiaryScaffold(
        bottomBar = {
            BookDiaryBottomBar(
                tabs = MainSections.values(),
                currentRoute = MainSections.SEARCH.route,
                navigateToRoute = onNavigateToRoute
            )
        },
        modifier = modifier
    ) {paddingValues ->
        BookDiarySurface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            Column {
                Spacer(modifier = Modifier.statusBarsPadding())
                SearchBar(
                    query = viewModel.searchState.query,
                    onQueryChange = { viewModel.searchState.query = it },
                    onSearch = {
                        viewModel.getSearchBookList(viewModel.searchState.query.text,100)
                        viewModel.addSearchHistory(context,viewModel.searchState.query.text)
                        viewModel.searchState.searching = true
                               },
                    searchFocused = viewModel.searchState.focused || viewModel.searchState.query.text != "",
                    onSearchFocusChange = {viewModel.searchState.focused = it},
                    onClearQuery = {viewModel.searchState.query = TextFieldValue("")},
                    searching = viewModel.searchState.searching,
                )
                BookDiaryDivider()
                searchBookList.apply {
                    when{
                        loadState.append is LoadState.Loading -> { viewModel.searchState.searching = false }
                        loadState.append is LoadState.NotLoading -> {}
                        loadState.append is LoadState.Error -> {}
                        loadState.refresh is LoadState.Loading -> {}
                        loadState.refresh is LoadState.Error -> {}
                    }
                }
                when (viewModel.searchState.searchDisplay) {
                    SearchDisplay.StandBy -> {
                        viewModel.getSearchHistory(context)
                        StandByScreen(viewModel)
                    }
                    SearchDisplay.Results -> {
                        ResultScreen(
                            books = searchBookList,
                            onBookClick = onBookClick
                        )
                    }
                }
            }
        }
    }
}