package com.example.presentation.record

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.presentation.components.BookDiarySurface
import com.example.presentation.graph.BookDiaryBottomBar
import com.example.presentation.graph.MainSections

@Composable
fun Record(
    onBookClick: (Long) -> Unit,
    onNavigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecordViewModel = viewModel()
) {
    val myBookList = viewModel.myBookList.observeAsState()
    viewModel.getMyBookList()
    Log.e("","room db 체크 ${myBookList.value}")
    Scaffold(
        bottomBar = {
            BookDiaryBottomBar(
                tabs = MainSections.values(),
                currentRoute = MainSections.RECORD.route,
                navigateToRoute = onNavigateToRoute
            )
        },
        modifier = modifier
    ) {paddingValues ->
        RecordScreen(
            onBookClick = onBookClick,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun RecordScreen(

    onBookClick: (Long) -> Unit,
    modifier: Modifier = Modifier
){
    BookDiarySurface {
        Box(modifier = modifier.fillMaxSize()){
            Text(text = "RecordScreen")
        }
    }
}