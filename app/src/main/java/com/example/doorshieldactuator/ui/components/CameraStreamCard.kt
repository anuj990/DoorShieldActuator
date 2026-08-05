package com.example.doorshieldactuator.ui.components


import android.annotation.SuppressLint
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun CameraStreamCard(
    streamUrl: String,
    modifier: Modifier = Modifier
) {

    GlassSurface(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp),
        cornerRadius = 22.dp,
        elevation = 8.dp,
        contentPadding = PaddingValues(0.dp)
    ) {

        Box(
            contentAlignment = Alignment.Center
        ) {

            AndroidView(
                modifier = Modifier.fillMaxWidth(),
                factory = { context ->

                    WebView(context).apply {

                        settings.javaScriptEnabled = true
                        settings.cacheMode = WebSettings.LOAD_NO_CACHE
                        settings.loadsImagesAutomatically = true
                        settings.domStorageEnabled = true

                        webViewClient = WebViewClient()

                        loadUrl(streamUrl)
                    }
                },
                update = { webView ->

                    if (webView.url != streamUrl) {

                        webView.loadUrl(streamUrl)
                    }
                }
            )
        }
    }
}