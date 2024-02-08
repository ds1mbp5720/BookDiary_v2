package com.example.presentation.profile

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.presentation.components.BookDiarySurface
import com.example.presentation.components.SettingButton
import com.example.presentation.graph.BookDiaryBottomBar
import com.example.presentation.graph.MainSections
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

@Composable
fun Profile(
    onBookClick: (Long) -> Unit,
    onNavigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        bottomBar = {
            BookDiaryBottomBar(
                tabs = MainSections.values(),
                currentRoute = MainSections.PROFILE.route,
                navigateToRoute = onNavigateToRoute
            )
        },
        modifier = modifier
    ) {paddingValues ->
        ProfileScreen(
            onBookClick = onBookClick,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun ProfileScreen(
    onBookClick: (Long) -> Unit,
    modifier: Modifier = Modifier
){
    val context = LocalContext.current
    BookDiarySurface {
        Column(
            modifier = modifier.fillMaxSize()
        ){
            SettingButton(
                onClick = {
                    val intent = Intent(context, OssLicensesMenuActivity::class.java)
                    context.startActivity(intent)
                },
                text = "오픈소스 라이선스"
            )
            Spacer(modifier = Modifier.height(7.dp))
            SettingButton(
                onClick = {
                    //todo: 앱 사용방법 (가로 스크롤 방식, 하단 점들로 표시)
                },
                text = "앱 사용 방법"
            )
            Spacer(modifier = Modifier.height(7.dp))
            SettingButton(
                onClick = {
                    //todo: 이전 버전 기록들
                },
                text = "버전 ${context.packageManager.getPackageInfo(context.packageName,0).versionName}"
            )
        }
    }
}