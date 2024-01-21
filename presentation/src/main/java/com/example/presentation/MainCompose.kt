package com.example.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.presentation.bookdetail.BookDetail
import com.example.presentation.bookdetail.BookDetailViewModel
import com.example.presentation.graph.MainSections
import com.example.presentation.graph.addMainGraph
import com.example.presentation.home.HomeViewModel
import com.example.presentation.navigation.MainDestinations
import com.example.presentation.navigation.rememberBookDiaryNavController
import com.example.presentation.theme.BookDiaryTheme

@Composable
fun BookDiaryApp() {
    BookDiaryTheme {
        val bookDiaryNavController = rememberBookDiaryNavController()
        val homeViewModel: HomeViewModel = viewModel()
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
    bookDetailViewModel: BookDetailViewModel
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = MainSections.HOME.route,
    ){
        addMainGraph(onBookSelected, onNavigateToRoute, homeViewModel = homeViewModel)
    }
    composable(
        "${MainDestinations.BOOK_DETAIL_ROOT}/{${MainDestinations.BOOK_ID_KEY}}",
        arguments = listOf(navArgument(MainDestinations.BOOK_ID_KEY) { type = NavType.LongType })
    ) {navBackStackEntry ->
        val arguments = requireNotNull(navBackStackEntry.arguments)
        val bookId = arguments.getLong(MainDestinations.BOOK_ID_KEY)
        BookDetail(bookId = bookId, upPress = upPress, bookDetailViewModel = bookDetailViewModel)
    }
}