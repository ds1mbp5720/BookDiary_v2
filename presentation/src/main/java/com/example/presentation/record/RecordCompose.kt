package com.example.presentation.record

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylibrary.R
import com.example.presentation.components.BannersAds
import com.example.presentation.components.BasicButton
import com.example.presentation.components.BookDiaryDivider
import com.example.presentation.components.BookDiaryScaffold
import com.example.presentation.components.BookDiarySurface
import com.example.presentation.components.MyRecordDivider
import com.example.presentation.components.SearchBar
import com.example.presentation.components.SwipeToDismissVertical
import com.example.presentation.graph.BookDiaryBottomBar
import com.example.presentation.graph.MainSections
import com.example.presentation.theme.BookDiaryTheme
import com.example.presentation.util.textChangeVertical
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class RecordType {
    MYBOOK, WISH
}

@Composable
fun Record(
    onBookClick: (Long) -> Unit,
    onNavigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecordViewModel = viewModel()
) {
    val myBookList = viewModel.myBookList.observeAsState()
    val wishBookList = viewModel.wishBookList.observeAsState()
    viewModel.getMyBookList()
    viewModel.getWishBookList()
    val myBooks = myBookList.value
    val wishBooks = wishBookList.value
    var contentType by remember { mutableStateOf(RecordType.MYBOOK) }
    val scrollState = rememberScrollState()
    BookDiaryScaffold(
        bottomBar = {
            BookDiaryBottomBar(
                tabs = MainSections.values(),
                currentRoute = MainSections.RECORD.route,
                navigateToRoute = onNavigateToRoute
            )
        },
        modifier = modifier
    ) { paddingValues ->
        BookDiarySurface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                BookRecordContent(
                    animationVisible = contentType == RecordType.MYBOOK,
                    contentTitle = "내 책 목록" + " ${myBooks?.size ?: "0"} 권",
                    books = myBooks?.mapperMyBookToBasicBookRecordList(),
                    onBookClick = onBookClick,
                    onBookDeleteSwipe = { id ->
                        viewModel.deleteMyBook(id)
                    },
                    modifier = modifier.background(BookDiaryTheme.colors.brandSecondary)
                )
                BookRecordContent(
                    animationVisible = contentType == RecordType.WISH,
                    contentTitle = "찜 목록" + " ${wishBooks?.size ?: "0"} 권",
                    books = wishBooks?.mapperWishBookToBasicBookRecordList(),
                    onBookClick = onBookClick,
                    onBookDeleteSwipe = { id ->
                        viewModel.deleteWishBook(id)
                    },
                    modifier = modifier.background(BookDiaryTheme.colors.brandSecondary)
                )
                SearchBar(
                    query = viewModel.searchState.query,
                    onQueryChange = { viewModel.searchState.query = it },
                    onSearch = {
                        viewModel.searchState.searching = true
                    },
                    searchFocused = viewModel.searchState.focused || viewModel.searchState.query.text != "",
                    onSearchFocusChange = {viewModel.searchState.focused = it},
                    onClearQuery = {viewModel.searchState.query = TextFieldValue("") },
                    searching = viewModel.searchState.searching,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                )
                BookDiaryDivider(modifier = Modifier.padding(top = 56.dp))
                SwipeContentButton(
                    contentType = contentType,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 70.dp),
                    onClick = {
                        contentType = if (contentType == RecordType.MYBOOK) RecordType.WISH else RecordType.MYBOOK
                    }
                )
                BannersAds(modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .align(Alignment.BottomCenter)
                )
            }
        }
    }
}

@Composable
fun SwipeContentButton(
    contentType: RecordType,
    modifier: Modifier,
    onClick: () -> Unit
) {
    BasicButton(
        onClick = onClick,
        modifier = modifier,
        border = BorderStroke(width = 1.dp, color = Color.Black)
    ) {
        androidx.compose.material3.Icon(
            modifier = Modifier.weight(1f),
            imageVector = if (contentType == RecordType.MYBOOK) Icons.Filled.Edit else Icons.Filled.ShoppingCart,
            contentDescription = "swipe_content"
        )
        Text(
            modifier = Modifier.weight(1f),
            text = if (contentType == RecordType.MYBOOK) stringResource(id = R.string.str_change_wish) else stringResource(id = R.string.str_change_record),
            color = BookDiaryTheme.colors.textLink,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun BookRecordContent(
    animationVisible: Boolean,
    contentTitle: String,
    books: List<BasicBookRecord>?,
    onBookClick: (Long) -> Unit,
    onBookDeleteSwipe: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = animationVisible,
        modifier = Modifier,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        Box(modifier = modifier.padding(top = 60.dp)) {
            MyRecordDivider(modifier = Modifier.fillMaxWidth())
            if (books != null) {
                BookRecordRow(
                    books = books,
                    onBookClick = onBookClick,
                    onBookDeleteSwipe = onBookDeleteSwipe
                )
            } else Spacer(modifier = Modifier.height(540.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 24.dp)
                    .background(color = Color.White)
                    .border(width = 1.dp, color = BookDiaryTheme.colors.brand)
            ) {
                Text(
                    text = contentTitle,
                    style = MaterialTheme.typography.titleLarge,
                    color = BookDiaryTheme.colors.brand,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .width(160.dp)
                        .padding(start = 16.dp)
                    //.wrapContentWidth(Alignment.Start)
                )
            }
            MyRecordDivider(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            )
        }
    }
}

//random 으로 책 그림 다양하게 -> 스크롤간 재생성으로 id값의 % 로 지정
@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun BookRecordRow(
    books: List<BasicBookRecord>,
    onBookClick: (Long) -> Unit,
    onBookDeleteSwipe: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.height(540.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom,
        userScrollEnabled = true
    ) {
        items(books, key = { it.itemId }) { book ->
            val dismissState = rememberDismissState(
                confirmStateChange = { dismissValue ->
                    when (dismissValue) {
                        DismissValue.Default -> {
                            false
                        }

                        DismissValue.DismissedToStart -> { // swipe top
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(100)
                                onBookDeleteSwipe(book.itemId)
                            }
                            true
                        }

                        DismissValue.DismissedToEnd -> { // swipe bottom
                            false
                        }
                    }
                }
            )
            // row 버전 swipe 함수
            SwipeToDismissVertical(
                modifier = Modifier.animateItemPlacement(),
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),
                dismissThresholds = { dismissDirection ->
                    FractionalThreshold(0.4f)
                },
                background = {
                    val color by animateColorAsState(
                        when (dismissState.targetValue) {
                            DismissValue.Default -> Color.Transparent
                            // swipe 방향에 따른 ui 변경 배경 색상
                            DismissValue.DismissedToEnd -> Color.Blue
                            DismissValue.DismissedToStart -> Color.Red
                        }, label = ""
                    )
                    val alignment = Alignment.CenterEnd
                    val icon = if (dismissState.targetValue == DismissValue.DismissedToStart) Icons.Default.Delete
                    else Icons.Default.Edit

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
                            contentDescription = "Swipe Icon",
                            modifier = Modifier.scale(scale)
                        )
                    }
                }
            ) {
                BookDraw(
                    bookId = book.itemId,
                    bookTitle = book.title,
                    onBookClick = onBookClick
                )
            }
        }
    }
}

@Composable
fun BookDraw(
    bookId: Long,
    bookTitle: String,
    onBookClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    // id값 기준 높이, 색상 지정
    val bookColor: Color = when ((bookId % 4).toInt()) {
        0 -> Color(color = 0xFFA73933)
        1 -> Color(color = 0xFFDDAB88)
        2 -> Color(color = 0xFF50A154)
        else -> Color(color = 0xFF735549)
    }
    val labelColor: Color = when ((bookId % 4).toInt()) {
        0 -> Color(color = 0xFFFECC00)
        1 -> Color(color = 0xFFD5CEC9)
        2 -> Color(color = 0xFF353333)
        else -> Color(color = 0xFF41393C)
    }
    val bookHeightRatio = when ((bookId % 4).toInt()) {
        0 -> 10f
        1 -> 20f
        2 -> 30f
        else -> 40f
    }
    Text(
        text = bookTitle.textChangeVertical(),
        style = MaterialTheme.typography.titleLarge,
        maxLines = 20,
        color = BookDiaryTheme.colors.textPrimary,
        textAlign = TextAlign.Center,
        modifier = modifier
            .border(1.dp, Color.Black, RoundedCornerShape(10))
            .width(75.dp)
            .height(540.dp - bookHeightRatio.dp) //  offSet 값 고려 높이값 460 기준으로 계산
            .background(color = bookColor, shape = RoundedCornerShape(10))
            .drawBehind {
                drawRect(
                    color = labelColor,
                    size = Size(width = 75.dp.toPx(), height = 20.dp.toPx()),
                    topLeft = Offset(x = 0f, y = bookHeightRatio)
                )
            }
            .clickable {
                onBookClick(bookId)
            }
    )
}
