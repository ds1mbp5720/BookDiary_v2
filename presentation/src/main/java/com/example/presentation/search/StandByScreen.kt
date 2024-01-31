package com.example.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.presentation.theme.BookDiaryTheme

@Composable
fun StandByScreen(
   viewModel: SearchViewModel = viewModel()
){
    val searchHistory = viewModel.searchHistory.collectAsState().value
    if(searchHistory.isEmpty()){
        Text(
            modifier = Modifier.fillMaxSize(),
            text = "검색기록이 없습니다.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 20.dp, bottom = 20.dp),
        userScrollEnabled = true
    ){
        item {
            Text(
                modifier = Modifier.fillMaxSize(),
                text = "검색기록",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
        items(searchHistory.size ?: 0){
            Card(
                modifier = Modifier
                    .padding(horizontal = 6.dp, vertical = 6.dp)
                    .fillMaxWidth()
                    .clickable {
                        viewModel.searchState.query = TextFieldValue(searchHistory[it])
                        viewModel.getSearchBookList(searchHistory[it],100)
                        viewModel.searchState.searching = true
                    },
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                colors = CardDefaults.cardColors(containerColor = BookDiaryTheme.colors.uiBackground),
                shape = RoundedCornerShape(corner = CornerSize(16.dp))
            ){
                Row(
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp, top = 6.dp, bottom = 6.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = searchHistory[it],
                        style = MaterialTheme.typography.labelLarge,
                        color = BookDiaryTheme.colors.textLink
                    )
                }
            }
        }
    }
}