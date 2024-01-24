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
import com.example.presentation.bookdetail.BookDetail
import com.example.presentation.bookdetail.BookDetailState
import com.example.presentation.bookdetail.BookDetailViewModel
import com.example.presentation.graph.MainSections
import com.example.presentation.graph.addMainGraph
import com.example.presentation.home.HomeViewModel
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
        addMainGraph(onBookSelected, onNavigateToRoute, homeViewModel = homeViewModel, recordViewModel = recordViewModel, searchViewModel = searchViewModel,)
    }
    composable( // todo 이부분 여러번 호출로 발생하는 문제
        "${MainDestinations.BOOK_DETAIL_ROOT}/{${MainDestinations.BOOK_ID_KEY}}",
        arguments = listOf(navArgument(MainDestinations.BOOK_ID_KEY) { type = NavType.LongType })
    ) {backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val bookId = arguments.getLong(MainDestinations.BOOK_ID_KEY)
        bookDetailViewModel.getBookDetail(itemId = bookId)
        //val bookState = bookDetailViewModel.bookDetail.collectAsState()
        //if(bookState.value != BookDetailState.Loading && bookState.value != BookDetailState.Error()){
            //val bookDetailInfo = bookDetailViewModel.bookDetail.collectAsState().value.data?.bookList!![0]
            BookDetail(bookId = bookId, upPress = upPress, bookDetailViewModel = bookDetailViewModel)
        //}
    }
}