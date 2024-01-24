package com.example.presentation.record

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.domain.model.MyBookModel
import com.example.presentation.components.BookDiaryScaffold
import com.example.presentation.components.BookDiarySurface
import com.example.presentation.graph.BookDiaryBottomBar
import com.example.presentation.graph.MainSections
import com.example.presentation.theme.BookDiaryTheme
import com.example.presentation.util.textChangeVertical
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
                    contentTitle = "내 책 목록 테스트",
                    books = books,
                    onBookClick = onBookClick
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
        if (books != null) {
            BookRecordRow(
                books = books,
                onBookClick = onBookClick
            )
        }
    }
}

//random 으로 책 그림 다양하게
@Composable
fun BookRecordRow(
    books: List<MyBookModel>,
    onBookClick: (Long) -> Unit,
    modifier: Modifier = Modifier
){
    val scroll = rememberScrollState(0)
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom,
        userScrollEnabled = true
    ){
        items(books){book ->
            BookDraw(
                bookId = book.itemId,
                bookTitle = book.title,
                )
        }
    }
}

@Composable
fun BookDraw(
    bookId: Long,
    bookTitle: String,
    modifier: Modifier = Modifier,

){
    val rotateYN: Boolean = when(Random.nextInt(5)){
        0 -> false
        else -> true
    }
    val bookColor: Color = when((bookId % 4).toInt()){
        0 -> Color(color = 0xFFA73933)
        1 -> Color(color = 0xFFDDAB88)
        2 -> Color(color = 0xFFFEFEFE)
        else -> Color(color = 0xFF3C7F23)
    }
    val labelColor: Color = when((bookId % 4).toInt()){
        0 -> Color(color = 0xFFFECC00)
        1 -> Color(color = 0xFFD5CEC9)
        2 -> Color(color = 0xFF353333)
        else -> Color(color = 0xFF80E4A4)
    }
    /*Canvas(
        modifier = modifier
    ) {
        drawRoundRect(
            color = color,
            size = Size(width = 100.dp.toPx(), height = 100.dp.toPx()),
            cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
        )
    }*/

    Text(
        text = bookTitle.textChangeVertical(),
        maxLines = 20,
        color = BookDiaryTheme.colors.textPrimary,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .rotate(degrees = if(rotateYN) 0f else -5f)
            .border(1.dp, Color.Black, RoundedCornerShape(1))
            .width(90.dp)
            .height(400.dp)
            .background(color = bookColor, shape = RoundedCornerShape(10))
            .drawBehind {
                drawRect(
                    color = labelColor,
                    size = Size(width = 90.dp.toPx(), height = 20.dp.toPx()),
                    topLeft = Offset(x = 0f, y = 60f) // todo y 높이 변화에 라벨지 그림 높이도 조정하기
                )
            }
    )
}
