package com.example.presentation.appinfo

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mylibrary.R
import com.example.presentation.components.AppInfoButton
import com.example.presentation.components.BookDiarySurface
import com.example.presentation.graph.BookDiaryBottomBar
import com.example.presentation.graph.MainSections
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

@Composable
fun AppInfo(
    onManualClick: () -> Unit,
    onNavigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        bottomBar = {
            BookDiaryBottomBar(
                tabs = MainSections.values(),
                currentRoute = MainSections.AppInfo.route,
                navigateToRoute = onNavigateToRoute
            )
        },
        modifier = modifier
    ) { paddingValues ->
        ProfileScreen(
            onManualClick = onManualClick,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun ProfileScreen(
    onManualClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    BookDiarySurface {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 20.dp, start = 12.dp, end = 12.dp)
        ) {
            AppInfoButton(
                onClick = {
                    val intent = Intent(context, OssLicensesMenuActivity::class.java)
                    context.startActivity(intent)
                },
                text = stringResource(id = R.string.str_opensource_license)
            )
            Spacer(modifier = Modifier.height(7.dp))
            AppInfoButton(
                onClick = {
                    onManualClick()
                },
                text = stringResource(id = R.string.str_app_manual)
            )
            Spacer(modifier = Modifier.height(7.dp))
            AppInfoButton(
                onClick = {
                    //todo: 이전 버전 기록들
                },
                text = stringResource(id = R.string.str_app_version, context.packageManager.getPackageInfo(context.packageName, 0).versionName)
            )
        }
    }
}