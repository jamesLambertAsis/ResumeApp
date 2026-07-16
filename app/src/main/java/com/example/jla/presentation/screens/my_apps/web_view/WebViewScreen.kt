package com.example.jla.presentation.screens.my_apps.web_view


import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.jla.presentation.screens.composables.BackButton


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
        BackButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            enableBgColor = true
        ) {
            onBack()
        }
    }
}