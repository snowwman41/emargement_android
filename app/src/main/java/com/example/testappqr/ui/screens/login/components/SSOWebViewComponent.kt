package com.example.testappqr.ui.screens.login.components

import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebStorage
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.testappqr.SharedApiResponseModel
import com.example.testappqr.models.ApiSSOResponse
import com.example.testappqr.network.RetrofitApi
import kotlinx.coroutines.runBlocking
import java.io.ByteArrayInputStream

@Composable
fun SSOWebViewComponent(
    navController: NavHostController,
    sharedApiResponseModel: SharedApiResponseModel
) {
    var isLoading by remember { mutableStateOf(true) }
    var webViewError by remember { mutableStateOf(false) }
    val ssoUrl = "http://10.0.2.2:8080/auth/cas"
    var shouldNavigate by remember { mutableStateOf(false) }


    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                WebView(ctx).apply {
                    settings
                        .apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        setSupportZoom(true)
                        builtInZoomControls = true
                        displayZoomControls = false
                        loadWithOverviewMode = true
                        useWideViewPort = true
                    }
                      val cookieManager = CookieManager.getInstance()
                    cookieManager.setAcceptCookie(true)
                    cookieManager.setAcceptThirdPartyCookies(this, true)
                    cookieManager.flush()


                    webViewClient = object : WebViewClient() {
                        override fun onReceivedError(
                            view: WebView?,
                            request: WebResourceRequest?,
                            error: WebResourceError?
                        ) {
                            webViewError = true
                            isLoading = false
                        }
                        //intercept to go out of the webview, otherwise we receive data in webview and we can't interact with it
                        override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
                            val requestUrl = request?.url.toString()
                            if (requestUrl.startsWith("http://10.0.2.2:8080/auth/cas/validate")) { // ✅ Detect JSON request

                                sharedApiResponseModel.apiSSOResponse = handleValidationRequest(requestUrl)
                                if (sharedApiResponseModel.apiSSOResponse != null){
                                    shouldNavigate = true
                                }
                                return WebResourceResponse(
                                    "text/plain",
                                    "UTF-8",
                                    ByteArrayInputStream("".toByteArray()) // Empty response to prevent the default one
                                )
                            }
                            return super.shouldInterceptRequest(view, request) // give the ok the intercept
                        }

                        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                            val url = request?.url?.toString() ?: return false
                            if (url.startsWith("http://localhost:8080")) {
                                val newUrl = url.replace("localhost", "10.0.2.2")
                                    .replace("127.0.0.1", "10.0.2.2")
                                view?.loadUrl(newUrl) // Load the modified URL
                                return true //override : true , continue with request, either modified
                            }
                           return false //override : false , continue with unmodified request
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            isLoading = false
                        }
                    }

                    webChromeClient = WebChromeClient()
                    loadUrl(ssoUrl)
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        if (webViewError) {
            Text(
                text = "Error loading content",
                modifier = Modifier.align(Alignment.Center)
            )
        }
        if (shouldNavigate) {
            LaunchedEffect (Unit){

                navController.navigate("home") {
                    popUpTo("login") {
                        inclusive = true
                    }

                }

            }
        }

    }

}

private fun handleValidationRequest(url: String): ApiSSOResponse? {
    return runBlocking {
        try {
            val response = RetrofitApi.api.casValidate(url)
            response
        } catch (e: Exception) {
             null
        }
    }
}