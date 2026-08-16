package com.example.socialize

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.socialize.viewmodel.NetworkViewModel
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable

import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.core.view.WindowInsetsControllerCompat

import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.socialize.auth.GoogleAuthUiClient
import com.example.socialize.composables.home
import com.example.socialize.screens.SplashScreen
import com.example.socialize.entity.UserPassword
import com.example.socialize.repository.DatastoreRepository
import com.example.socialize.ui.components.ErrorDialog
import com.example.socialize.ui.theme.SocializeTheme
import com.example.socialize.viewmodel.AuthState
import com.example.socialize.viewmodel.DatastoreViewModel
import com.google.firebase.BuildConfig
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import kotlin.math.roundToInt

import androidx.compose.ui.graphics.RenderEffect

import android.graphics.Shader
import androidx.compose.foundation.LocalIndication

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.socialize.viewmodel.AuthLoginState

import kotlin.math.roundToInt

@AndroidEntryPoint
class SignAndLogin : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = android.graphics.Color.BLUE,
                darkScrim = android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = android.graphics.Color.BLACK,
                darkScrim = android.graphics.Color.TRANSPARENT
            )
        )

        setContent {
            SocializeTheme {
                val navControllerHost = rememberNavController()
                val datastoreViewModel: DatastoreViewModel = hiltViewModel()
                val authState by datastoreViewModel.authStateFlow
                    .collectAsState(initial = AuthLoginState.Loading)
                var splashDone by remember { mutableStateOf(false) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    when {
                        !splashDone -> {
                            SplashScreen(onSplashComplete = { splashDone = true })
                        }
                        authState == AuthLoginState.Loading -> {
                            // DataStore still reading — show nothing (splash already covered this)
                            Box(Modifier.fillMaxSize())
                        }
                        authState == AuthLoginState.Authenticated -> {
                            AppNavHost(
                                navController = navControllerHost,
                                startDestination = "home",
                                innerPadding = innerPadding
                            )
                        }
                        else -> {
                            AppNavHost(
                                navController = navControllerHost,
                                startDestination = "LoginSignUp",
                                innerPadding = innerPadding
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier
            .fillMaxSize()
    ) {
        composable("LoginSignUp") {
            LoginSignUp(navController)
        }
        composable("home") {
            home(navController, innerPadding)
        }
    }
}


enum class GlassType {
    CONVEX,
    CONCAVE
}

@Composable
fun MovableGlassView(
    modifier: Modifier = Modifier,
    size: Dp = 150.dp,
    cornerRadius: Dp = 24.dp,
    glassType: GlassType = GlassType.CONVEX
) {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    val highlightAlpha = if (glassType == GlassType.CONVEX) 0.45f else 0.15f
    val shadowAlpha = if (glassType == GlassType.CONVEX) 0.25f else 0.45f

    Box(
        modifier = modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .size(size)
            .clip(RoundedCornerShape(cornerRadius))
            // 🌈 Glass refraction gradient
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color.White.copy(alpha = highlightAlpha),
                        Color.White.copy(alpha = 0.05f),
                        Color.Black.copy(alpha = shadowAlpha)
                    ),
                    start = Offset.Zero,
                    end = Offset.Infinite
                )
            )
            // 🌫 Soft glass blur
            .blur(14.dp)
            // ✨ Edge refraction highlight
            .border(
                1.5.dp,
                Brush.linearGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.6f),
                        Color.White.copy(alpha = 0.2f),
                        Color.Transparent
                    )
                ),
                RoundedCornerShape(cornerRadius)
            )
            // 🕳 Inner shadow illusion
            .drawWithContent {
                drawContent()
                drawRoundRect(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.35f)
                        )
                    ),
                    cornerRadius = CornerRadius(cornerRadius.toPx()),
                    size = this.size
                )
            }
            .pointerInput(Unit) {
                detectDragGestures { change, drag ->
                    change.consume()
                    offsetX += drag.x
                    offsetY += drag.y
                }
            }
    )
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginSignUp(navController: NavController, networkViewModel: NetworkViewModel = hiltViewModel(),datastoreViewModel: DatastoreViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val isLoading = remember{ mutableStateOf(false)}
    var showDialog by remember { mutableStateOf(false) }
    // Observe auth state
    val authState by networkViewModel.authState.collectAsState()
    LaunchedEffect(coroutineScope) {
        Log.d("checkAuth", ""
        )
    }
    // Show success/error dialogs and handle navigation
    LaunchedEffect(authState) {
        when (val state = authState) {
            is AuthState.Success -> {
                isLoading.value = false
                // Show success message
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                
                // Navigate to home on success with a small delay to show the success message
                if(state.method.equals("login")) {
                    datastoreViewModel.saveData(DatastoreRepository.AUTHENTICATED,true)
                    Handler(Looper.getMainLooper()).postDelayed({
                        navController.navigate("home") {
                            popUpTo(0) { inclusive = true } // Clear back stack
                        }
                    }, 500)
                }
            }
            is AuthState.Error -> {
                isLoading.value = false
                // Show error dialog
                if (state.message.isNotBlank()) {
                    Toast.makeText(context, state.message+"hello", Toast.LENGTH_LONG).show()
                }
            }
            is AuthState.Loading -> {
                isLoading.value = true
                // Show loading state if needed
            }
            else -> {
                isLoading.value = false
            }
        }
    }
    val googleAuthUiClient = remember { GoogleAuthUiClient(context) }
    


    var selectedSection by remember { mutableStateOf("Sign Up") }
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.background(color = Color.White).fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 5.dp)
                .fillMaxSize()
                .background(color = Color.White)
                .systemBarsPadding() // Add padding for system bars
                .verticalScroll(scrollState) // Apply vertical scrolling
        ) {
            Box(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)) {
                Row(modifier = Modifier.padding(start = 0.dp)) {
                    Image(
                        painter = painterResource(R.drawable.socializeicon),
                        contentDescription = "logo",
                        Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Socialize",
                        style = TextStyle(
                            fontSize = 30.sp,
                            color = Color(0xFFFF9800), // Orange 500
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Text(
                text = "Welcome to Socialize!",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
            )
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                text = "Join us today and dive into a world of connection! Sign up to start your journey, or log in to keep exploring with friends.",
                style = TextStyle(color = Color.Gray, fontSize = 14.sp)
            )
            Spacer(modifier = Modifier.height(7.dp))

            CustomAppBar(selectedSection = selectedSection, onSectionSelected = { selectedSection = it })
            Spacer(modifier = Modifier.height(7.dp))

            if (selectedSection == "Sign Up") {
                Signup(navController)
            } else {
                Login(navController)
            }

            if(showDialog){
                UrlInputDialog(
                    onDismiss = { showDialog = false },
                    onConfirm = { enteredUrl ->
                       networkViewModel.updateBaseUrl(enteredUrl)
                    }
                )
            }
            Spacer(modifier = Modifier.height(5.dp))

            Row(modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()) {
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .padding(3.dp)
                        .clickable(
                        ) {
                            handleGoogleSignIn(
                                isLoading = isLoading.value,
                                context = context,
                                coroutineScope = coroutineScope,
                                googleAuthUiClient = googleAuthUiClient,
                                networkViewModel = networkViewModel
                            )
//                            networkViewModel.checkConnection();
                        },
                    shape = RoundedCornerShape(30.dp),
                    border = BorderStroke(width = 1.dp, color = Color.Gray),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.google),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp)
                        )
                        Text(text = " Google")
                    }
                }
                // Facebook button (hidden but kept for future use)
                Box(
                    modifier = Modifier
                        .size(0.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(3.dp),
                        shape = RoundedCornerShape(30.dp),
                        border = BorderStroke(width = 1.dp, color = Color.Gray),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(R.drawable.facebook),
                                contentDescription = "",
                                modifier = Modifier.size(20.dp)
                            )
                            Text(text = " Facebook")
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 15.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = "By using this app, you agree to our terms and conditions and acknowledge that you have read and understood them",
                    style = TextStyle(color = Color.Gray)
                )
            }
        }
        if (isLoading.value){
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Color.Magenta)
            }
        }
    }
}

//Dialog box for adding custom url
@Composable
fun UrlInputDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var url by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Enter Base URL") },
        text = {
            Column {
                OutlinedTextField(
                    value = url,
                    onValueChange = { url = it },
                    label = { Text("Base URL") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(url)
                    onDismiss()
                }
            ) { Text("OK") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Signup(navController: NavController, networkViewModel: NetworkViewModel = hiltViewModel(),datastoreViewModel: DatastoreViewModel = hiltViewModel()) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val isLoading = remember{ mutableStateOf(false)}
    val coroutineScope = rememberCoroutineScope()
    val googleAuthUiClient = remember { GoogleAuthUiClient(context) }
    var showUrlDialog by remember { mutableStateOf(false) }

    LaunchedEffect(coroutineScope) {
        datastoreViewModel
            .getFlow(DatastoreRepository.BASE_URL)
            .collect { value ->
                val baseUrl = value as? String

                if (!baseUrl.isNullOrBlank()) {
                    networkViewModel.updateBaseUrl(baseUrl)
                }
            }
    }



    var name by remember { mutableStateOf("") }
    var email by remember{ mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFFE2F3EA), Color(0xFFCDA2E0)) // Custom gradient colors
    )
    Text(
        text = "Name",
        style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(start = 10.dp, top = 8.dp, bottom = 4.dp)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Color.Gray),  // Gray outline for the Card
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Background color for the Card
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
            TextField(
                value = name,
                onValueChange = { newText -> name = newText },
                placeholder = { Text("Enter your Name") },
                textStyle = TextStyle(fontSize = 15.sp),
                modifier = Modifier
                    .fillMaxWidth(), // Padding inside the card
                colors = TextFieldDefaults.colors(
                    unfocusedLabelColor = Color.Gray,
                    unfocusedContainerColor = Color.White,
                    unfocusedIndicatorColor = Color.White, // Removes underline
                    focusedIndicatorColor = Color.Transparent // Removes underline
                ),
                trailingIcon = {
                    IconButton(onClick = { /* Handle click */ }) {
                        Icon(imageVector = Icons.Filled.Person, contentDescription = "Clear text")
                    }
                }
            )
        }
    }
    Text(
        text = "Email",
        style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(start = 10.dp, top = 8.dp, bottom = 4.dp)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Color.Gray),  // Gray outline for the Card
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Background color for the Card
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
            TextField(
                value = email,
                onValueChange = { newText -> email = newText },
                placeholder = { Text("Enter your email") },
                textStyle = TextStyle(fontSize = 15.sp),
                modifier = Modifier
                    .fillMaxWidth(), // Padding inside the card
                colors = TextFieldDefaults.colors(
                    unfocusedLabelColor = Color.Gray,
                    unfocusedContainerColor = Color.White,
                    unfocusedIndicatorColor = Color.White, // Removes underline
                    focusedIndicatorColor = Color.Transparent // Removes underline
                ),
                trailingIcon = {
                    IconButton(onClick = { /* Handle click */ }) {
                        Icon(imageVector = Icons.Filled.Email, contentDescription = "Clear text")
                    }
                }
            )
        }
    }
    Text(
        text = "Password",
        style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(start = 10.dp, top = 8.dp, bottom = 4.dp)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Color.Gray),  // Gray outline for the Card
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Background color for the Card
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
            TextField(
                value = password,
                onValueChange = { newText -> password = newText },
                placeholder = { Text("Enter your password") },
                textStyle = TextStyle(fontSize = 15.sp),
                modifier = Modifier
                    .fillMaxWidth(), // Padding inside the card
                colors = TextFieldDefaults.colors(
                    unfocusedLabelColor = Color.Gray,
                    unfocusedContainerColor = Color.White,
                    unfocusedIndicatorColor = Color.White, // Removes underline
                    focusedIndicatorColor = Color.Transparent // Removes underline
                ),
                trailingIcon = {
                    IconButton(onClick = { /* Handle click */ }) {
                        Icon(imageVector = Icons.Filled.Lock, contentDescription = "Clear text")
                    }
                }
            )
        }
    }
    Spacer(Modifier.height(20.dp))
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .combinedClickable(
                onClick = {
                    navController.navigate("home") {
                        popUpTo(0) { inclusive = true } // Clear back stack
                    }
//                    scope.launch {
//                        networkViewModel.authenticate(UserPassword(name = name, email = email, password = password),"signup")
//                    }
//                    handleGoogleSignIn(
//                        isLoading = isLoading,
//                        context = context,
//                        coroutineScope = coroutineScope,
//                        googleAuthUiClient = googleAuthUiClient,
//                        oneTapLauncher = oneTapLauncher
//                    )
                },
                onLongClick = {
                    Toast.makeText(context, "Long press to configure IP address", Toast.LENGTH_SHORT).show()
                    showUrlDialog = true
                }
            ),
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.elevatedCardElevation(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFF9800)),

    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(text = "Signup", style = TextStyle(fontSize = 17.sp, color = Color.White))
        }
    }

    // URL Configuration Dialog
    if (showUrlDialog) {
        UrlInputDialog(
            onDismiss = { showUrlDialog = false },
            onConfirm = { enteredUrl ->
                networkViewModel.updateBaseUrl(enteredUrl)
                Toast.makeText(context, "Base URL updated to: $enteredUrl", Toast.LENGTH_LONG).show()
                datastoreViewModel.saveData(DatastoreRepository.BASE_URL,enteredUrl)
            }
        )
    }

    Spacer(Modifier.height(20.dp))
    Row (verticalAlignment = Alignment.CenterVertically){
        Row (modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .padding(end=10.dp)
            .weight(1f)
            .background(color = Color.Gray)){}
        Text(text = "Or Sign up with", style = TextStyle(color = Color.Gray))
        Row (modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .padding(start=10.dp)
            .weight(1f)
            .background(color = Color.Gray)){}
    }

    if (isLoading.value) {
        Box(modifier = Modifier.fillMaxWidth()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Color.Magenta)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Login(navController: NavController, networkViewModel: NetworkViewModel = hiltViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var showUrlDialog by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    

    
    // Show error dialog if needed
    if (showErrorDialog) {
        ErrorDialog(
            message = errorMessage,
            onDismiss = { showErrorDialog = false }
        )
    }
    Text(
        text = "Email",
        style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(start = 10.dp, top = 8.dp, bottom = 4.dp)
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        border = BorderStroke(1.dp, Color.Gray),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        TextField(
            value = email,
            onValueChange = { newText -> email = newText },
            placeholder = { Text("Enter your Email") },
            modifier = Modifier.fillMaxSize(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                focusedIndicatorColor = Color.Transparent
            )
        )
    }
    Text(
        text = "Password",
        style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(start = 10.dp, top = 8.dp, bottom = 4.dp)
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        border = BorderStroke(1.dp, Color.Gray),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        TextField(
            value = password,
            onValueChange = { newText -> password = newText },
            placeholder = { Text("Enter your password") },
            modifier = Modifier.fillMaxSize(),
            colors = TextFieldDefaults.colors(
                unfocusedLabelColor = Color.Gray,
                unfocusedContainerColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                focusedIndicatorColor = Color.Transparent
            )
        )
    }
    Spacer(Modifier.height(20.dp))
    // Login Button
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .combinedClickable(
                onClick = {
                    val userPassword = UserPassword(
                        email = email,
                        password = password
                    )
                    coroutineScope.launch {
                        networkViewModel.authenticate(userPassword, "login")
                    }
                }
            ),
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.elevatedCardElevation(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFF9800)),

        ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(text = "Sign In", style = TextStyle(fontSize = 17.sp, color = Color.White))
        }
    }


    // Test Credentials Button (for development)
    if (BuildConfig.DEBUG) {
        TextButton(
            onClick = {
                coroutineScope.launch {
                    networkViewModel.saveTestCredentialsToDatastore(
                        name = "Test User",
                        email = "test@example.com",
                        password = "test123"
                    )
                    email = "test@example.com"
                    password = "test123"
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Use Test Account")
        }
    }
    // URL Configuration Dialog
    if (showUrlDialog) {
        UrlInputDialog(
            onDismiss = { showUrlDialog = false },
            onConfirm = { enteredUrl ->
                networkViewModel.updateBaseUrl(enteredUrl)
                Toast.makeText(context, "Base URL updated to: $enteredUrl", Toast.LENGTH_LONG).show()
            }
        )
    }

    // Forgot Password Text
    TextButton(
        onClick = {
            // TODO: Implement forgot password flow
            Toast.makeText(context, "Forgot Password Clicked", Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier.padding(top = 4.dp)
    ) {
        Text("Forgot Password?")
    }
    

    
    // Divider with "Or login with" text
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp),
            color = Color.Gray,
            thickness = 1.dp
        )
        Text(
            text = "Or login with",
            style = TextStyle(color = Color.Gray),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Divider(
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp),
            color = Color.Gray,
            thickness = 1.dp
        )
    }
}
@Composable
fun CustomAppBar(selectedSection: String, onSectionSelected: (String) -> Unit) {
    val activeColor = Color.White
    val backgroundColor = Color(0xFFF0F0F0)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(20.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(20.dp))
                .background(color = if (selectedSection == "Sign Up") activeColor else Color.Transparent)
                .clickable { onSectionSelected("Sign Up") }
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(20.dp))
                .background(color = if (selectedSection == "Log In") activeColor else Color.Transparent)
                .clickable { onSectionSelected("Log In") }
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Log In",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

internal fun handleGoogleSignIn(
    isLoading: Boolean,
    context: Context,
    coroutineScope: CoroutineScope,
    googleAuthUiClient: GoogleAuthUiClient,
    networkViewModel: NetworkViewModel
) {
    if (isLoading) return

    coroutineScope.launch {
        val signInResult = googleAuthUiClient.signIn()

        signInResult
            .onSuccess { res ->
                networkViewModel.signInWithGoogle(res.idToken)
            }
            .onFailure { e ->
                Toast.makeText(
                    context,
                    e.message ?: "Google Sign-In failed",
                    Toast.LENGTH_LONG
                ).show()
            }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    SocializeTheme {
        val navController = rememberNavController()
        LoginSignUp(navController)
    }
}