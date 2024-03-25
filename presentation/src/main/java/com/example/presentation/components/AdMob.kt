package com.example.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

/**
 * Google AdBob UI ( BannerType)
 */
@Composable
fun BannersAds(
    modifier: Modifier
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.FULL_BANNER)
                adUnitId = "ca-app-pub-3940256099942544/6300978111" //todo 등록 전 변경하기
                loadAd(AdRequest.Builder().build())
            }
        },
        update = { adView ->
            adView.loadAd(AdRequest.Builder().build())
        }
    )
}