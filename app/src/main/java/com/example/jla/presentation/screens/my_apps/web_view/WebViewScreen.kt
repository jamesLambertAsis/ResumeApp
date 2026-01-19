package com.example.jla.presentation.screens.my_apps.web_view


import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import com.example.jla.R
import com.example.jla.ui.theme.ShadeBlue


@Composable
fun WebView(
    onBack: () -> Unit
) {
    val context = LocalContext.current

    val url = remember { mutableStateOf("https://www.google.com/m") }

    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    val webView = remember {
        android.webkit.WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )

            settings.setJavaScriptEnabled(true)
            webViewClient = WebViewClient()

            loadUrl(url.value)
        }
    }

    DisposableEffect(webView) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack()
                }
            }

        }

        onBackPressedDispatcher?.addCallback(callback)

        onDispose { callback.remove() }
    }

    Box(
        Modifier.fillMaxSize()
    ) {

        AndroidView(
            factory = { webView },
            modifier = Modifier.fillMaxSize()
        )
        Box(
            Modifier
                .padding()
                .padding(end = 20.dp, bottom = 20.dp)
                .align(Alignment.BottomEnd)
        ) {
            Box(
                Modifier
                    .clip(CircleShape)
                    .background(Color(ShadeBlue.value))
                    .clickable {
                        onBack()
                    }

                    .zIndex(1f)
            ) {
                Icon(
                    modifier = Modifier.padding(12.dp),
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = null
                )
            }
        }
    }
}