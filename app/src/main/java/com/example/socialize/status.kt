package com.example.socialize

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlin.math.roundToInt
@Composable
fun SwipeableCards(navController: NavController) {
    var currentIndex by remember { mutableStateOf(0) }
    val totalCards = 5

    val cardOffsets = remember { mutableStateOf(0f) }
    val dragOffset = animateFloatAsState(targetValue = cardOffsets.value, label = "")

    val cards = List(totalCards) { "Card #$it" }
    val initialProgressData = listOf(
        ProgressData(0f, Color.Red),
        ProgressData(0f, Color.Green),
        ProgressData(0f, Color.Blue),
        ProgressData(0f, Color.Yellow),
        ProgressData(0f, Color.Cyan)
    )
    val progressData = remember { mutableStateOf(initialProgressData) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(
                colors = listOf(Color.Yellow.copy(alpha = 0.5f),Color.Red.copy(0.5f)),
                start = Offset(0f,0f),
                end = Offset(1000f,1000f)
            )),

    ) {
        for (i in cards.indices) {
            if (i == currentIndex || i == currentIndex + 1) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .offset(x = if (i == currentIndex) dragOffset.value.dp else 0.dp)
                        .pointerInput(Unit) {
                            detectHorizontalDragGestures { _, dragAmount ->
                                cardOffsets.value = (cardOffsets.value + dragAmount).coerceIn(-500f, 500f)
                            }
                        },
                    colors=CardDefaults.cardColors(containerColor = Color.Transparent),

                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = cards[i], color = Color.White, modifier = Modifier.padding(bottom = 16.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            for (progressPair in progressData.value) {
                                LinearProgressIndicator(
                                    progress = progressPair.progress,
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(4.dp)
                                        .padding(horizontal = 8.dp)
                                        .background(color = Color.Blue),
                                    color = progressPair.color,

                                )
                            }
                        }
                    }
                }
            }
        }
        var stateList= listOf(Status("skfjk", listOf("skfk","sjkfjksj")),Status("skfjk", listOf("skfk","sjkfjksj")),Status("skfjk", listOf("skfk","sjkfjksj")),Status("skfjk", listOf("skfk","sjkfjksj")),Status("skfjk", listOf("skfk","sjkfjksj")))

        Row(modifier=Modifier.align(Alignment.BottomCenter).padding(bottom = 10.dp)) {
            discoverList(stateList)
        }

        LaunchedEffect(dragOffset.value) {
            if (dragOffset.value >= 200f) { // Threshold to consider the swipe completed
                currentIndex = (currentIndex + 1) % totalCards
                cardOffsets.value = 0f
                progressData.value = initialProgressData
            }
        }
    }

    LaunchedEffect(currentIndex) {
        for (i in progressData.value.indices) {
            while (progressData.value[i].progress < 1f) {
                delay(10) // Update every 10ms
                progressData.value = progressData.value.mapIndexed { index, data ->
                    if (index == i) {
                        val newProgress = (data.progress + 0.01f).coerceAtMost(1f)
                        data.copy(progress = newProgress)
                    } else {
                        data
                    }
                }
            }
        }
    }
}


data class ProgressData(val progress: Float, val color: Color)

@Preview(showBackground = true)
@Composable
fun PreviewSwipeableCards() {
    var navController= rememberNavController()
    SwipeableCards(navController )
}

