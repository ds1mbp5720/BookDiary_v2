package com.example.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.domain.model.BookModel
import com.example.presentation.bookdetail.BookDetail
import com.example.presentation.bookdetail.BookDetailState
import com.example.presentation.bookdetail.BookDetailViewModel
import com.example.presentation.graph.MainSections
import com.example.presentation.graph.addMainGraph
import com.example.presentation.home.HomeViewModel
import com.example.presentation.home.SingleCategoryListScreen
import com.example.presentation.navigation.MainDestinations
import com.example.presentation.navigation.rememberBookDiaryNavController
import com.example.presentation.record.RecordViewModel
import com.example.presentation.search.SearchViewModel
import com.example.presentation.theme.BookDiaryTheme

@Composable
fun BookDiaryApp() {
    BookDiaryTheme {
        val bookDiaryNavController = rememberBookDiaryNavController()
        val homeViewModel: HomeViewModel = viewModel()
        val recordViewModel: RecordViewModel = viewModel()
        val searchViewModel: SearchViewModel = viewModel()
        val bookDetailViewModel: BookDetailViewModel = viewModel()
        NavHost(
            navController = bookDiaryNavController.navController,
            startDestination = MainDestinations.HOME_ROUTE
        ){
            bookDiaryNavGraph(
                onBookSelected = bookDiaryNavController::navigateToBookDetail,
                onListSelected = bookDiaryNavController::navigateToRecommendList,
                upPress = bookDiaryNavController::upPress,
                onNavigateToRoute = bookDiaryNavController::navigateToBottomBarRoute,
                homeViewModel = homeViewModel,
                recordViewModel = recordViewModel,
                searchViewModel = searchViewModel,
                bookDetailViewModel = bookDetailViewModel
            )
        }
    }
}

private fun NavGraphBuilder.bookDiaryNavGraph(
    onBookSelected: (Long, NavBackStackEntry) -> Unit,
    onListSelected: (String, NavBackStackEntry) -> Unit,
    upPress: () -> Unit,
    onNavigateToRoute: (String) -> Unit,
    homeViewModel: HomeViewModel,
    recordViewModel: RecordViewModel,
    searchViewModel: SearchViewModel,
    bookDetailViewModel: BookDetailViewModel
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = MainSections.HOME.route,
    ){
        addMainGraph(onBookSelected = onBookSelected, onListSelected = onListSelected, onNavigateToRoute =  onNavigateToRoute, homeViewModel = homeViewModel, recordViewModel = recordViewModel, searchViewModel = searchViewModel)
    }
    // 책 상세보기 화면
    composable( // todo 이부분 여러번 호출로 발생하는 문제
        "${MainDestinations.BOOK_DETAIL_ROOT}/{${MainDestinations.BOOK_ID_KEY}}",
        arguments = listOf(navArgument(MainDestinations.BOOK_ID_KEY) { type = NavType.LongType })
    ) {backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val bookId = arguments.getLong(MainDestinations.BOOK_ID_KEY)
        bookDetailViewModel.getBookDetail(itemId = bookId)
        BookDetail(bookId = bookId, upPress = upPress, bookDetailViewModel = bookDetailViewModel)
    }
    // home 화면 한개 리스트 화면
    composable(
        "${MainDestinations.BOOK_LIST_ROOT}/{${MainDestinations.BOOK_LIST_TYPE}}",
        arguments = listOf(navArgument(MainDestinations.BOOK_LIST_TYPE) { type = NavType.StringType })
    ) {backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val listType = arguments.getString(MainDestinations.BOOK_LIST_TYPE)
        val bookListAll: LazyPagingItems<BookModel> = homeViewModel.singleCategoryBookList.collectAsLazyPagingItems()
        when(listType) {
            "ItemNewAll" -> homeViewModel.getSingleCategoryBookList("ItemNewAll",100)
            "ItemNewSpecial" -> homeViewModel.getSingleCategoryBookList("ItemNewSpecial",100)
            "Bestseller" -> homeViewModel.getSingleCategoryBookList("Bestseller",100)
            "BlogBest" -> homeViewModel.getSingleCategoryBookList("BlogBest",100)
        }
        SingleCategoryListScreen(
            listType = when(listType){
                "ItemNewAll" -> "신간 전체"
                "ItemNewSpecial" -> "주목할 만한 신간"
                "Bestseller" -> "베스트셀러"
                "BlogBest" -> "블로거 베스트셀러(국내 도서)"
                else -> "" },
            books = bookListAll,
            onBookClick = { id -> onBookSelected(id, backStackEntry) }
        )
    }
}