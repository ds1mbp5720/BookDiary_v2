package com.example.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mylibrary.R
import com.example.presentation.theme.BookDiaryTheme

@Composable
fun BookDiaryBasicDialog(
    title: String = "",
    body: String = "",
    dismissAction: () -> Unit,
    confirmAction: () -> Unit
) {
    AlertDialog(
        containerColor = BookDiaryTheme.colors.uiBackground,
        onDismissRequest = dismissAction,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = BookDiaryTheme.colors.textLink,
            )
        },
        text = {
            Text(
                text = body,
                style = MaterialTheme.typography.bodyLarge,
                color = BookDiaryTheme.colors.textLink,
            )
        },
        dismissButton = {
            BasicButton(
                onClick = dismissAction,
                modifier = Modifier,
                // colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                border = BorderStroke(width = 1.dp, color = Color.Black)
            ) {
                Text(
                    text = stringResource(id = R.string.str_cancel),
                    modifier = Modifier.fillMaxWidth(),
                    color = BookDiaryTheme.colors.textLink,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        },
        confirmButton = {
            BasicButton(
                onClick = confirmAction,
                modifier = Modifier,
                //colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                border = BorderStroke(width = 1.dp, color = Color.Black)
            ) {
                Text(
                    text = stringResource(id = R.string.str_confirm),
                    modifier = Modifier.fillMaxWidth(),
                    color = BookDiaryTheme.colors.textLink,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    )
}