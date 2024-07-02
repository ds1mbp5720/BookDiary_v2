package com.example.presentation.record.detail

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mylibrary.R
import com.example.presentation.bookdetail.Image
import com.example.presentation.bookdetail.Title
import com.example.presentation.bookdetail.minTitleOffset
import com.example.presentation.bookdetail.titleHeight
import com.example.presentation.components.BasicUpButton
import com.example.presentation.components.BookDiarySurface
import com.example.presentation.components.DetailHeader
import com.example.presentation.record.RecordViewModel
import com.example.presentation.theme.ReviewPaperColor

@Composable
fun RecordDetailScreen(
    modifier: Modifier = Modifier,
    upPress: () -> Unit,
    recordViewModel: RecordViewModel
) {
    val bookDetailInfo = recordViewModel.myBookDetail.observeAsState().value
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        val scroll = rememberScrollState(0)
        DetailHeader()
        if (bookDetailInfo != null) {
            Body(
                myReview = bookDetailInfo.myReview ?: "기록한 리뷰가 업습니다.",
                scroll = scroll,
                modifier = Modifier.fillMaxWidth()
            )
            Title(
                title = bookDetailInfo.title,
                author = bookDetailInfo.author,
                period = bookDetailInfo.period
            ) {
                1000 //scroll.value
            }
            Image(imageUrl = bookDetailInfo.imageUrl) {
                1000 //scroll.value
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    text = "책 정보가 없습니다."
                )
            }
        }
        BasicUpButton(upPress)
    }
}

@Composable
private fun Body(
    myReview: String,
    scroll: ScrollState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(minTitleOffset)
        )
        Column(
            modifier = Modifier.verticalScroll(scroll),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BookDiarySurface(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(7.dp)
                        )
                        .background(
                            color = ReviewPaperColor,
                            shape = RoundedCornerShape(7.dp)
                        )
                ) {
                    Spacer(modifier = Modifier.height(titleHeight + 40.dp))
                    Text(
                        text = stringResource(id = R.string.str_record_title),
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp),
                    )
                    Text(
                        text = myReview,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp, vertical = 20.dp)
                            .padding(
                                bottom = if (myReview.length < 1000) 450.dp else 0.dp
                            ),
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreViewBody() {
    Body(
        myReview = "테스트 리뷰",
        scroll = rememberScrollState(0)
    )
}