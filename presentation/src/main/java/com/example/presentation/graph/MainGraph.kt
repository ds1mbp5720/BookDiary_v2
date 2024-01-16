package com.example.presentation.graph

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.mylibrary.R
import com.example.presentation.components.BookDiarySurface
import com.example.presentation.home.Home

enum class MainSections(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    HOME(R.string.str_home, Icons.Outlined.Home ,"home/home"),
    SEARCH(R.string.str_search, Icons.Outlined.Search ,"home/search"),
    RECORD(R.string.str_record, Icons.Outlined.Create ,"home/record"),
    PROFILE(R.string.str_profile, Icons.Outlined.AccountCircle ,"home/profile")
}
fun NavGraphBuilder.addMainGraph(
    onBookSelected: (Long, NavBackStackEntry) -> Unit,
    onNavigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    composable(MainSections.HOME.route){ from ->
        Home(onBookClick = { id -> onBookSelected(id, from) }, onNavigateToRoute = onNavigateToRoute, modifier =  modifier)
    }
    composable(MainSections.SEARCH.route){ from ->

    }
    composable(MainSections.RECORD.route){ from ->

    }
    composable(MainSections.PROFILE.route){ from ->

    }
}

@Composable
fun MainBottomBar(
    tabs: Array<MainSections>,
    currentRoute: String,
    navigationRoute: (String) -> Unit,
    color: Color = Color.White,
    contentColor : Color = Color.Gray
    //todo 색상 추가
) {
    val routes = remember { tabs.map { it.route } }
    val currentSection = tabs.first { it.route == currentRoute }

    BookDiarySurface(
        color = color,
        contentColor = contentColor
    ){

    }
}