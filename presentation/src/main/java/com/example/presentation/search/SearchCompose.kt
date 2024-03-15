package com.example.presentation.search

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.domain.model.BookModel
import com.example.mylibrary.R
import com.example.presentation.components.BookDiaryDivider
import com.example.presentation.components.BookDiaryScaffold
import com.example.presentation.components.BookDiarySurface
import com.example.presentation.graph.BookDiaryBottomBar
import com.example.presentation.graph.MainSections
import com.example.presentation.theme.BookDiaryTheme
import com.example.presentation.util.SearchDisplay
import com.example.presentation.util.mirroringBackIcon
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Search(
    onBookClick: (Long) -> Unit,
    onNavigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel()
) {
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()
    val searchBookList: LazyPagingItems<BookModel> = viewModel.searchBookList.collectAsLazyPagingItems()
    val loadingState = viewModel.loading.collectAsStateWithLifecycle()
    var refreshing by remember { mutableStateOf(false) }
    var refreshingAction by remember { mutableStateOf(true) } // 새로고침 스크롤 간격는 변수
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            if(viewModel.searchState.searchDisplay == SearchDisplay.Results){
                refreshing = true
                viewModel.getSearchBookList(viewModel.searchState.query.text,100)
            }
            coroutine.launch {//스크롤 갱신 1.5초 delay
                refreshingAction = false
                delay(1500)
                refreshingAction = true
            }
        }
    )
    LaunchedEffect(key1 = loadingState.value){
        refreshing = loadingState.value
    }
    LaunchedEffect(key1 = searchBookList.loadState){
        searchBookList.apply {
            when{
                loadState.append is LoadState.Loading -> {
                    viewModel.searchState.searching = false
                    viewModel.searchState.noResult = false
                    Log.e("","갱신 상태1 ${viewModel.searchState.noResult}")
                    refreshing = false
                }
                loadState.append is LoadState.NotLoading -> {
                    Log.e("","갱신 상태3 ${searchBookList.itemCount}")
                    if(this.loadState.append.endOfPaginationReached && searchBookList.itemCount == 0){
                        viewModel.searchState.searching = false
                        viewModel.searchState.noResult = true
                    }
                }
                loadState.append is LoadState.Error -> {
                    Toast.makeText(context, "Error:" + (loadState.append as LoadState.Error).error.message, Toast.LENGTH_SHORT).show()
                }
                loadState.refresh is LoadState.Loading -> {}
                loadState.refresh is LoadState.Error -> {
                    Toast.makeText(context, "Error:" + (loadState.refresh as LoadState.Error).error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
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
                Box(
                    modifier = Modifier
                        .background(BookDiaryTheme.colors.uiBackground)
                        .fillMaxWidth()
                        .height(
                            if (refreshing) 90.dp
                            else lerp(0.dp, 90.dp, pullRefreshState.progress.coerceIn(0f..1f))
                        )
                ){
                    if(refreshing){
                        CircularProgressIndicator(
                            color = BookDiaryTheme.colors.brand,
                            modifier = Modifier
                                .padding(horizontal = 6.dp)
                                .align(Alignment.Center)
                                .size(70.dp)
                        )
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
                            onBookClick = onBookClick,
                            searchResult = viewModel.searchState.noResult,
                            modifier = Modifier
                                .pullRefresh(
                                    state = pullRefreshState,
                                    enabled = refreshingAction
                                )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    onSearch: () -> Unit,
    searchFocused: Boolean,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    searching: Boolean,
    modifier: Modifier = Modifier
){
    val keyboardController = LocalSoftwareKeyboardController.current
    BookDiarySurface(
        color = BookDiaryTheme.colors.uiFloated,
        contentColor = BookDiaryTheme.colors.textSecondary,
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            if(query.text.isEmpty()) SearchHint()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight()
            ){
                if(searchFocused) {
                    IconButton(onClick = onClearQuery) {
                        Icon(
                            imageVector = mirroringBackIcon(),
                            tint = BookDiaryTheme.colors.iconPrimary,
                            contentDescription = "ic_back"
                        )
                    }
                }
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            onSearch.invoke()
                            keyboardController?.hide()
                        }
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .onFocusChanged {
                            onSearchFocusChange(it.isFocused)
                        }
                )
                if(searchFocused) {
                    IconButton(
                        onClick = {
                            onSearch.invoke()
                            keyboardController?.hide()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            tint = BookDiaryTheme.colors.iconPrimary,
                            contentDescription = ""
                        )
                    }
                }
                if(searching){
                    CircularProgressIndicator(
                        color = BookDiaryTheme.colors.iconPrimary,
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                            .size(36.dp)
                    )
                } else{
                    Spacer(modifier = Modifier.width(48.dp))
                }
            }
        }
    }
}

@Composable
fun SearchHint(){
    Row(
      verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
    ){
        Icon(
           imageVector = Icons.Outlined.Search,
            tint = BookDiaryTheme.colors.textHelp,
            contentDescription = "ic_search",
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = R.string.str_search_hint),
            color = BookDiaryTheme.colors.textHelp
        )
    }
}