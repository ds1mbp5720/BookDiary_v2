package com.example.presentation.search

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.presentation.theme.BookDiaryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandByScreen(
   searchViewModel: SearchViewModel = viewModel()
){
    val context = LocalContext.current
    val searchHistory = searchViewModel.searchHistory.collectAsState().value
    if(searchHistory.isEmpty()){
        Text(
            modifier = Modifier.fillMaxSize(),
            text = "검색기록이 없습니다.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 20.dp, bottom = 20.dp),
        userScrollEnabled = true
    ){
        item {
            Text(
                modifier = Modifier.fillMaxSize(),
                text = "검색기록",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
        items(searchHistory, key = {it}){search ->
            val dismissState = rememberDismissState(
                positionalThreshold = { it * 0.4f },
                confirmValueChange = {dismissValue ->
                    when(dismissValue){
                        DismissValue.Default -> {false}
                        DismissValue.DismissedToStart -> {
                            searchViewModel.removeSearchHistory(context, search)
                            true
                        }
                        DismissValue.DismissedToEnd -> {false}
                    }
                }
            )
            val cardElevation = animateDpAsState(
                if (dismissState.dismissDirection != null) 4.dp else 0.dp, label = ""
            ).value
            SwipeToDismiss(
                state = dismissState,
                modifier = Modifier
                    .padding(vertical = Dp(1f)),
                directions = setOf(
                    DismissDirection.EndToStart
                ),
                background = {
                    val color by animateColorAsState(
                        when (dismissState.targetValue) {
                            DismissValue.Default -> Color.White
                            else -> Color.Red
                        }, label = ""
                    )
                    val alignment = Alignment.CenterEnd
                    val icon = Icons.Default.Delete

                    val scale by animateFloatAsState(
                        if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f, label = ""
                    )
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(horizontal = Dp(20f)),
                        contentAlignment = alignment
                    ) {
                        Icon(
                            icon,
                            contentDescription = "Delete Icon",
                            modifier = Modifier.scale(scale)
                        )
                    }
                },
                dismissContent = {
                    Card(
                        elevation = CardDefaults.elevatedCardElevation(
                            defaultElevation = cardElevation,
                            pressedElevation = cardElevation,
                            focusedElevation = cardElevation,
                            hoveredElevation = cardElevation,
                            draggedElevation = cardElevation,
                            disabledElevation = cardElevation,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Dp(50f))
                            .align(alignment = Alignment.CenterVertically)
                    ) {
                        SearchHistoryCard(
                            search = search,
                            onClickEvent = {
                                searchViewModel.searchState.query = TextFieldValue(search)
                                searchViewModel.getSearchBookList(search,100)
                                searchViewModel.searchState.searching = true}
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun SearchHistoryCard(
    search: String,
    onClickEvent: () -> Unit
){
    Card(
        modifier = Modifier
            .padding(horizontal = 6.dp, vertical = 6.dp)
            .fillMaxWidth()
            .clickable { onClickEvent.invoke() },
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = BookDiaryTheme.colors.uiBackground),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ){
        Row(
            modifier = Modifier
                .padding(start = 4.dp, end = 4.dp, top = 6.dp, bottom = 6.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = search,
                style = MaterialTheme.typography.titleMedium,
                color = BookDiaryTheme.colors.textLink
            )
        }
    }
}