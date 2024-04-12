package com.example.presentation.search

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Settings
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylibrary.R
import com.example.presentation.components.BasicButton
import com.example.presentation.theme.BookDiaryTheme

@Composable
fun StandByScreen(
    searchViewModel: SearchViewModel = viewModel()
) {
    var historyMode by remember { mutableStateOf(true) } // true: list -> false : flow
    val searchHistory = searchViewModel.searchHistory.collectAsState().value
    if (searchHistory.isEmpty()) {
        Text(
            modifier = Modifier.fillMaxSize(),
            text = stringResource(id = R.string.str_no_search_record),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    } else {
        Column(
            modifier = Modifier
                .padding(top = 8.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.str_search_record),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                BasicButton(
                    onClick = {
                        historyMode = !historyMode
                    },
                    modifier = Modifier
                        .align(Alignment.CenterEnd),
                    border = BorderStroke(
                        width = 1.dp,
                        color = BookDiaryTheme.colors.brand
                    ),
                    backgroundGradient = listOf(BookDiaryTheme.colors.uiBackground, BookDiaryTheme.colors.uiBackground)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "change_list_mode")
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            if (historyMode) {
                SearchHistoryColumn(
                    searchHistory = searchHistory,
                    searchViewModel = searchViewModel
                )
            } else {
                SearchHistoryFlow(
                    searchHistory = searchHistory,
                    searchViewModel = searchViewModel
                )
            }
        }
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchHistoryFlow(
    searchHistory: List<String>,
    searchViewModel: SearchViewModel
) {
    val context = LocalContext.current
    FlowRow(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(start = 10.dp)
            .wrapContentHeight(align = Alignment.Top),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        repeat(searchHistory.size) { index ->
            SearchHistoryCard(
                modifier = Modifier,
                search = searchHistory[index],
                deleteButtonVisible = true,
                onDeleteEvent = {
                    searchViewModel.removeSearchHistory(context, searchHistory[index])
                },
                onClickEvent = {
                    searchViewModel.searchState.query = TextFieldValue(searchHistory[index])
                    searchViewModel.getSearchBookList(searchHistory[index], 100)
                    searchViewModel.searchState.searching = true
                }
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchHistoryColumn(
    searchHistory: List<String>,
    searchViewModel: SearchViewModel
) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        userScrollEnabled = true
    ) {
        items(searchHistory, key = { it }) { search ->
            val dismissState = rememberDismissState(
                positionalThreshold = { it * 0.2f },
                confirmValueChange = { dismissValue ->
                    when (dismissValue) {
                        DismissValue.Default -> {
                            false
                        }

                        DismissValue.DismissedToStart -> {
                            searchViewModel.removeSearchHistory(context, search)
                            true
                        }

                        DismissValue.DismissedToEnd -> {
                            false
                        }
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
                            .background(
                                color = color,
                                shape = RoundedCornerShape(16.dp)
                            )
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
                            .align(alignment = Alignment.CenterVertically)
                    ) {
                        SearchHistoryCard(
                            modifier = Modifier.fillMaxWidth(),
                            search = search,
                            onClickEvent = {
                                searchViewModel.searchState.query = TextFieldValue(search)
                                searchViewModel.getSearchBookList(search, 100)
                                searchViewModel.searchState.searching = true
                            }
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun SearchHistoryCard(
    modifier: Modifier,
    search: String,
    deleteButtonVisible: Boolean = false,
    onDeleteEvent: () -> Unit = {},
    onClickEvent: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .clickable { onClickEvent.invoke() },
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = BookDiaryTheme.colors.uiBackground),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row(
            modifier = modifier
                .padding(start = 16.dp, end = 8.dp, top = 6.dp, bottom = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = search,
                style = MaterialTheme.typography.titleMedium,
                color = BookDiaryTheme.colors.textLink
            )
            Spacer(modifier = Modifier.width(8.dp))
            if (deleteButtonVisible) {
                Icon(
                    modifier = Modifier
                        .clickable {
                            onDeleteEvent.invoke()
                        },
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "search_history_delete"
                )
            }
        }
    }
}