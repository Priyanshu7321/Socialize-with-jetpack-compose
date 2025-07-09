package com.example.socialize.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.socialize.R
import kotlinx.coroutines.delay
import androidx.compose.foundation.layout.systemBarsPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableCards(navController: NavController) {
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val screenWidth = LocalConfiguration.current.screenWidthDp
    var imageList= listOf(R.drawable.boy,R.drawable.boy1,R.drawable.boy2)
    var count= remember { mutableIntStateOf(0) }
    var progressList= remember { mutableStateListOf(0f,0f,0f) }
    var statusReply = remember{
        mutableStateOf("Write your review")
    }
    LaunchedEffect(Unit) {
        for(i in progressList.indices){
            while (progressList[i]<1f){
                delay(300)
                progressList[i]+=0.1f
            }
            count.value++
        }
    }
    Box(modifier = Modifier.height(screenHeight.dp).width(screenWidth.dp).clickable(onClick = {count.value++}).background(color=Color.White.copy(alpha=0.2f)).systemBarsPadding()){
        Row(Modifier.fillMaxWidth()) {
            progressList.indices.forEach {index->
                LinearProgressIndicator(
                    progress = { progressList[index] },
                    modifier = Modifier.weight(1f).padding(5.dp),
                )
            }
        }
        Image(
            painter = rememberAsyncImagePainter( imageList[(count.value)%3]),
            contentDescription = "",
            modifier = Modifier.fillMaxSize()
        )
        Box(Modifier.padding(top = 20.dp)) {
            Box(modifier = Modifier.fillMaxWidth().height(50.dp).padding(end = 10.dp)) {
                Row(
                    modifier = Modifier.height(50.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(R.drawable.back),
                        contentDescription = "",
                        modifier = Modifier.padding(10.dp, 10.dp)
                    )
                    Spacer(Modifier.width(5.dp))
                    Image(
                        painter = rememberAsyncImagePainter(R.drawable.boy),
                        contentDescription = ""
                    )
                    Spacer(Modifier.width(5.dp))
                    Column(Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                        Text(
                            text = "Akshay",
                            style = TextStyle(fontSize = 18.sp)
                        )
                        Text(
                            text = "posted 8h ago",
                        )
                    }

                }
                Image(
                    painter = rememberAsyncImagePainter(R.drawable.menulist),
                    contentDescription = "",
                    modifier = Modifier.align(Alignment.BottomEnd).padding(13.dp, 13.dp),
                    colorFilter = ColorFilter.tint(color = Color.Black)
                )
            }
        }
        Row(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 15.dp)
                .background(Color.Transparent) // <-- Make Row background transparent
        ) {
            Card(
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth()
                    .weight(6f).align(Alignment.CenterVertically),
                shape = RoundedCornerShape(30.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp), // <-- No shadow/elevation
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black // or whatever content color
                )
            ) {
                TextField(
                    value = statusReply.value,
                    onValueChange = { statusReply.value = it },
                    label = { Text("Enter text") },
                    modifier = Modifier.padding(all=5.dp).weight(5f),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent, // <-- Also this is needed
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )
            }

            Spacer(Modifier.width(5.dp))

            Card(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .align(Alignment.CenterVertically),
                shape = CircleShape,
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp), // Optional if you want it flat
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                // You can place an icon or button here
            }
        }


    }
}
