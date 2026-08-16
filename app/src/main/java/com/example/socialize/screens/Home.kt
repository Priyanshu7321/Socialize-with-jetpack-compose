package com.example.socialize.composables

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.socialize.R
import com.example.socialize.screens.Settings
import com.example.socialize.screens.SwipeableCards
import com.example.socialize.screens.Users
import com.example.socialize.screens.VideoCallWebView
import com.example.socialize.screens.chats
import com.example.socialize.screens.members
import com.example.socialize.screens.post
import com.example.socialize.screens.profileforother
import com.example.socialize.screens.profileforus
import com.example.socialize.screens.videoView
import com.example.socialize.ui.theme.Dimens
import com.kashif_e.backdrop.backdrops.rememberLayerBackdrop
import com.kashif_e.backdrop.drawBackdrop
import com.kashif_e.backdrop.effects.blur
import com.kashif_e.backdrop.effects.colorControls
import com.kashif_e.backdrop.effects.lens
import com.kashif_e.backdrop.highlight.Highlight
import com.kashif_e.backdrop.highlight.HighlightStyle
import com.kashif_e.backdrop.shadow.InnerShadow
import com.kashif_e.backdrop.shadow.Shadow
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants
import kotlin.math.PI
import kotlin.math.sin
import kotlin.random.Random

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun home(navControllerHost: NavController,innerPadding: PaddingValues) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    var selectedIcon by remember { mutableStateOf("Home") }
    val navController = rememberNavController()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val bottomBarRoutes = listOf("home", "members", "videoCall", "users")

    Scaffold(
        containerColor = Color.Transparent // ✅ important
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .padding(bottom = innerPadding.calculateBottomPadding())
            ,
            contentAlignment = Alignment.BottomCenter
        ) {

            // 🔹 Main App Content
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    DrawerContent(navController, drawerState)
                }
            ) {
                NavHost(
                    navController = navController,
                    startDestination = "home",
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable("home") { homeContent(navController) }
                    composable("profile") { profileforus(navController) }
                    composable("profileforother") { profileforother(navController) }
                    composable("chats") { chats(navController) }
                    composable("posting") { post(navController) }
                    composable("members") { members(navController) }
                    composable("status") { SwipeableCards(navController) }
                    composable("videoCall") { videoView(navController) }
                    composable(
                        "videoCallWebView/{userId}/{otherUserId}",
                        arguments = listOf(
                            navArgument("userId") { type = NavType.StringType },
                            navArgument("otherUserId") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        VideoCallWebView(
                            navController,
                            backStackEntry.arguments?.getString("userId") ?: "",
                            backStackEntry.arguments?.getString("otherUserId") ?: ""
                        )
                    }
                    composable("users") { Users(navController) }
                    composable("settings") { Settings(navController,navControllerHost) }
                }
            }

            // 🔥 Floating Bottom Bar (Overlay)
            if (currentRoute in bottomBarRoutes) {
                BottomNavigationBar(
                    navController = navController,
                    selectedIcon = selectedIcon
                ) { selected ->
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
    }
}

/**
 * Draws a convex bump using Canvas — separate from the backdrop shape
 * so lens() can use RoundedCornerShape (CornerBasedShape requirement).
 */
fun drawConvexBump(
    drawScope: androidx.compose.ui.graphics.drawscope.DrawScope,
    bumpFraction: Float,
    bumpWidthPx: Float,
    bumpHeightPx: Float,
    color: Color
) {
    val w = drawScope.size.width
    val cx = bumpFraction * w
    val bw = bumpWidthPx / 2f
    val bumpStart = (cx - bw).coerceIn(0f, w)
    val bumpEnd = (cx + bw).coerceIn(0f, w)
    val path = Path().apply {
        moveTo(bumpStart, 0f)
        cubicTo(bumpStart + bw * 0.3f, 0f, cx - bw * 0.1f, -bumpHeightPx, cx, -bumpHeightPx)
        cubicTo(cx + bw * 0.1f, -bumpHeightPx, bumpEnd - bw * 0.3f, 0f, bumpEnd, 0f)
        close()
    }
    drawScope.drawPath(path, color)
    // Subtle highlight stroke on bump edge
    val strokePath = Path().apply {
        moveTo(bumpStart, 0f)
        cubicTo(bumpStart + bw * 0.3f, 0f, cx - bw * 0.1f, -bumpHeightPx, cx, -bumpHeightPx)
        cubicTo(cx + bw * 0.1f, -bumpHeightPx, bumpEnd - bw * 0.3f, 0f, bumpEnd, 0f)
    }
    drawScope.drawPath(strokePath, Color.White.copy(alpha = 0.5f), style = Stroke(width = 1.5f))
}

@Composable
fun BottomNavigationBar(navController: NavController, selectedIcon: String, onIconSelected: (String) -> Unit) {
    val selectorBackdrop = rememberLayerBackdrop()
    val density = LocalDensity.current
    val scope = rememberCoroutineScope()

    val tabFractions = mapOf("Home" to 0.1f, "Chat" to 0.32f, "Video" to 0.68f, "Search" to 0.9f)
    val targetFraction = tabFractions[selectedIcon] ?: 0.1f

    val selectorX = remember { Animatable(targetFraction) }
    // Controls glass visibility: 1f = fully visible during drag, 0f = invisible at rest
    val glassAlpha = remember { Animatable(0f) }
    var isDragging by remember { mutableStateOf(false) }

    val prevSelected = remember { mutableStateOf(selectedIcon) }
    if (prevSelected.value != selectedIcon && !isDragging) {
        prevSelected.value = selectedIcon
        scope.launch {
            selectorX.animateTo(
                targetValue = targetFraction,
                animationSpec = spring(dampingRatio = 0.5f, stiffness = Spring.StiffnessMediumLow)
            )
        }
    }

    val selectorWidthDp = 52.dp
    val selectorHeightDp = 36.dp
    val selectorWidthPx = with(density) { selectorWidthDp.toPx() }
    val selectorShape = RoundedCornerShape(50)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = Dimens.horizontalPadding, end = Dimens.horizontalPadding, bottom = 16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .align(Alignment.Center),
            shape = RoundedCornerShape(32.dp),
            elevation = CardDefaults.elevatedCardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures(
                            onDragStart = {
                                isDragging = true
                                scope.launch {
                                    // Fade glass in instantly on drag start
                                    glassAlpha.animateTo(1f, animationSpec = tween(120))
                                }
                            },
                            onDragEnd = {
                                isDragging = false
                                val nearest = tabFractions.minByOrNull { kotlin.math.abs(it.value - selectorX.value) }
                                if (nearest != null) {
                                    scope.launch {
                                        // Snap to tab with spring, then fade glass out
                                        selectorX.animateTo(
                                            nearest.value,
                                            animationSpec = spring(dampingRatio = 0.4f, stiffness = Spring.StiffnessMedium)
                                        )
                                        glassAlpha.animateTo(0f, animationSpec = tween(250))
                                    }
                                    onIconSelected(nearest.key)
                                }
                            },
                            onDragCancel = {
                                isDragging = false
                                scope.launch { glassAlpha.animateTo(0f, animationSpec = tween(200)) }
                            },
                            onHorizontalDrag = { change, dragAmount ->
                                change.consume()
                                scope.launch {
                                    selectorX.snapTo((selectorX.value + dragAmount / size.width).coerceIn(0.05f, 0.95f))
                                }
                            }
                        )
                    }
            ) {
                // Glass effect — only visible while dragging (controlled by glassAlpha)
                if (glassAlpha.value > 0f) {
                    val barWidthPx = with(density) {
                        (LocalContext.current.resources.displayMetrics.widthPixels - (Dimens.horizontalPadding * 2).toPx())
                    }
                    val selectorOffsetX = (selectorX.value * barWidthPx - selectorWidthPx / 2f)
                        .coerceIn(0f, barWidthPx - selectorWidthPx)

                    Box(
                        modifier = Modifier
                            .offset { androidx.compose.ui.unit.IntOffset(selectorOffsetX.toInt(), 0) }
                            .width(selectorWidthDp)
                            .height(selectorHeightDp)
                            .alpha(glassAlpha.value)
                            .align(Alignment.CenterStart)
                            .drawBackdrop(
                                backdrop = selectorBackdrop,
                                shape = { selectorShape },
                                effects = {
                                    blur(radius = 30f)
                                    colorControls(brightness = 0.1f, contrast = 1.15f, saturation = 1.3f)
                                    lens(
                                        refractionHeight = 8f.dp.toPx(),
                                        refractionAmount = 16f.dp.toPx(),
                                        depthEffect = true,
                                        chromaticAberration = true
                                    )
                                },
                                highlight = { Highlight(width = 1.dp, alpha = 0.8f, style = HighlightStyle.Ambient) },
                                shadow = { Shadow(radius = 12.dp, color = Color.Black.copy(alpha = 0.12f)) },
                                innerShadow = { InnerShadow(radius = 6.dp, color = Color.White.copy(alpha = 0.4f), offset = DpOffset(0.dp, (-1).dp)) }
                            )
                    )
                }

                // Icons
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconWithSelection(isSelected = selectedIcon == "Home", icon = Icons.Filled.Home, contentDescription = "Home", onClick = { onIconSelected("Home") })
                    IconWithSelection(isSelected = selectedIcon == "Chat", painter = rememberAsyncImagePainter(R.drawable.chat), contentDescription = "Chat", onClick = { onIconSelected("Chat") })
                    Spacer(Modifier.width(56.dp))
                    IconWithSelection(isSelected = selectedIcon == "Video", painter = rememberAsyncImagePainter(R.drawable.video), contentDescription = "Video", onClick = { onIconSelected("Video") })
                    IconWithSelection(isSelected = selectedIcon == "Search", icon = Icons.Filled.Search, contentDescription = "Search", onClick = { onIconSelected("Search") })
                }            }
        }

        // FAB
        Card(
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.BottomCenter)
                .offset(y = (-4).dp)
                .clickable { navController.navigate("posting") },
            shape = CircleShape,
            elevation = CardDefaults.elevatedCardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xfff89b29))
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Image(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Post",
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier.size(28.dp)
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
            .size(if (isAddButton) 50.dp else 42.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) Color(0xfff89b29).copy(alpha = 0.15f) else Color.Transparent)
            .clickable { onClick() }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = if (isSelected) Color(0xfff89b29) else Color.Gray,
                modifier = Modifier.size(if (isAddButton) 32.dp else 24.dp)
            )
        } else if (painter != null) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                colorFilter = ColorFilter.tint(if (isSelected) Color(0xfff89b29) else Color.Gray),
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
fun discoverList(statusList: List<Status>, navController: NavController) {
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
            .height(88.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyRow(
            state = infiniteState,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            contentPadding = PaddingValues(top = 4.dp, start = 4.dp, end = 4.dp)
        ) {
            items(statusList.size * 10) { index ->
                val actualIndex = index % statusList.size

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                        .clickable { navController.navigate("status") }
                ) {
                    Box(modifier = Modifier.size(48.dp)) {
                        GradientCircle()
                        Card(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(3.dp),
                            shape = CircleShape,
                            elevation = CardDefaults.elevatedCardElevation(4.dp)
                        ) {
                            AsyncImage(
                                model = R.drawable.woman,
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                    Spacer(Modifier.height(3.dp))
                    Text(names[actualIndex % names.size], fontSize = 11.sp)
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
                .background(Color.Transparent),
                shape = RoundedCornerShape(30.dp),
                elevation = CardDefaults.elevatedCardElevation(4.dp), colors = CardDefaults.cardColors(containerColor = cardColor)) {
                Column(Modifier.padding(15.dp)) {
                    Row (modifier = Modifier
                        .height(60.dp)
                        .padding(4.dp)){
                        Image(painter = rememberAsyncImagePainter( (R.drawable.boy)), contentDescription ="" , modifier = Modifier.size(50.dp).clickable(
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

    Box() {
        Column(
            modifier = Modifier
                .padding(horizontal = Dimens.horizontalPadding)
                .statusBarsPadding()
        ) {
            // Static header — does not scroll
            TopBar(navController)
            Spacer(modifier = Modifier.height(10.dp))
            TabRow(colorState) { colorState = it }
            Spacer(modifier = Modifier.height(10.dp))

            // Scrollable content — story + posts scroll together
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.height(95.dp)
                    ) {
                        ProfileBox()
                        Spacer(Modifier.width(1.dp))
                        discoverList(stateList, navController)
                    }
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = "Recent Post",
                        style = TextStyle(color = Color.Gray, fontSize = 20.sp)
                    )
                    Spacer(Modifier.height(5.dp))
                }
                items(20) { it ->
                    val cardColor = generateRandomColor()
                    val scrollState = rememberScrollState()
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp, end = 5.dp, top = 5.dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(30.dp),
                                ambientColor = Color(0xFFFF9800).copy(alpha = 0.30f),
                                spotColor = Color(0xFFFF9800).copy(alpha = 0.35f)
                            ),
                        shape = RoundedCornerShape(30.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ){
                        Column(Modifier.padding(15.dp)) {
                            Row(modifier = Modifier.height(60.dp).padding(end = 4.dp, bottom = 4.dp)) {
                                Image(
                                    painter = rememberAsyncImagePainter(R.drawable.boy),
                                    contentDescription = "",
                                    modifier = Modifier.size(50.dp).clickable(
                                        enabled = true,
                                        onClick = {
                                            navController.navigate("profileforother") {
                                                launchSingleTop = true
                                            }
                                        }
                                    )
                                )
                                Column(
                                    modifier = Modifier.fillMaxHeight().padding(start = 5.dp),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(text = "Alice", style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp))
                                    Text(text = "Posted 1h ago", style = TextStyle(color = Color.Gray))
                                }
                                Row(
                                    Modifier.fillMaxWidth().fillMaxHeight(),
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = rememberAsyncImagePainter(R.drawable.menulist),
                                        colorFilter = ColorFilter.tint(color = Color.Black),
                                        contentDescription = "",
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Text(text = "Discover adventure in patogania's peaks or serenity provences @helmets-arrival", style = TextStyle(fontSize = 15.sp))
                            Spacer(Modifier.height(10.dp))
                            Row(modifier = Modifier.horizontalScroll(scrollState)) {
                                GlideImage(imageUrl = R.drawable.forest2, modifier = Modifier.size(150.dp), contentScale = ContentScale.Crop)
                                Spacer(Modifier.width(5.dp))
                                GlideImage(imageUrl = R.drawable.forest2, modifier = Modifier.size(150.dp), contentScale = ContentScale.Crop)
                            }
                            Row(modifier = Modifier.height(40.dp), verticalAlignment = Alignment.Bottom) {
                                Row(modifier = Modifier.height(40.dp), verticalAlignment = Alignment.Bottom) {
                                    Image(painter = rememberAsyncImagePainter(R.drawable.like), modifier = Modifier.size(20.dp), contentDescription = "")
                                    Text(text = "349", style = TextStyle(color = Color.Gray), modifier = Modifier.padding(start = 4.dp))
                                }
                                Spacer(modifier = Modifier.width(15.dp))
                                Row(modifier = Modifier.height(40.dp), verticalAlignment = Alignment.Bottom) {
                                    Image(painter = rememberAsyncImagePainter(R.drawable.comment), modifier = Modifier.size(20.dp), contentDescription = "")
                                    Text(text = "520", style = TextStyle(color = Color.Gray), modifier = Modifier.padding(start = 4.dp))
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                Image(painter = rememberAsyncImagePainter(R.drawable.share), modifier = Modifier.size(18.dp), contentDescription = "")
                            }
                        }
                    }
                    Spacer(Modifier.height(5.dp))
                }
            }
        }
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
                enabled = true,
                onClick = {
                navController.navigate("profile") {
                    launchSingleTop = true
                }
            }
            ),
        )
        Spacer(modifier = Modifier.width(5.dp))
        Box(modifier = Modifier.clickable(onClick = {navController.navigate("settings"){launchSingleTop = true} }, enabled = true)){
            NotificationBadge()
        }

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
            onClick = onClick
        )
    )
}

@Composable
fun ProfileBox() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {Box(
        modifier = Modifier
            .padding(4.dp)
            .size(54.dp)
        ,

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
                .size(16.dp)
                .offset(y = 8.dp)
                .align(Alignment.BottomCenter)
                .background(Color.Transparent)
        )
    }

        Spacer(Modifier.height(3.dp))
        Text("You", fontSize = 11.sp)
    }

}

@Composable
fun GradientCircle() {
    Canvas(
        modifier = Modifier.size(54.dp)
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
        DrawerItem("Settings", navController, drawerState, "settings")
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