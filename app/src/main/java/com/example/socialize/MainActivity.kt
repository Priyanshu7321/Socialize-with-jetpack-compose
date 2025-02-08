package com.example.socialize

import android.widget.ImageView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.Alignment

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun home(navController: NavController){

    Box(modifier = Modifier
        .background(color = Color.White)
        .fillMaxSize()
        .padding(bottom = 20.dp), contentAlignment = Alignment.BottomCenter){
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val coroutineScope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DrawerContent(navController, drawerState)
            }
        ) {
            homeContent()
        }
        BottomNavigationBar()


    }
}

@Composable
fun BottomNavigationBar() {
    var selectedIcon by remember { mutableStateOf("Home") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .align(Alignment.BottomCenter),
            colors = CardDefaults.cardColors(
                containerColor = Color.Blue.copy(alpha = 0.3f) // Semi-transparent background
            ),
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
                    onClick = { selectedIcon = "Home" }
                )
                IconWithSelection(
                    isSelected = selectedIcon == "Chat",
                    painter = painterResource(R.drawable.chat),
                    contentDescription = "Chat",
                    onClick = { selectedIcon = "Chat" }
                )

                Spacer(Modifier.width(50.dp))
                IconWithSelection(
                    isSelected = selectedIcon == "Video",
                    painter = painterResource(R.drawable.video),
                    contentDescription = "Video",
                    onClick = { selectedIcon = "Video" }
                )
                IconWithSelection(
                    isSelected = selectedIcon == "Search",
                    icon = Icons.Filled.Search,
                    contentDescription = "Search",
                    onClick = { selectedIcon = "Search" }
                )
            }
        }
        Card(
            modifier = Modifier
                .height(60.dp) // Ensure the card height matches the parent
                .width(60.dp) // Elevate the card visually above the bar
                .clip(shape = CircleShape)
                .background(color = Color.White)
                .align(Alignment.Center),
            shape = CircleShape,
            elevation = CardDefaults.elevatedCardElevation(8.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(), // Make the box fill the card size
                contentAlignment = Alignment.Center // Center the icon inside the box
            ) {
                Image(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Plus",
                    colorFilter = ColorFilter.tint(color = Color.Blue),
                    modifier = Modifier
                        .size(40.dp) // Customize the icon size for scaling
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
    onClick: () -> Unit,
    isAddButton: Boolean = false
) {
    Box(
        modifier = Modifier
            .size(if (isAddButton) 50.dp else 40.dp)
            .background(
                color = if (isSelected) Color.Gray.copy(alpha = 0.5f) else Color.Transparent,
                shape = CircleShape
            )
            .clickable { onClick() }
            .shadow(if (isAddButton) 8.dp else 0.dp, CircleShape)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {

        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = Color.White,

                modifier = Modifier.size(if (isAddButton) 32.dp else 24.dp) // Larger icon size for Add button
            )
        } else if (painter != null) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.size(if (isAddButton) 32.dp else 24.dp)
            )
        }
    }
}

@Composable
fun GlideImage(
    imageUrl: Any,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            ImageView(context).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
        },
        update = { imageView ->
            Glide.with(imageView.context)
                .load(imageUrl)
                .into(imageView)
        }
    )
}
data class Status(val profileImage :String,val profileStatus: List<String>)

@Composable
fun discoverList(statusList: List<Status>) {
    val infiniteState = rememberLazyListState(initialFirstVisibleItemIndex = Int.MAX_VALUE / 2)
    val names = listOf(
        "Alice", "Bob", "Charlie", "David", "Eve",
        "Frank", "Grace", "Hannah", "Ivy", "Jack",
        "Karen", "Leo", "Mona", "Nina", "Oscar",
        "Paul", "Quinn", "Rita", "Steve", "Tina"
    )
    LazyRow(
        state = infiniteState,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        items(Int.MAX_VALUE) { index ->
            val actualIndex = index % statusList.size // Wrap the index using modulus
            val status = statusList[actualIndex] // Get the actual item
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .padding(4.dp) // Padding between items
                        .size(55.dp) // Fixed size for each item
                ) {
                    // Circular gradient border
                    Canvas(
                        modifier = Modifier
                            .matchParentSize()
                    ) {
                        val strokeWidth = 4.dp.toPx()
                        drawCircle(
                            brush = Brush.linearGradient(
                                colors = listOf(Color.Red, Color.Blue),
                                start = Offset(0f, 0f),
                                end = Offset(size.width, size.height)
                            ),
                            radius = size.minDimension / 2,
                            style = Stroke(strokeWidth),
                        )
                    }

                    // Image with circular card
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 10.dp, end = 10.dp, top = 4.dp, bottom = 4.dp),
                        shape = CircleShape, // Make the card circular
                        elevation = CardDefaults.elevatedCardElevation(4.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.boy6),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                Spacer(Modifier.width(1.dp)) // Space between cards
                Text(names[index%20])
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
fun postList(){

    LazyColumn(modifier = Modifier
        .fillMaxHeight()
        .padding(top = 15.dp)) {
        items(20){
            it->
            val cardColor = generateRandomColor()
            val scrollState = rememberScrollState()
            Card (modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp, top = 10.dp)
                .background(Color.White),
                shape = RoundedCornerShape(30.dp),
                elevation = CardDefaults.elevatedCardElevation(4.dp), colors = CardDefaults.cardColors(containerColor = cardColor)) {
                Column(Modifier.padding(15.dp)) {
                    Row (modifier = Modifier
                        .height(60.dp)
                        .padding(4.dp)){
                        Image(painter = painterResource(R.drawable.boy), contentDescription = "", modifier = Modifier.size(50.dp))
                        Column(modifier = Modifier
                            .fillMaxHeight()
                            .padding(start = 5.dp), verticalArrangement = Arrangement.Center) {
                            Text(text = "Alice", style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp) )
                            Text(text = "Posted 1h ago", style = TextStyle(color = Color.Gray,))
                        }
                        Row(Modifier.fillMaxWidth().fillMaxHeight(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(R.drawable.menulist),
                                colorFilter = ColorFilter.tint(color = Color.Black),
                                contentDescription = "",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Text(text = "Discover adventure in patogania's peaks or serenity provences @helmets-arrival", style = TextStyle(fontSize = 15.sp))
                    Spacer(Modifier.height(10.dp))
                    Row(modifier = Modifier.horizontalScroll(scrollState)) {
                        GlideImage(R.drawable.forest1, modifier = Modifier.size(150.dp))

                        Spacer(Modifier.width(5.dp))
                        GlideImage(R.drawable.forest1, modifier = Modifier.size(150.dp))

                    }
                    Row(modifier = Modifier.height(40.dp), verticalAlignment = Alignment.CenterVertically){
                        Row (modifier = Modifier.height(40.dp), verticalAlignment = Alignment.CenterVertically){
                            Image(painter = painterResource(R.drawable.like), modifier = Modifier.size(27.dp), contentDescription = "")
                            Text(text = " 349 Likes", style = TextStyle(color = Color.Gray))
                        }
                        Spacer(modifier = Modifier.width(15.dp))
                        Row (modifier = Modifier.height(40.dp), verticalAlignment = Alignment.CenterVertically){
                            Image(
                                painter = painterResource(R.drawable.comment),
                                modifier = Modifier.size(27.dp),
                                contentDescription = ""
                            )
                            Text(text = " 520 Comments", style = TextStyle(color = Color.Gray))
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Image(painter = painterResource(R.drawable.share), modifier = Modifier.size(23.dp), contentDescription = "")

                    }
                }
            }

            Spacer(Modifier.height(5.dp))
        }
    }
}

@Composable
fun homeContent(){
    var stateList= listOf(Status("skfjk", listOf("skfk","sjkfjksj")),Status("skfjk", listOf("skfk","sjkfjksj")),Status("skfjk", listOf("skfk","sjkfjksj")))
    var colorstate by remember{ mutableStateOf("Discover") }
    Column (modifier = Modifier.padding(top = 15.dp, start = 10.dp, end = 10.dp)){
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(end = 10.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(R.drawable.letters), contentDescription = "",modifier=Modifier.size(40.dp))

            Spacer(modifier = Modifier.width(5.dp))
            Text(text = "Socialize", style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold, brush = Brush.linearGradient(
                colors = listOf(Color(0xffff0f7b),Color(0xfff89b29))
            )))
            Spacer(modifier = Modifier.weight(1f))
            Image(painter = painterResource(R.drawable.boy), contentDescription = "",modifier=Modifier.size(50.dp))
            Spacer(modifier = Modifier.width(5.dp))
            Card(shape = RoundedCornerShape(20.dp)) {
                Row (modifier = Modifier.padding(start = 10.dp, end = 10.dp,top=5.dp,bottom=5.dp),verticalAlignment = Alignment.CenterVertically){
                    Image(painter = painterResource(R.drawable.bell), contentDescription = "",modifier=Modifier.size(30.dp))
                    Text(text = "3", style = TextStyle(fontWeight = FontWeight.Bold))
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Text(text = "Discover", style = TextStyle(color = if (colorstate=="Discover") Color.Black else Color.Gray, fontSize = 20.sp, fontWeight = FontWeight.Bold),modifier=Modifier.clickable{colorstate="Discover"})
            Spacer(modifier = Modifier.width(15.dp))
            Text(text = "Following", style = TextStyle(color = if (colorstate=="Following") Color.Black else Color.Gray, fontSize = 20.sp, fontWeight = FontWeight.Bold),modifier=Modifier.clickable{colorstate="Following"})

        }
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Box(
                modifier = Modifier
                    .padding(4.dp) // Padding between items
                    .size(80.dp),
                contentAlignment = Alignment.BottomCenter// Fixed size for each item
            ) {
                // Drawing the gradient border
                Canvas(
                    modifier = Modifier
                        .matchParentSize() // Ensure Canvas matches the Box size
                ) {
                    val strokeWidth = 4.dp.toPx() // Set the border width
                    drawCircle(
                        brush = Brush.linearGradient(
                            colors = listOf(Color.Red, Color.Blue),
                            start = Offset(0f, 0f),
                            end = Offset(size.width, size.height)
                        ),
                        radius = size.minDimension / 2, // Draw circle within bounds
                        style = Stroke(strokeWidth) // Draws just the border
                    )
                }

                // Circular Card background
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
                    , // No inner padding, so it aligns with the border
                    shape = CircleShape, // Make the card circular
                    elevation = CardDefaults.elevatedCardElevation(4.dp)
                ) {
                    // Internal content (replace with actual content from the Status object)
                    Image(
                        painter = painterResource(R.drawable.boy), // Replace with dynamic content as needed
                        contentDescription = "",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize() // Ensure the image fills the card
                    )
                }
                    Image(painter = painterResource(R.drawable.add), contentDescription = "",  modifier = Modifier
                        .size(20.dp).offset(y=10.dp).align(Alignment.BottomCenter).background(color=Color.White))

            }
            Spacer(Modifier.width(1.dp))
            discoverList(stateList)
        }
        Spacer(Modifier.height(10.dp))
        Text(text = "Recent Post", style = TextStyle(color = Color.Gray, fontSize = 20.sp))
        postList()
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
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun viewPreview(){
    var navController= rememberNavController()
    home(navController )
}