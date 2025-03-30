package com.example.socialize.composables

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SwipeableCards(navController: NavController) {
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val screenWidth = LocalConfiguration.current.screenWidthDp

    // Keeps track of the currently selected item
    var selectedItem by remember { mutableStateOf(0) }
    var list : List<List<String>> = listOf(
        listOf("A section 1","A section 2"),
        listOf("B section 1","B section 2")
    )
    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemIndex }
            .collect { index ->
                coroutineScope.launch {
                    delay(300) // Small delay to let user finish scrolling
                    lazyListState.animateScrollToItem(index) // Snap to the nearest item
                    selectedItem = index
                }
            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color.White.copy(alpha = 0.5f), Color.Blue.copy(0.5f)),
                    start = Offset(0f, 0f),
                    end = Offset(1000f, 1000f)
                )
            ),
    ) {
        LazyRow(
            state = lazyListState,
            modifier = Modifier.fillMaxSize().padding(10.dp),
            flingBehavior = rememberSnapFlingBehavior(lazyListState) // Ensures snapping effect
        ) {
            items(10) { item ->
                val isSelected = item == selectedItem

                // Rotation Effect
                val rotationAngle by animateFloatAsState(
                    targetValue = if (isSelected) 0f else if (item < selectedItem) -10f else 10f,
                    animationSpec = tween(500), label = ""
                )

                // Scaling Effect
                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 1.1f else 1f,
                    animationSpec = tween(500), label = ""
                )

                // Opacity Effect
                val alpha by animateFloatAsState(
                    targetValue = if (isSelected) 1f else 0.5f,
                    animationSpec = tween(500), label = ""
                )

                Box(
                    modifier = Modifier
                        .height(screenHeight.dp - 10.dp)
                        .width(screenWidth.dp - 10.dp)
                        .graphicsLayer {
                            rotationZ = rotationAngle
                            scaleX = scale
                            scaleY = scale
                            this.alpha = alpha
                        }
                        .background(Color.Cyan, shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Item #$item", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSwipeableCards() {
    val navController = rememberNavController()
    SwipeableCards(navController)
}
