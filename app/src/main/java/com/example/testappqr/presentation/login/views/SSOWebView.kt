//package com.example.testappqr.presentation.login.views
//
//import android.net.Uri
//import android.webkit.CookieManager
//import android.webkit.WebChromeClient
//import android.webkit.WebResourceError
//import android.webkit.WebResourceRequest
//import android.webkit.WebResourceResponse
//import android.webkit.WebView
//import android.webkit.WebViewClient
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import androidx.navigation.NavHostController
//
//
//import com.example.testappqr.presentation.login.viewmodels.LoginVM
//import com.example.testappqr.presentation.navigation.Routes
//import com.example.testappqr.presentation.sharedviews.BasicButton
//import java.io.ByteArrayInputStream
//
//@Composable
//fun SSOWebViewComponent(
//    navController: NavHostController,
//    loginVM: LoginVM = hiltViewModel()
//) {
//    val ip = "172.25.208.1"
//    val amuSSO = "https://ident.univ-amu.fr/cas/login"
//    val validationService = "http://localhost:8080/auth/cas/validate"
//
//    val url = Uri.parse(amuSSO)
//        .buildUpon()
//        .appendQueryParameter("service", validationService)
//        .build()
//        .toString()
//
//    val loginState by loginVM.loginState.collectAsStateWithLifecycle()
//
//    Box(modifier = Modifier.fillMaxSize()) {
//
//        if (loginState.userData != null) {
//            println(loginState.userData)
//            println("inside user state not null")
//            LaunchedEffect (Unit){
//                navController.navigate(Routes.PROFESSOR_SESSIONS) {
//                    popUpTo(Routes.LOGIN) {
//                        inclusive = true
//                    }
//                    launchSingleTop = true
//                }
//            }
//        }else{
//            AndroidView(
//                factory = { ctx ->
//                    WebView(ctx).apply {
//                        settings
//                            .apply {
//                                javaScriptEnabled = true
//                                domStorageEnabled = true
//                                setSupportZoom(true)
//                                builtInZoomControls = true
//                                displayZoomControls = false
//                                loadWithOverviewMode = true
//                                useWideViewPort = true
//                            }
//                        // Enable cookies for auth
//                        val cookieManager = CookieManager.getInstance()
//                        cookieManager.setAcceptCookie(true)
//                        cookieManager.setAcceptThirdPartyCookies(this, true)
//                        cookieManager.flush()
//
//
//                        webViewClient = object : WebViewClient() {
//                            override fun onReceivedError(
//                                view: WebView?,
//                                request: WebResourceRequest?,
//                                error: WebResourceError?
//                            ) {
//                                loginVM.updateWebViewError(true)
//                                loginVM.updateIsLoading(false)
//                            }
//                            //intercept to go out of the webview, otherwise we receive data in webview and we can't interact with it
//                            override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
//                                val requestUrl = request?.url.toString()
//                                if (requestUrl.startsWith("http://$ip:8080/auth/cas/validate")) {
//                                    loginVM.getUserData(requestUrl)
////                                if (loginState.userData != null){
//                                    loginVM.updateShouldNavigate(true)
////                                }
//
//                                    return WebResourceResponse(
//                                        "text/plain",
//                                        "UTF-8",
//                                        ByteArrayInputStream("".toByteArray()) // Empty response to prevent the default one
//                                    )
//                                }
//                                return super.shouldInterceptRequest(view, request) // give the ok the intercept
//                            }
//
//                            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
//                                val url = request?.url?.toString() ?: return false
//                                if (url.startsWith("http://localhost:8080")) {
//                                    val newUrl = url.replace("localhost", ip)
//                                    view?.loadUrl(newUrl) // Load the modified URL
//                                    return true //override : true , continue with request, either modified
//                                }
//                                return false //override : false , continue with unmodified request
//                            }
//
//                            override fun onPageFinished(view: WebView?, url: String?) {
//                                loginVM.updateIsLoading(false)
//                            }
//
//                        }
//                        webChromeClient = WebChromeClient()
//                        loadUrl(url)
//                    }
//                },
//                modifier = Modifier.fillMaxSize()
//            )
//        }
//
//
//
//
//        if (loginState.isLoading) {
//            CircularProgressIndicator(
//                modifier = Modifier.align(Alignment.Center)
//            )
//        }
//
//        if (loginState.webViewError) {
//            Text(
//                text = "Error loading content",
//                modifier = Modifier.align(Alignment.Center)
//            )
//            BasicButton(
//                onClick= { navController.navigate(Routes.LOGIN) },
//                text = "Refresh",
//            )
//        }
//
//    }
//}
//
////fun overrideUrl(view: WebView?, request: WebResourceRequest?): Boolean{
////    val url = request?.url?.toString() ?: return false
////    if (url.startsWith("http://localhost:8080")) {
////        val newUrl = url.replace("localhost", ip)
////        view?.loadUrl(newUrl) // Load the modified URL
////        return true //override : true , continue with request, either modified
////    }
////    return false //override : false , continue with unmodified request
////
////}