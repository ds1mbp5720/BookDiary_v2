package com.example.presentation.components.pager

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.times
import kotlin.math.absoluteValue

class ShiftIndicatorType(
    private val dotGraphic: DotGraphic = DotGraphic(),
    private val shiftSizeFactor: Float = 3f
) : IndicatorType() {
    @Composable
    override fun IndicatorTypeComposable(
        globalOffsetProvider: () -> Float,
        modifier: Modifier,
        dotCount: Int,
        dotSpacing: Dp,
        onDotClicked: ((Int) -> Unit)?
    ) {
        Box(modifier = modifier) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = dotSpacing,
                    alignment = Alignment.CenterHorizontally
                ),
                contentPadding = PaddingValues(start = dotSpacing, end = dotSpacing)
            ) {
                items(dotCount) { dotIndex ->
                    // 스크롤 값을 가져와서 modifier width 확장 및 원복
                    val dotWidth by remember(globalOffsetProvider()) {
                        derivedStateOf { computeDotWidth(dotIndex, globalOffsetProvider()) }
                    }
                    val dotModifier by remember(dotWidth) {
                        mutableStateOf(
                            Modifier
                                .width(dotWidth)
                                .clickable { onDotClicked?.invoke(dotIndex) }
                        )
                    }
                    Dot(graphic = dotGraphic, dotWidth = dotWidth, modifier = dotModifier)
                }
            }
        }
    }

    // 현재 화면 position dot 폭 확장 함수
    private fun computeDotWidth(currentDotIndex: Int, globalOffset: Float): Dp {
        val diffFactor = 1f - (currentDotIndex - globalOffset).absoluteValue.coerceAtMost(1f)
        val widthToAdd = ((shiftSizeFactor - 1f).coerceAtLeast(0f) * dotGraphic.size * diffFactor)
        return dotGraphic.size + widthToAdd
    }
}