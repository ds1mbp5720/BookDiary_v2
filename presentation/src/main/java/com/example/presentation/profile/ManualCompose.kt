package com.example.presentation.profile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mylibrary.R
import com.example.presentation.components.BasicButton
import com.example.presentation.components.pager.DotGraphic
import com.example.presentation.components.pager.DotsIndicator
import com.example.presentation.components.pager.ShiftIndicatorType
import com.example.presentation.theme.BookDiaryTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ManualViewPager() {
    val pageCount by remember { mutableIntStateOf(3) }
    Column {
        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f
        ){
            pageCount
        }
        HorizontalPager(
            modifier = Modifier,
            state = pagerState
        ) {
            when (pagerState.currentPage){ // todo 이미지 넣기
                0 -> ManualImageItem(imgResId = R.drawable.book_24)
                1 -> ManualImageItem(imgResId = R.drawable.book_24)
                2 -> ManualImageItem(imgResId = R.drawable.book_24)
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        ShiftDotIndicators(pageCount = pageCount, pagerState = pagerState, modifier = Modifier.padding(top = 24.dp))
        Spacer(modifier = Modifier.height(15.dp))
        var desc = ""
        when(pagerState.currentPage){
            0 -> desc = stringResource(id = R.string.str_manual_desc1)
            1 -> desc = stringResource(id = R.string.str_manual_desc2)
            2 -> desc = stringResource(id = R.string.str_manual_desc3)
        }
        Text(
            text = desc,
            style = MaterialTheme.typography.titleLarge,
            color = BookDiaryTheme.colors.textHelp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(15.dp))
        val buttonScope = rememberCoroutineScope()
        ManualPageMoveButton(
            modifier = Modifier.padding(horizontal = 28.dp),
            prevListener = {
                if(pagerState.currentPage == 0){
                    return@ManualPageMoveButton
                }
                val prevPage = pagerState.currentPage - 1
                buttonScope.launch{
                    pagerState.animateScrollToPage(prevPage)
                }
            },
            nextListener = {
                if(pagerState.currentPage == pagerState.pageCount - 1){
                    return@ManualPageMoveButton
                }
                val nextPage = pagerState.currentPage + 1
                buttonScope.launch{
                    pagerState.animateScrollToPage(nextPage)
                }
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ShiftDotIndicators(pageCount: Int, pagerState: PagerState, modifier: Modifier){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        DotsIndicator(
            dotCount = pageCount,
            type = ShiftIndicatorType(
                dotGraphic = DotGraphic(
                    selectedStateColor = BookDiaryTheme.colors.brand,
                    unSelectedStateColor = Color.Gray,
                    size = 6.dp
                )
            ),
            pagerState = pagerState)
    }
}
@Composable
private fun ManualImageItem(
    @DrawableRes imgResId: Int = R.drawable.book_24 // todo 임시 text 이미지
){
    Image(
        modifier = Modifier
            .height(400.dp),
        painter = painterResource(id = imgResId),
        contentScale = ContentScale.Crop,
        contentDescription = "manual_image",
    )
}
@Composable
private fun ManualPageMoveButton(
    modifier: Modifier = Modifier,
    prevListener: () -> Unit = {},
    nextListener: () -> Unit = {}
){
    Row(
       modifier = modifier
           .fillMaxWidth()
           .wrapContentHeight(Alignment.CenterVertically)
    ){
        BasicButton(
            onClick = prevListener,
            modifier = Modifier.weight(1f),
            border = BorderStroke(width = 1.dp, color = Color.Black)
        ) {
            Icon(
                modifier = Modifier.weight(1f),
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "manual_prev")
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.str_prev),
                color = BookDiaryTheme.colors.textLink,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        BasicButton(
            onClick = nextListener,
            modifier = Modifier.weight(1f),
            border = BorderStroke(width = 1.dp, color = Color.Black)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.str_next),
                color = BookDiaryTheme.colors.textLink,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.End
            )
            Icon(
                modifier = Modifier.weight(1f),
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "manual_next")
        }

    }
}