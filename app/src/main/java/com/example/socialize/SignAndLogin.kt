package com.example.socialize

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.socialize.ui.theme.SocializeTheme
import kotlinx.coroutines.launch
import io.appwrite.Client
import io.appwrite.services.Account

class SignAndLogin : ComponentActivity() {
    val client = Client(context = this).setProject("677ec69a00172ea7c40a")

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SocializeTheme {
                val navController = rememberNavController()
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = "greeting",
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable("greeting") {
                                Greeting(navController)
                            }
                            composable("home") {
                                home(navController)
                            }
                            composable("profile"){
                                profileforus(navController)
                            }
                            composable("chats") {
                                chats(navController)
                            }
                            composable("posting") {
                                post(navController)
                            }
                            composable("members") {
                                members(navController)
                            }
                            composable("status"){
                                SwipeableCards(navController)
                            }
                        }
                    }
                }
        }
    }
}
@Composable
fun Greeting(navController: NavController) {

    var selectedSection by remember { mutableStateOf("Sign Up") }

    Box(modifier=Modifier.background(color=Color.White)){
    Column(
        modifier = Modifier
            .padding(start = 30.dp, end = 30.dp, top = 10.dp)
            .fillMaxSize()
            .background(color = Color.White)
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

        Row(modifier = Modifier.height(60.dp).fillMaxWidth()) {
            Card(
                modifier = Modifier.weight(1f).height(50.dp).padding(3.dp),
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
            Card(
                modifier = Modifier.weight(1f).height(50.dp).padding(3.dp),
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
    }
}
@Composable
fun Signup(navController: NavController) {
    var name by remember { mutableStateOf("") }
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
            .height(52.dp)
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Color.Gray),  // Gray outline for the Card
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Background color for the Card
        )
    ) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            TextField(
                value = name,
                onValueChange = { newText -> name = newText },
                placeholder = { Text("Enter your Name") },
                textStyle = TextStyle(fontSize = 15.sp),
                modifier = Modifier
                    .fillMaxSize(), // Padding inside the card
                colors = TextFieldDefaults.colors(
                    unfocusedLabelColor = Color.Gray,
                    unfocusedContainerColor = Color.White,
                    unfocusedIndicatorColor = Color.White, // Removes underline
                    focusedIndicatorColor = Color.Transparent // Removes underline
                ),
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
            .height(52.dp)
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Color.Gray),  // Gray outline for the Card
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Background color for the Card
        )
    ) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            TextField(
                value = name,
                onValueChange = { newText -> name = newText },
                placeholder = { Text("Enter your email") },
                textStyle = TextStyle(fontSize = 15.sp),
                modifier = Modifier
                    .fillMaxSize(), // Padding inside the card
                colors = TextFieldDefaults.colors(
                    unfocusedLabelColor = Color.Gray,
                    unfocusedContainerColor = Color.White,
                    unfocusedIndicatorColor = Color.White, // Removes underline
                    focusedIndicatorColor = Color.Transparent // Removes underline
                )
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
            .height(52.dp)
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Color.Gray),  // Gray outline for the Card
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Background color for the Card
        )
    ) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            TextField(
                value = name,
                onValueChange = { newText -> name = newText },
                placeholder = { Text("Enter your password") },
                textStyle = TextStyle(fontSize = 15.sp),
                modifier = Modifier
                    .fillMaxSize(), // Padding inside the card
                colors = TextFieldDefaults.colors(
                    unfocusedLabelColor = Color.Gray,
                    unfocusedContainerColor = Color.White,
                    unfocusedIndicatorColor = Color.White, // Removes underline
                    focusedIndicatorColor = Color.Transparent // Removes underline
                )
            )
        }
    }
    Spacer(Modifier.height(20.dp))
    Box(modifier = Modifier.fillMaxWidth().height(50.dp).background(gradient,
                RoundedCornerShape(30.dp)
    ).clickable(onClick = {navController.navigate("home")}),
        contentAlignment = Alignment.Center
        ) {
            Text(text = "Signup", style = TextStyle(fontSize = 17.sp,color = Color.White))
        }

    Spacer(Modifier.height(20.dp))
    Row (verticalAlignment = Alignment.CenterVertically){
        Row (modifier = Modifier.fillMaxWidth().height(1.dp).weight(1f).background(color = Color.Gray)){}
        Text(text = "Or Sign up with", style = TextStyle(color = Color.Gray))
        Row (modifier = Modifier.fillMaxWidth().height(1.dp).weight(1f).background(color = Color.Gray)){}
    }
}
@Composable
fun Login(navController: NavController){
    var name by remember { mutableStateOf("") }


    Text(
        text = "Email",
        style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(start = 10.dp, top = 8.dp, bottom = 4.dp)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        border = BorderStroke(1.dp, Color.Gray),  // Gray outline for the Card
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Background color for the Card
        )
    ) {
        TextField(
            value = name,
            onValueChange = { newText -> name = newText },
            placeholder = { Text("Enter your Email") },
            modifier = Modifier
                .fillMaxSize(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                unfocusedIndicatorColor = Color.White, // Removes underline
                focusedIndicatorColor = Color.Transparent // Removes underline
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
            .height(50.dp)
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        border = BorderStroke(1.dp, Color.Gray),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        TextField(
            value = name,
            onValueChange = { newText -> name = newText },
            placeholder = { Text("Enter your password") },
            modifier = Modifier
                .fillMaxSize(),
            colors = TextFieldDefaults.colors(
                unfocusedLabelColor = Color.Gray,
                unfocusedContainerColor = Color.White,
                unfocusedIndicatorColor = Color.White, // Removes underline
                focusedIndicatorColor = Color.Transparent // Removes underline
            )
        )
    }
    Spacer(Modifier.height(20.dp))
    ElevatedButton(
        modifier = Modifier.fillMaxWidth()
            .height(50.dp).padding(start = 10.dp, end = 10.dp),
        shape = RoundedCornerShape(30.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 10.dp),
        onClick = {navController.navigate("home")}
    ) {
        Text(text = "Login", style = TextStyle(fontSize = 17.sp))
    }
    Spacer(Modifier.height(20.dp))
    Row (verticalAlignment = Alignment.CenterVertically){
        Row (modifier = Modifier.fillMaxWidth().height(1.dp).weight(1f).background(color = Color.Gray)){}
        Text(text = "Or login with", style = TextStyle(color = Color.Gray))
        Row (modifier = Modifier.fillMaxWidth().height(1.dp).weight(1f).background(color = Color.Gray)){}
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    SocializeTheme {
        val navController = rememberNavController()
        Greeting(navController)
    }
}



