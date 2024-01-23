package com.example.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue

class SearchState(
    query: TextFieldValue,
    focused: Boolean,
    searching: Boolean
) {
    var query by mutableStateOf(query)
    var focused by mutableStateOf(focused)
    var searching by mutableStateOf(searching)
}

@Composable
fun rememberSearchState(
    query: TextFieldValue = TextFieldValue(""),
    focused: Boolean = false,
    searching: Boolean = false
): SearchState{
    return remember{
        SearchState(
            query = query,
            focused = focused,
            searching = searching
        )
    }
}