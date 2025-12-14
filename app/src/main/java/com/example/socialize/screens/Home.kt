package com.example.socialize.composables

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.socialize.R
import com.example.socialize.screens.Users
import com.example.socialize.screens.members
import com.example.socialize.screens.profileforother
import com.example.socialize.screens.profileforus
import com.example.socialize.screens.videoView
import kotlinx.coroutines.launch
import kotlin.random.Random

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.socialize.screens.SwipeableCards
import com.example.socialize.screens.VideoCallWebView
import com.example.socialize.screens.chats
import com.example.socialize.screens.post

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun home() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    var selectedIcon by remember { mutableStateOf("Home") }
    var navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val bottomBarRoutes = listOf("home", "members", "videoCall", "users")
    Scaffold(
        bottomBar = {
            if (currentRoute in bottomBarRoutes) {
                BottomNavigationBar(navController, selectedIcon) { selected ->
                    selectedIcon = selected
                    when (selected) {
                        "Home" -> navController.navigate("home")
                        "Chat" -> navController.navigate("members")
                        "Video" -> navController.navigate("videoCall")
                        "Search" -> navController.navigate("users")
                    }
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            BackHandler {
                navController.navigate("home")
            }
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    DrawerContent(navController, drawerState)
                }
            ) {
                NavHost(
                    navController = navController,
                    startDestination = "home",
                    modifier = Modifier.padding(5.dp)
                ) {
                    composable("home") {
                        homeContent(navController)
                    }
                    composable("profile") {
                        profileforus(navController)
                    }
                    composable("profileforother") {
                        profileforother(navController)
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
                    composable("status") {
                        SwipeableCards(navController)
                    }
                    composable("videoCall") {
                        videoView(navController)
                    }
                    composable(
                        "videoCallWebView/{userId}/{otherUserId}",
                        arguments = listOf(
                            androidx.navigation.navArgument("userId") { type = androidx.navigation.NavType.StringType },
                            androidx.navigation.navArgument("otherUserId") { type = androidx.navigation.NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("userId") ?: ""
                        val otherUserId = backStackEntry.arguments?.getString("otherUserId") ?: ""
                        VideoCallWebView(navController, userId, otherUserId)
                    }
                    composable("users") {
                        Users(navController)
                    }

                }
            }

        }
    }


}

@Composable
fun BottomNavigationBar(navController: NavController, selectedIcon: String, onIconSelected: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp,bottom=10.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .align(Alignment.BottomCenter),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.elevatedCardElevation(5.dp),
            shape = RoundedCornerShape(50.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Transparent),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconWithSelection(
                    isSelected = selectedIcon == "Home",
                    icon = Icons.Filled.Home,
                    contentDescription = "Home",
                    onClick = { onIconSelected("Home") }
                )
                IconWithSelection(
                    isSelected = selectedIcon == "Chat",
                    painter = rememberAsyncImagePainter(R.drawable.chat),
                    contentDescription = "Chat",
                    onClick = { onIconSelected("Chat") }
                )
                Spacer(Modifier.width(50.dp))
                IconWithSelection(
                    isSelected = selectedIcon == "Video",
                    painter = rememberAsyncImagePainter(R.drawable.video),
                    contentDescription = "Video",
                    onClick = { onIconSelected("Video") }
                )
                IconWithSelection(
                    isSelected = selectedIcon == "Search",
                    icon = Icons.Filled.Search,
                    contentDescription = "Search",
                    onClick = { onIconSelected("Search") }
                )
            }
        }
        Card(
            modifier = Modifier
                .height(60.dp)
                .width(60.dp)
                .clip(shape = CircleShape)
                .background(color = Color.White)
                .align(Alignment.Center)
                .clickable(
                    indication = LocalIndication.current,
                    interactionSource = remember { MutableInteractionSource() }
                ) { navController.navigate("posting") },
            shape = CircleShape,
            elevation = CardDefaults.elevatedCardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xfff89b29))
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Plus",
                    colorFilter = ColorFilter.tint(color = Color.White),
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

@Composable
fun IconWithSelection(
    isSelected: Boolean,
    icon: ImageVector? = null,
    painter: Painter? = null,
    contentDescription: String,
    onClick:  () -> Unit,
    isAddButton: Boolean = false
) {
    Box(
        modifier = Modifier
            .size(if (isAddButton) 50.dp else 40.dp)
            .background(
                color = if (isSelected) Color.Gray.copy(alpha = 0.5f) else Color.Transparent,
                shape = CircleShape
            ).clickable(
                indication = LocalIndication.current,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() }
            .shadow(if (isAddButton) 8.dp else 0.dp, CircleShape)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {

        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = Color(0xfff89b29),

                modifier = Modifier.size(if (isAddButton) 32.dp else 24.dp) // Larger icon size for Add button
            )
        } else if (painter != null) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                colorFilter = ColorFilter.tint(Color(0xfff89b29)),
                modifier = Modifier.size(if (isAddButton) 32.dp else 24.dp)
            )
        }
    }
}

@Composable
fun GlideImage(
    imageUrl: Any,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    val context = LocalContext.current
    val imageRequest = ImageRequest.Builder(context)
        .data(imageUrl)
        .size(width = 800, height = 600) // Limit size to prevent memory issues
        .build()
    
    AsyncImage(
        model = imageRequest,
        contentDescription = "",
        modifier = modifier,
        contentScale = contentScale
    )
}
data class Status(val profileImage :String,val profileStatus: List<String>)

@Composable
fun discoverList(statusList: List<Status>) {
    val infiniteState = rememberLazyListState()

    val names = listOf(
        "Alice", "Bob", "Charlie", "David", "Eve",
        "Frank", "Grace", "Hannah", "Ivy", "Jack",
        "Karen", "Leo", "Mona", "Nina", "Oscar",
        "Paul", "Quinn", "Rita", "Steve", "Tina"
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            state = infiniteState,
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(statusList.size * 10) { index -> // Only repeat 10 times to avoid Int.MAX_VALUE
                val actualIndex = index % statusList.size
                val status = statusList[actualIndex] // Get the actual item

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(55.dp)
                    ) {
                        GradientCircle() // Cached composable for smooth performance

                        // Image
                        Card(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp),
                            shape = CircleShape,
                            elevation = CardDefaults.elevatedCardElevation(4.dp)
                        ) {
                            AsyncImage(
                                model = R.drawable.woman, // Use Coil for better caching
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                    Text(names[actualIndex % names.size], fontSize = 12.sp)
                }
            }
        }
    }
}




fun generateRandomColor(): Color {
    val random = Random
    val red = random.nextInt(180, 256) // Lighter shades with higher values
    val green = random.nextInt(180, 256) // Lighter shades with higher values
    val blue = random.nextInt(180, 256) // Lighter shades with higher values
    return Color(red, green, blue)
}

@Composable
fun postList(navController: NavController){

    LazyColumn(modifier = Modifier
        .fillMaxHeight()
        .padding(top = 5.dp)) {
        items(20){
                it->
            val cardColor = generateRandomColor()
            val scrollState = rememberScrollState()
            Card (modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp, top = 5.dp)
                .background(Color.White),
                shape = RoundedCornerShape(30.dp),
                elevation = CardDefaults.elevatedCardElevation(4.dp), colors = CardDefaults.cardColors(containerColor = cardColor)) {
                Column(Modifier.padding(15.dp)) {
                    Row (modifier = Modifier
                        .height(60.dp)
                        .padding(4.dp)){
                        Image(painter = rememberAsyncImagePainter( (R.drawable.boy)), contentDescription ="" , modifier = Modifier.size(50.dp).clickable(
                            indication = LocalIndication.current,
                            interactionSource = remember { MutableInteractionSource() },
                            enabled = true,
                            onClick = {
                            navController.navigate("profileforother") {
                                launchSingleTop = true
                            }
                        }
                        ))
                        Column(modifier = Modifier
                            .fillMaxHeight()
                            .padding(start = 5.dp), verticalArrangement = Arrangement.Center) {
                            Text(text = "Alice", style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp) )
                            Text(text = "Posted 1h ago", style = TextStyle(color = Color.Gray,))
                        }
                        Row(Modifier.fillMaxWidth().fillMaxHeight(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = rememberAsyncImagePainter((R.drawable.menulist)),
                                colorFilter = ColorFilter.tint(color = Color.Black),
                                contentDescription ="" ,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Text(text = "Discover adventure in patogania's peaks or serenity provences @helmets-arrival", style = TextStyle(fontSize = 15.sp))
                    Spacer(Modifier.height(10.dp))
                    Row(modifier = Modifier.horizontalScroll(scrollState)) {
                        GlideImage(
                            imageUrl = R.drawable.forest2, // Using smaller image (65KB instead of 2.4MB)
                            modifier = Modifier.size(150.dp),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(Modifier.width(5.dp))
                        GlideImage(
                            imageUrl = R.drawable.forest2, // Using smaller image (65KB instead of 2.4MB)
                            modifier = Modifier.size(150.dp),
                            contentScale = ContentScale.Crop
                        )

                    }
                    Row(modifier = Modifier.height(40.dp), verticalAlignment = Alignment.CenterVertically){
                        Row (modifier = Modifier.height(40.dp), verticalAlignment = Alignment.CenterVertically){
                            Image(painter = rememberAsyncImagePainter( (R.drawable.like)), modifier = Modifier.size(27.dp), contentDescription = "")
                            Text(text = " 349 Likes", style = TextStyle(color = Color.Gray))
                        }
                        Spacer(modifier = Modifier.width(15.dp))
                        Row (modifier = Modifier.height(40.dp), verticalAlignment = Alignment.CenterVertically){
                            Image(
                                painter = rememberAsyncImagePainter( (R.drawable.comment)),
                                modifier = Modifier.size(27.dp),
                                contentDescription = ""
                            )
                            Text(text = " 520 Comments", style = TextStyle(color = Color.Gray))
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Image(painter = rememberAsyncImagePainter( (R.drawable.share)), modifier = Modifier.size(23.dp), contentDescription = "")

                    }
                }
            }

            Spacer(Modifier.height(5.dp))
        }
    }
}

@Composable
fun homeContent(navController: NavController) {
    val stateList = listOf(
        Status("skfjk", listOf("skfk", "sjkfjksj")),
        Status("skfjk", listOf("skfk", "sjkfjksj")),
        Status("skfjk", listOf("skfk", "sjkfjksj"))
    )

    var colorState by remember { mutableStateOf("Discover") }

    Column(
        modifier = Modifier.padding( start = 10.dp, end = 10.dp)
    ) {
        TopBar(navController)
        Spacer(modifier = Modifier.height(10.dp))

        // Discover and Following Tabs
        TabRow(colorState) { colorState = it }

        Spacer(modifier = Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(80.dp)) {
            ProfileBox()
            Spacer(Modifier.width(1.dp))
            discoverList(stateList)
        }

        Spacer(Modifier.height(10.dp))

        Text(
            text = "Recent Post",
            style = TextStyle(color = Color.Gray, fontSize = 20.sp)
        )
        postList(navController)
    }
}

@Composable
fun TopBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = R.drawable.socializeicon,
            contentDescription = "",
            modifier = Modifier.size(40.dp)
        )

        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "Socialize",
            style = TextStyle(
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xffff0f7b), Color(0xfff89b29))
                )
            )
        )
        Spacer(modifier = Modifier.weight(1f))

        AsyncImage(
            model = R.drawable.boy,
            contentDescription = "",
            modifier = Modifier.size(40.dp).clickable(
                indication = LocalIndication.current,
                interactionSource = remember { MutableInteractionSource() },
                enabled = true,
                onClick = {
                navController.navigate("profile") {
                    launchSingleTop = true
                }
            }
            ),
        )
        Spacer(modifier = Modifier.width(5.dp))

        NotificationBadge()
    }
}

@Composable
fun NotificationBadge() {
    Card(shape = RoundedCornerShape(20.dp)) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = R.drawable.bell,
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )
            Text(text = "3", style = TextStyle(fontWeight = FontWeight.Bold))
        }
    }
}

@Composable
fun TabRow(selectedTab: String, onTabSelected: (String) -> Unit) {
    Row {
        TabButton("Discover", selectedTab == "Discover") { onTabSelected("Discover") }
        Spacer(modifier = Modifier.width(15.dp))
        TabButton("Following", selectedTab == "Following") { onTabSelected("Following") }
    }
}

@Composable
fun TabButton(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Text(
        text = label,
        style = TextStyle(
            color = if (isSelected) Color.Black else Color.Gray,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        ),
        modifier = Modifier.clickable(
            indication = LocalIndication.current,
            interactionSource = remember { MutableInteractionSource() },
            onClick = onClick
        )
    )
}

@Composable
fun ProfileBox() {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(80.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        GradientCircle()

        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            shape = CircleShape,
            elevation = CardDefaults.elevatedCardElevation(4.dp)
        ) {
            AsyncImage(
                model = R.drawable.boy,
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }

        AsyncImage(
            model = R.drawable.button,
            contentDescription = "",
            modifier = Modifier
                .size(20.dp)
                .offset(y = 10.dp)
                .align(Alignment.BottomCenter)
                .background(Color.Transparent)
        )
    }
}

@Composable
fun GradientCircle() {
    Canvas(
        modifier = Modifier.size(80.dp)
    ) {
        val strokeWidth = 4.dp.toPx()
        drawCircle(
            brush = Brush.linearGradient(
                colors = listOf(Color.Red, Color.Blue),
                start = Offset(0f, 0f),
                end = Offset(size.width, size.height)
            ),
            radius = size.minDimension / 2,
            style = Stroke(strokeWidth)
        )
    }
}


@Composable
fun DrawerContent(navController: NavController, drawerState: DrawerState) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 70.dp).clip(RoundedCornerShape(topEnd = 30.dp, bottomEnd = 30.dp))
            .background(color = Color.White)
    ) {
        Text(
            text = "Navigation",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        DrawerItem("Profile", navController, drawerState, "home")
        DrawerItem("Security", navController, drawerState, "security")
        DrawerItem("Privacy", navController, drawerState, "privacy")
        DrawerItem("Help", navController, drawerState, "help")
        DrawerItem("About", navController, drawerState, "about")
    }
}

@Composable
fun DrawerItem(
    label: String,
    navController: NavController,
    drawerState: DrawerState,
    destination: String
) {
    val coroutineScope = rememberCoroutineScope()

    TextButton(onClick = {
        coroutineScope.launch {
            drawerState.close()  // Close the drawer when an item is clicked
        }
        navController.navigate(destination)
    }) {
        Text(text = label, color = Color.Black)
    }
}

fun hello() : Int{
    return 89
}