package com.example.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.presentation.theme.BookDiaryTheme
import com.example.presentation.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicEditText(
    modifier: Modifier,
    hint: String,
    preText: String = "",
    empty: Boolean = false,
    emptyText: String = "",
    emptyModifier: Modifier,
    updateText: (String) -> Unit
) {
    var inputText by remember { mutableStateOf(TextFieldValue(preText)) }
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = BookDiaryTheme.colors.brand,
                shape = RoundedCornerShape(30.dp)
            ),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(30.dp),
        value = inputText,
        onValueChange = {
            inputText = it
            updateText.invoke(it.text)
        },
        textStyle = Typography.labelLarge,
        placeholder = {
            Text(
                text = hint,
                style = Typography.labelLarge,
                color = Color.Gray
            )
        }
    )
    if(empty){
        Text(
            modifier = emptyModifier,
            text = emptyText,
            color = Color.Red
        )
    } else {
        Spacer(modifier = emptyModifier)
    }
}