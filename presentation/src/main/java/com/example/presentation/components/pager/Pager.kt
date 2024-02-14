package com.example.presentation.components.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

data class DotGraphic(
    val size: Dp = 16.dp,
    val selectedStateColor: Color = Color.White,
    val unSelectedStateColor: Color = Color.Gray,
    val shape: Shape = CircleShape,
    val borderWidth: Dp? = null,
    val borderColor: Color = Color.White
)
@Composable
fun Dot(
    graphic: DotGraphic,
    dotWidth: Dp,
    modifier: Modifier
) {
    val shapeColor = if(dotWidth.value.toInt() == graphic.size.value.toInt()){
        graphic.unSelectedStateColor
    } else {
        graphic.selectedStateColor
    }

    Box(
        modifier = modifier
            .background(
                color = shapeColor,
                shape = graphic.shape)
            .size(graphic.size)
            .let {
                graphic.borderWidth?.let { borderWidth ->
                    it.border(
                        width = borderWidth,
                        color = graphic.borderColor,
                        shape = graphic.shape
                    )
                } ?: it
            }
    )
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DotsIndicator(
    dotCount: Int,
    modifier: Modifier = Modifier,
    dotSpacing: Dp = 12.dp,
    type: IndicatorType,
    pagerState: PagerState
){
    val coroutineScope = rememberCoroutineScope()
    DotsIndicator(
        dotCount = dotCount,
        modifier = modifier,
        dotSpacing = dotSpacing,
        type = type,
        currentPage = pagerState.currentPage,
        currentPageOffsetFraction = { pagerState.currentPageOffsetFraction }
    ){ dotIndex ->
        coroutineScope.launch { pagerState.animateScrollToPage(dotIndex) }
    }
}
@Composable
fun DotsIndicator(
    dotCount: Int,
    modifier: Modifier = Modifier,
    dotSpacing: Dp = 12.dp,
    type: IndicatorType,
    currentPage: Int,
    currentPageOffsetFraction: () -> Float,
    onDotClicked: ((index: Int) -> Unit)? = null
) {
    val globalOffset by remember(dotCount, currentPage, currentPageOffsetFraction()) {
        derivedStateOf {
            computeGlobalScrollOffset(currentPage, currentPageOffsetFraction(), dotCount)
        }
    }
    type.IndicatorTypeComposable(globalOffsetProvider = { globalOffset }, modifier = modifier, dotCount = dotCount, dotSpacing = dotSpacing, onDotClicked = onDotClicked)
}

private fun computeGlobalScrollOffset(position: Int, positionOffset: Float, totalCount: Int): Float{
    var offset = position + positionOffset
    val lastPageIndex = (totalCount - 1).toFloat()
    if(offset == lastPageIndex) {
        offset = lastPageIndex - .0001f
    }
    val leftPosition = offset.toInt()
    val rightPosition = leftPosition + 1
    if(rightPosition > lastPageIndex || leftPosition < 0)
        return 0f

    return leftPosition + offset % 1
}