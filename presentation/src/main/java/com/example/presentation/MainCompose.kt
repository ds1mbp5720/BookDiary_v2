package com.example.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
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
        val  homeViewModel: HomeViewModel = viewModel()
        NavHost(
            navController = bookDiaryNavController.navController,
            startDestination = MainDestinations.HOME_ROUTE
        ){
            bookDiaryNavGraph(
                onBookSelected = bookDiaryNavController::navigateToBookDetail,
                upPress = bookDiaryNavController::upPress,
                onNavigateToRoute = bookDiaryNavController::navigateToBottomBarRoute,
                homeViewModel = homeViewModel
            )
        }
    }
}

private fun NavGraphBuilder.bookDiaryNavGraph(
    onBookSelected: (Long, NavBackStackEntry) -> Unit,
    upPress: () -> Unit,
    onNavigateToRoute: (String) -> Unit,
    homeViewModel: HomeViewModel
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = MainSections.HOME.route
    ){
        addMainGraph(onBookSelected, onNavigateToRoute, homeViewModel = homeViewModel)
    }
    /*composable(
        "",
        arguments = listOf(navArgument(MainDestinations.BOOK_ID_KEY) { type = NavType.LongType })
    ) {navBackStackEntry ->
        val arguments = requireNotNull(navBackStackEntry.arguments)
        val bookId = arguments.getLong(MainDestinations.BOOK_ID_KEY)
        // todo compose 책 상세화면 추가 (bookId, upPress)
    }*/
}