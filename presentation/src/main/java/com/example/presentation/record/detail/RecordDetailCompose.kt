package com.example.presentation.record.detail

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.example.presentation.record.RecordViewModel

@Composable
fun RecordDetailScreen(
    modifier: Modifier = Modifier,
    upPress: () -> Unit,
    recordViewModel: RecordViewModel
) {
    val bookDetailInfo = recordViewModel.myBookDetail.observeAsState().value
    if (bookDetailInfo != null) {
        Log.e("","상세보기 클릭 상태 체크 ${bookDetailInfo.title}")
    }
}