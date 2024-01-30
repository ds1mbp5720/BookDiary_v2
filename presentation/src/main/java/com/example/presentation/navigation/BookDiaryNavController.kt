package com.example.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

object MainDestinations {
    const val HOME_ROUTE = "home"
    const val SEARCH_ROOT = "search"
    const val RECORD_ROOT = "record"
    const val MY_PAGE_ROOT = "myPage"
    const val BOOK_DETAIL_ROOT = "detail"
    const val BOOK_ID_KEY = "bookId"
    const val BOOK_LIST_ROOT = "book_list"
    const val BOOK_LIST_TYPE = "list_type"
}

@Composable
fun rememberBookDiaryNavController(
    navController: NavHostController = rememberNavController()
): BookDiaryNavController = remember(navController) {
    BookDiaryNavController(navController)
}

@Stable
class BookDiaryNavController(
    val navController: NavHostController,
) {
    val currentRoute: String? get() = navController.currentDestination?.route

    fun upPress() { navController.navigateUp() }

    fun navigateToBottomBarRoute(route: String) {
        if(route != currentRoute){
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true
                popUpTo(findStartDestination(navController.graph).id) {
                    saveState = true
                }
            }
        }
    }

    fun navigateToBookDetail(bookId: Long, from: NavBackStackEntry) {
        if(from.lifeCycleIsResume()){
            navController.navigate("${MainDestinations.BOOK_DETAIL_ROOT}/$bookId")
        }
    }

    fun navigateToRecommendList(listType: String, from: NavBackStackEntry) {
        if(from.lifeCycleIsResume()){
            navController.navigate("${MainDestinations.BOOK_LIST_ROOT}/$listType")
        }
    }
}

// lifecycle resume 체크용 Boolean
private fun NavBackStackEntry.lifeCycleIsResume() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED
private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)

/**
 * Compied JetSnack JetSnackNavController.kt
 */
private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}