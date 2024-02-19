package com.example.presentation.record

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.domain.model.BookModel
import com.example.domain.model.MyBookModel
import com.example.mylibrary.R
import com.example.presentation.components.BookDiaryBasicDialog
import com.example.presentation.components.BookDiaryScaffold
import com.example.presentation.components.BookDiarySurface
import com.example.presentation.components.MyRecordDivider
import com.example.presentation.components.SwipeToDismissVertical
import com.example.presentation.graph.BookDiaryBottomBar
import com.example.presentation.graph.MainSections
import com.example.presentation.theme.BookDiaryTheme
import com.example.presentation.util.textChangeVertical
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.annotation.meta.When
import kotlin.random.Random
import kotlin.random.nextInt

@Composable
fun Record(
    onBookClick: (Long) -> Unit,
    onNavigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecordViewModel = viewModel()
) {
    val myBookList = viewModel.myBookList.observeAsState()
    viewModel.getMyBookList()
    val books = myBookList.value
    BookDiaryScaffold(
        bottomBar = {
            BookDiaryBottomBar(
                tabs = MainSections.values(),
                currentRoute = MainSections.RECORD.route,
                navigateToRoute = onNavigateToRoute
            )
        },
        modifier = modifier
    ) {paddingValues ->
        BookDiarySurface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column {
                BookRecordContent(
                    contentTitle = "내 책 목록" + " ${books?.size ?: "0"} 권",
                    books = books,
                    onBookClick = onBookClick,
                    onBookDeleteSwipe = {id ->
                        viewModel.deleteMyBook(id)
                    }
                )
            }
        }
    }
}

@Composable
fun BookRecordContent(
    contentTitle: String,
    books: List<MyBookModel>?,
    onBookClick: (Long) -> Unit,
    onBookDeleteSwipe: (Long) -> Unit,
    modifier: Modifier = Modifier,
){
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .heightIn(min = 56.dp)
                .padding(start = 24.dp)
        ) {
            Text(
                text = contentTitle,
                style = MaterialTheme.typography.titleLarge,
                color = BookDiaryTheme.colors.brand,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        MyRecordDivider(modifier = Modifier.fillMaxWidth())
        if (books != null) {
            BookRecordRow(
                books = books,
                onBookClick = onBookClick,
                onBookDeleteSwipe = onBookDeleteSwipe
            )
        } else Spacer(modifier = Modifier.height(420.dp))
        MyRecordDivider(modifier = Modifier.fillMaxWidth())
    }
}

//random 으로 책 그림 다양하게
@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun BookRecordRow(
    books: List<MyBookModel>,
    onBookClick: (Long) -> Unit,
    onBookDeleteSwipe: (Long) -> Unit,
    modifier: Modifier = Modifier
){
    val scroll = rememberScrollState(0)
    LazyRow(
        modifier = modifier.height(420.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom,
        userScrollEnabled = true
    ){
        items(books, key = {it.itemId}){book ->
            val dismissState = rememberDismissState(
                confirmStateChange = {dismissValue ->
                    when(dismissValue){
                        DismissValue.Default ->{ false}
                        DismissValue.DismissedToStart -> { // swipe top
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(100)
                                onBookDeleteSwipe(book.itemId)
                            }
                            true
                        }
                        DismissValue.DismissedToEnd -> { false} // swipe bottom
                    }
                }
            )
            // row 버전 swipe 함수
            SwipeToDismissVertical(
                modifier = Modifier.animateItemPlacement(),
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),
                dismissThresholds =  { dismissDirection ->
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
                    val icon = if(dismissState.targetValue == DismissValue.DismissedToStart ) Icons.Default.Delete
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
){
    // id값 기준으로 높이, 색상 지정
    val bookColor: Color = when((bookId % 4).toInt()){
        0 -> Color(color = 0xFFA73933)
        1 -> Color(color = 0xFFDDAB88)
        2 -> Color(color = 0xFF50A154)
        else -> Color(color = 0xFF735549)
    }
    val labelColor: Color = when((bookId % 4).toInt()){
        0 -> Color(color = 0xFFFECC00)
        1 -> Color(color = 0xFFD5CEC9)
        2 -> Color(color = 0xFF353333)
        else -> Color(color = 0xFF41393C)
    }
    val bookHeightRatio = when((bookId % 4).toInt()){
        0 -> 40f
        1 -> 60f
        else -> 50f
    }
    Text(
        text = bookTitle.textChangeVertical(),
        maxLines = 20,
        color = BookDiaryTheme.colors.textPrimary,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .border(1.dp, Color.Black, RoundedCornerShape(10))
            .width(90.dp)
            .height((460 - bookHeightRatio).dp) //  offSet 값 고려 높이값 460 기준으로 계산
            .background(color = bookColor, shape = RoundedCornerShape(10))
            .drawBehind {
                drawRect(
                    color = labelColor,
                    size = Size(width = 90.dp.toPx(), height = 20.dp.toPx()),
                    topLeft = Offset(x = 0f, y = bookHeightRatio)
                )
            }
            .clickable {
                onBookClick(bookId)
            }
    )
}
