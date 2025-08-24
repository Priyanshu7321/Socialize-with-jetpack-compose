package com.example.socialize

import android.app.Activity
import android.content.Context
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
import androidx.compose.material.ripple.rememberRipple
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.example.socialize.viewmodel.NetworkViewModel
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.socialize.auth.GoogleAuthUiClient
import com.example.socialize.composables.home
import com.example.socialize.entity.UserPassword
import com.example.socialize.ui.components.ErrorDialog
import com.example.socialize.ui.theme.SocializeTheme
import com.example.socialize.viewmodel.AuthState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope

@AndroidEntryPoint
class SignAndLogin : ComponentActivity() {
//    val client = Client(context = this).setProject("677ec69a00172ea7c40a")

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SocializeTheme {
                val navController = rememberNavController()
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .systemBarsPadding(),
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = "LoginSignUp",
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable("LoginSignUp") {
                                LoginSignUp(navController)
                            }
                            composable("home") {
                                home()
                            }

                        }
                    }
                }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginSignUp(navController: NavController, networkViewModel: NetworkViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val isLoading by networkViewModel.isLoading
    
    // Observe auth state
    val authState by networkViewModel.authState.collectAsState()
    
    // Show success/error dialogs and handle navigation
    LaunchedEffect(authState) {
        when (val state = authState) {
            is AuthState.Success -> {
                // Show success message
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                
                // Navigate to home on success with a small delay to show the success message
                Handler(Looper.getMainLooper()).postDelayed({
                    navController.navigate("home") {
                        popUpTo(0) { inclusive = true } // Clear back stack
                    }
                }, 500)
            }
            is AuthState.Error -> {
                // Show error dialog
                if (state.message.isNotBlank()) {
                    Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
                }
            }
            is AuthState.Loading -> {
                // Show loading state if needed
            }
            else -> {}
        }
    }
    val googleAuthUiClient = remember { GoogleAuthUiClient(context) }
    
    // One Tap launcher
    val oneTapLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            coroutineScope.launch {
                val signInResult = googleAuthUiClient.signInWithIntent(result.data!!)
                signInResult.onSuccess { data ->
                    networkViewModel.signInWithGoogle(data.idToken)
                }.onFailure { e ->
                    Toast.makeText(context, e.message ?: "Google Sign-In failed", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    var selectedSection by remember { mutableStateOf("Sign Up") }
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.background(color = Color.White).fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 5.dp)
                .fillMaxSize()
                .background(color = Color.White)
                .verticalScroll(scrollState) // Apply vertical scrolling
        ) {
            Box(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)) {
                Row(modifier = Modifier.padding(start = 0.dp)) {
                    Image(
                        painter = painterResource(R.drawable.letters),
                        contentDescription = "logo",
                        Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Socialize",
                        style = TextStyle(fontSize = 30.sp, color = Color.Magenta, fontWeight = FontWeight.Bold)
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

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()) {
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.5f))
                            .wrapContentSize(Alignment.Center)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(50.dp),
                            color = Color.White
                        )
                    }
                }
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .padding(3.dp)
                        .clickable(
                            indication = rememberRipple(),
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            handleGoogleSignIn(
                                isLoading = isLoading,
                                context = context,
                                coroutineScope = coroutineScope,
                                googleAuthUiClient = googleAuthUiClient,
                                oneTapLauncher = oneTapLauncher
                            )
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
        if (isLoading){
            Box(modifier = Modifier.fillMaxWidth()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Color.Magenta)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Signup(navController: NavController, networkViewModel: NetworkViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val isLoading by networkViewModel.isLoading
    val coroutineScope = rememberCoroutineScope()
    val googleAuthUiClient = remember { GoogleAuthUiClient(context) }
    val oneTapLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val data = result.data!!
            coroutineScope.launch {
                val signInResult = googleAuthUiClient.signInWithIntent(data)
                signInResult.onSuccess { res ->
                    networkViewModel.signInWithGoogle(res.idToken)
                }.onFailure { e ->
                    Toast.makeText(context, e.message ?: "Google Sign-In failed", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    var name by remember { mutableStateOf("") }
    var email by remember{ mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isResponseSuccessfulorNot = networkViewModel.isResponseSuccessfulOrNot
    LaunchedEffect(isResponseSuccessfulorNot.value) {
        if(isResponseSuccessfulorNot.value){
            navController.navigate("home") {
                popUpTo("LoginSignUp") { inclusive = true }
                launchSingleTop = true
            }
        }
    }
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
                indication = rememberRipple(),
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    handleGoogleSignIn(
                        isLoading = isLoading,
                        context = context,
                        coroutineScope = coroutineScope,
                        googleAuthUiClient = googleAuthUiClient,
                        oneTapLauncher = oneTapLauncher
                    )
                },
                onLongClick = {
                    Toast.makeText(context, "SignUp Long Click", Toast.LENGTH_SHORT).show()
                    coroutineScope.launch {
                        networkViewModel.saveTestCredentialsToDatastore(
                            name = "abc",
                            email = "abc@gmail.com",
                            password = "12345678"
                        )
                        navController.navigate("home") {
                            popUpTo("LoginSignUp") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                }
            ),
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.elevatedCardElevation(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Magenta),

    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(text = "Signup", style = TextStyle(fontSize = 17.sp, color = Color.White))
        }
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

    if (isLoading) {
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
    
    val authState by networkViewModel.authState.collectAsState()
    val isLoading by networkViewModel.isLoading
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    
    // Handle auth state changes
    LaunchedEffect(authState) {
        when (val state = authState) {
            is AuthState.Success -> {
                navController.navigate("home") {
                    popUpTo("LoginSignUp") { inclusive = true }
                    launchSingleTop = true
                }
            }
            is AuthState.Error -> {
                errorMessage = state.message
                showErrorDialog = true
            }
            else -> {}
        }
    }
    
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
    Button(
        onClick = {
            val userPassword = UserPassword(
                username = email,
                password = password
            )
            coroutineScope.launch {
                networkViewModel.authenticate(userPassword, "login")
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF4A80F5),
            contentColor = Color.White,
            disabledContainerColor = Color(0xFFA0A0A0),
            disabledContentColor = Color.White
        ),
        enabled = !isLoading && email.isNotBlank() && password.isNotBlank()
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = Color.White,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = "Login",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
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
    // Forgot Password Text
    TextButton(
        onClick = {
            // TODO: Implement forgot password flow
            Toast.makeText(context, "Forgot Password Clicked", Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier.padding(top = 8.dp)
    ) {
        Text("Forgot Password?")
    }
    
    Spacer(modifier = Modifier.height(16.dp))
    
    // Sign Up Suggestion
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Don't have an account? ")
        TextButton(
            onClick = {
                // This will be handled by the parent composable to switch to signup
            }
        ) {
            Text("Sign Up", color = Color(0xFF4A80F5), fontWeight = FontWeight.Bold)
        }
    }
    
    // Divider with "Or login with" text
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
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
    oneTapLauncher: ActivityResultLauncher<IntentSenderRequest>
) {
    if (isLoading) return

    coroutineScope.launch {
        val intentSender = googleAuthUiClient.beginSignIn()
        if (intentSender != null) {
            try {
                oneTapLauncher.launch(IntentSenderRequest.Builder(intentSender).build())
            } catch (e: Exception) {
                Log.e("GoogleSignIn", "Failed to launch One Tap", e)
                Toast.makeText(context, "Failed to launch Google Sign-In", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(context, "Unable to start Google One Tap", Toast.LENGTH_LONG).show()
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