package com.example.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.presentation.graph.MainSections
import com.example.presentation.graph.addMainGraph
import com.example.presentation.navigation.MainDestinations
import com.example.presentation.navigation.rememberBookDiaryNavController
import com.example.presentation.theme.BookDiaryTheme

@Composable
fun BookDiaryApp() {
    BookDiaryTheme {
        val bookDiaryNavController = rememberBookDiaryNavController()
        NavHost(
            navController = bookDiaryNavController.navController,
            startDestination = MainDestinations.HOME_ROUTE
        ){
            bookDiaryNavGraph(
                onBookSelected = bookDiaryNavController::navigateToBookDetail,
                upPress = bookDiaryNavController::upPress,
                onNavigateToRoute = bookDiaryNavController::navigateToBottomBarRoute
            )
        }
    }
}

private fun NavGraphBuilder.bookDiaryNavGraph(
    onBookSelected: (Long, NavBackStackEntry) -> Unit,
    upPress: () -> Unit,
    onNavigateToRoute: (String) -> Unit
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = MainSections.HOME.route
    ){
        addMainGraph(onBookSelected, onNavigateToRoute)
    }
    composable(
        "",
        arguments = listOf(navArgument(MainDestinations.BOOK_ID_KEY) { type = NavType.LongType })
    ) {navBackStackEntry ->
        val arguments = requireNotNull(navBackStackEntry.arguments)
        val bookId = arguments.getLong(MainDestinations.BOOK_ID_KEY)
        // todo compose 책 상세화면 추가 (bookId, upPress)
    }
}