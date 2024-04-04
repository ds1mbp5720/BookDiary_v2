package com.example.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.mylibrary.R
import com.example.presentation.theme.BookDiaryTheme

/**
 * 리스트 내부 책 정보 UI 포장 목적 View
 */
@Composable
fun BookCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    color: Color = BookDiaryTheme.colors.uiBackground,
    contentColor: Color = BookDiaryTheme.colors.textPrimary,
    border: BorderStroke? = null,
    elevation: Dp = 4.dp,
    content: @Composable () -> Unit
) {
    BookDiarySurface(
        modifier = modifier,
        shape = shape,
        color = color,
        contentColor = contentColor,
        border = border,
        elevation = elevation,
        content = content
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GlideCard(
    imageUrl: String,
    contentDescription: String = "",
    contentScale: ContentScale = ContentScale.FillHeight,
    elevation: Dp = 0.dp,
    border: BorderStroke? = null,
    modifier: Modifier = Modifier,
    glideModifier: Modifier = Modifier,
) {
    BookDiarySurface(
        color = Color.White,
        elevation = elevation,
        shape = RectangleShape,
        modifier = modifier,
        border = border
    ) {
        GlideImage(
            model = imageUrl,
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = glideModifier,
        ) {
            it.error(R.drawable.book_24)
                .placeholder(R.drawable.book_24)
                .load(imageUrl)
        }
    }
}