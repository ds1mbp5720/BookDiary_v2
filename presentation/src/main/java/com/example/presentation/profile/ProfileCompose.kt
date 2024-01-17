package com.example.presentation.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.presentation.components.BookDiarySurface
import com.example.presentation.graph.BookDiaryBottomBar
import com.example.presentation.graph.MainSections

@Composable
fun Profile(
    onBookClick: (Long) -> Unit,
    onNavigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        bottomBar = {
            BookDiaryBottomBar(
                tabs = MainSections.values(),
                currentRoute = MainSections.HOME.route,
                navigateToRoute = onNavigateToRoute
            )
        },
        modifier = modifier
    ) {paddingValues ->
        ProfileScreen(
            onBookClick = onBookClick,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun ProfileScreen(

    onBookClick: (Long) -> Unit,
    modifier: Modifier = Modifier
){
    BookDiarySurface {
        Box(modifier = modifier.fillMaxSize()){
            Text(text = "ProfileScreen")
        }
    }
}