package com.example.presentation.components

import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.presentation.theme.BookDiaryTheme

@Composable
fun BookDiaryDivider(
    modifier: Modifier = Modifier,
    color: Color = BookDiaryTheme.colors.uiBorder.copy(alpha = 0.12f),
    thickness: Dp = 1.dp
){
    Divider(
        modifier = modifier,
        color = color,
        thickness = thickness,
    )
}