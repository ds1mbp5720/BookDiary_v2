package com.example.presentation.appinfo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.presentation.components.BasicSwitch
import com.example.presentation.components.BookDiarySurface

@Composable
fun SettingScreen() {
    BookDiarySurface(
        modifier = Modifier.fillMaxSize()
    ) {
        var testCheck by remember { mutableStateOf(false) }

        BasicSwitch(
            text = "Test",
            checked = testCheck,
            onChecked = {
                testCheck = it
            }
        )
    }

}