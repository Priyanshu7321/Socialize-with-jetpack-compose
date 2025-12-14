package com.example.socialize.screens

import android.Manifest
import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.VideocamOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun VideoCallWebView(
    navController: NavController,
    userId: String,
    otherUserId: String
) {
    val context = LocalContext.current
    var isMicOn by remember { mutableStateOf(true) }
    var isVideoOn by remember { mutableStateOf(true) }
    var webView: WebView? by remember { mutableStateOf(null) }

    // Permissions
    val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.MODIFY_AUDIO_SETTINGS
    )
    
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        val allGranted = perms.values.all { it }
        if (!allGranted) {
            Toast.makeText(context, "Permissions required for video call", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
    }

    LaunchedEffect(Unit) {
        launcher.launch(permissions)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                WebView(ctx).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        mediaPlaybackRequiresUserGesture = false
                        allowFileAccess = true
                        allowContentAccess = true
                    }
                    
                    webChromeClient = object : WebChromeClient() {
                        override fun onPermissionRequest(request: PermissionRequest?) {
                            request?.grant(request.resources)
                        }
                    }
                    
                    addJavascriptInterface(object {
                        @JavascriptInterface
                        fun onPeerConnected() {
                             post {
                                // Once connected to peer server, try to call the other user
                                evaluateJavascript("startCall('$otherUserId')", null)
                            }
                        }
                    }, "Android")
                    
                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            // Initialize with our user ID
                            evaluateJavascript("init('$userId')", null)
                        }
                    }
                   
                    loadUrl("file:///android_asset/call.html")
                    webView = this
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // Controls
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Mute Button
            IconButton(
                onClick = { 
                    isMicOn = !isMicOn 
                    webView?.evaluateJavascript("toggleAudio('$isMicOn')", null)
                },
                modifier = Modifier
                    .size(50.dp)
                    .background(if (isMicOn) Color.DarkGray else Color.Red, CircleShape)
            ) {
                Icon(
                    imageVector = if (isMicOn) Icons.Filled.Mic else Icons.Filled.MicOff,
                    contentDescription = "Toggle Mic",
                    tint = Color.White
                )
            }

            // End Call Button
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.Red, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.CallEnd,
                    contentDescription = "End Call",
                    tint = Color.White
                )
            }

            // Video Toggle Button
            IconButton(
                onClick = { 
                    isVideoOn = !isVideoOn
                    webView?.evaluateJavascript("toggleVideo('$isVideoOn')", null)
                },
                modifier = Modifier
                    .size(50.dp)
                    .background(if (isVideoOn) Color.DarkGray else Color.Red, CircleShape)
            ) {
                Icon(
                    imageVector = if (isVideoOn) Icons.Filled.Videocam else Icons.Filled.VideocamOff,
                    contentDescription = "Toggle Video",
                    tint = Color.White
                )
            }
        }
    }
}
