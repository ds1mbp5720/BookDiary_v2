package com.example.presentation.record.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.presentation.components.DetailHeader
import com.example.presentation.components.book.BookCoverImage
import com.example.presentation.record.RecordViewModel
import com.example.presentation.theme.BookDiaryTheme

@Composable
fun RecordDetailScreen(
    modifier: Modifier = Modifier,
    upPress: () -> Unit,
    recordViewModel: RecordViewModel
) {
    val context = LocalContext.current
    val bookDetailInfo = recordViewModel.myBookDetail.observeAsState().value
        DetailHeader()
        if (bookDetailInfo != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Title(
                        title = bookDetailInfo.title,
                        author = bookDetailInfo.author,

                        )
                    BookCoverImage(
                        imageUrl = bookDetailInfo.imageUrl,
                        modifier = Modifier.size(100.dp),
                        contentDescription = "record_detail_cover")
                }
                Body(
                    period = bookDetailInfo.period,
                    myReview = bookDetailInfo.myReview ?: "기록한 리뷰가 업습니다."
                )
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize()
            ){
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    text = "책 정보가 없습니다."
                )
            }
        }
}

@Composable
private fun Title(
    title: String,
    author: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            maxLines = 2,
            modifier = Modifier
        )
        Text(
            text = author,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            maxLines = 2,
            modifier = Modifier
        )
    }
}

@Composable
private fun Body(
    period: String,
    myReview: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Text(
            text = period,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(10.dp)
                )
                .background(
                    color = BookDiaryTheme.colors.uiBackground,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(5.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = myReview,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}


@Preview
@Composable
private fun PreViewTitle() {
    Title(
        title = "테스트 제목",
        author = "테스트 저자"
    )
}

@Preview
@Composable
private fun PreViewBody() {
    Body(
        period = "2024.02.02 ~ 2024.02.02",
        myReview = "테스트 리뷰")
}