package com.example.socialize.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.socialize.R
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.draw.shadow
import coil.compose.AsyncImage

@Composable
fun OptimizedAsyncImage(
    model: Any,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(model)
            .size(width = 800, height = 600) // Limit size to prevent memory issues
            .build(),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun profileforus(navController: NavController){
    val midh = LocalConfiguration.current.screenHeightDp/2
    val midw = LocalConfiguration.current.screenWidthDp/2-50
    var select by remember{ mutableIntStateOf(0) }
    val profileThreeOptions : List<String> = listOf("Post","Bookmarks","Followers")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 5.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(bottom = 8.dp),
            contentAlignment = Alignment.BottomCenter
        ){
            Card(shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomEnd = 20.dp, bottomStart = 20.dp)){
                OptimizedAsyncImage(
                    model = R.drawable.forest2,
                    contentDescription = "",
                    modifier = Modifier.height(200.dp).fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
            Box (
                Modifier
                    .height(110.dp)
                    .width(110.dp)
                    .offset(y=55.dp)
                    .padding(3.dp),
            ){
                Canvas(modifier = Modifier.size(170.dp)) {
                    drawCircle(
                        brush = Brush.linearGradient(
                            colors= listOf(Color.White,Color.White)
                        ),
                        radius = size.minDimension/2,
                        style = Stroke(22F)
                    )
                }
                Card(modifier = Modifier.fillMaxSize().padding(3.dp), shape = CircleShape, colors = CardDefaults.cardColors(containerColor = Color.White)){
                    Image(painter = rememberAsyncImagePainter(R.drawable.boy), contentDescription = "")
                }
            }
        }
        Spacer(Modifier.height(55.dp))
        Text(
            modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally), textAlign = TextAlign.Center,
            text = "George",
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 25.sp)
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(horizontal = 32.dp, vertical = 8.dp),
            text = "I'm delighted to introduce myself as a professional model",
            style = TextStyle(fontSize = 15.sp, color = Color.Gray, textAlign = TextAlign.Center)
        )
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .padding(vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Card (modifier = Modifier.height(70.dp).align(Alignment.Bottom), elevation = CardDefaults.elevatedCardElevation(3.dp), colors = CardDefaults.cardColors(containerColor = Color.White)){
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.height(70.dp).padding(10.dp)) {
                    Text(text = "50.5K", fontWeight = FontWeight.Bold)
                    Text(text = "Following", style = TextStyle(color=Color.Gray))
                }
            }
            Card (modifier = Modifier.height(70.dp), elevation = CardDefaults.elevatedCardElevation(3.dp), colors = CardDefaults.cardColors(containerColor = Color.White)){
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.height(70.dp).padding(10.dp)) {
                    Text(text = "30.5K", fontWeight = FontWeight.Bold)
                    Text(text = "Followers", style = TextStyle(color = Color.Gray))
                }
            }

            Card (modifier = Modifier.height(70.dp).align(Alignment.Bottom), elevation = CardDefaults.elevatedCardElevation(3.dp), colors = CardDefaults.cardColors(containerColor = Color.White)){
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.height(70.dp).padding(18.dp)) {
                    Text(text = "255", fontWeight = FontWeight.Bold)
                    Text(text = "Posts", style = TextStyle(color = Color.Gray))
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(vertical = 8.dp)
                .clip(shape = RoundedCornerShape(20.dp))
                .background(color = Color.Gray.copy(alpha = 0.2f))
                .padding(5.dp)
                .align(Alignment.CenterHorizontally)
        ){
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(20.dp))
                    .background(color = if (select == 0) Color.White else Color.Transparent)
                    .clickable { select = 0 }
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(R.drawable.collage),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )

                    if (select == 0) {
                        Spacer(Modifier.height(4.dp))
                        Text("Post", modifier = Modifier.weight(1f))
                    }
                }
            }
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(20.dp))
                    .background(color = if (select == 1) Color.White else Color.Transparent)
                    .clickable { select = 1 }
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(R.drawable.bookmark),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )

                    if (select == 1) {
                        Spacer(Modifier.height(4.dp))
                        Text("Post", modifier = Modifier.weight(1f))
                    }
                }
            }
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(20.dp))
                    .background(color = if (select == 1) Color.White else Color.Transparent)
                    .clickable { select = 1 }
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(R.drawable.custexp),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )

                    if (select == 2) {
                        Spacer(Modifier.height(4.dp))
                        BoxWithConstraints(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(horizontal = 8.dp)
                        ) {
                            val textSize = if (maxWidth < 200.dp) 12.sp else if (maxWidth < 300.dp) 14.sp else 16.sp

                            Text(
                                text = profileThreeOptions[2],
                                fontSize = textSize,
                                textAlign = TextAlign.Center,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

            }


        }

    }
}

@Composable
fun profileforother(navController: NavController){
    var midh= LocalConfiguration.current.screenHeightDp/2
    var midw= LocalConfiguration.current.screenWidthDp/2-50
    var select by remember{ mutableIntStateOf(0) }
    Column(modifier=Modifier.fillMaxSize()){
        Box(modifier=Modifier.fillMaxWidth().height(250.dp).padding(5.dp),){
            Card(shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomEnd = 40.dp, bottomStart = 40.dp)){
                OptimizedAsyncImage(
                    model = R.drawable.forest2, // Using smaller image (65KB instead of 2.4MB)
                    contentDescription = "",
                    modifier = Modifier.height(200.dp).fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
            Box (Modifier.height(100.dp).width(100.dp).offset(x=30.dp,y=150.dp)){
                Canvas(modifier = Modifier.size(180.dp)) {
                    drawCircle(
                        brush = Brush.linearGradient(
                            colors= listOf(Color.White,Color.White)
                        ),
                        radius = size.minDimension/2,
                        style = Stroke(22F)
                    )
                }
                Card(modifier = Modifier.fillMaxSize().padding(), shape = CircleShape, colors = CardDefaults.cardColors(containerColor = Color.White)){
                    Image(painter = rememberAsyncImagePainter(R.drawable.boy), contentDescription = "")
                }
            }
            Card(modifier=Modifier.offset(midw.dp+30.dp,175.dp).size(50.dp), shape = RoundedCornerShape(20.dp), elevation = CardDefaults.cardElevation(5.dp)){
                Box(Modifier.fillMaxSize().background(Color.White),contentAlignment = Alignment.Center){
                    Image(painter = rememberAsyncImagePainter(R.drawable.message), contentDescription = "",modifier=Modifier.size(40.dp))
                }
            }
            Card(modifier=Modifier.offset(midw.dp+90.dp,175.dp).height(50.dp).width(100.dp), shape = RoundedCornerShape(20.dp), elevation = CardDefaults.cardElevation(5.dp)){
                Box(Modifier.fillMaxSize().background(Color.White),contentAlignment = Alignment.Center){
                    Text("Follow", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold,color=Color.Black))
                }
            }
        }
        Spacer(Modifier.height(10.dp))
        Text(modifier = Modifier.offset(45.dp), text = "George", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 25.sp))
        Text(modifier = Modifier.align(Alignment.CenterHorizontally).padding(start = 50.dp,end=50.dp), text = "I'm delighted to introduce myself as a professional model", style = TextStyle( fontSize = 15.sp, color = Color.Gray, textAlign = TextAlign.Center))
        Row(modifier = Modifier.height(80.dp).fillMaxWidth()) {
            Column (Modifier.weight(1f).height(100.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "50.5K")
                Text(text = "Following", style = TextStyle(color=Color.Gray))
            }
            Column (Modifier.weight(1f).height(100.dp),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "30.5K")
                Text(text = "Followers", style = TextStyle(color=Color.Gray))
            }
            Column (Modifier.weight(1f).height(100.dp),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "255")
                Text(text = "Posts", style = TextStyle(color=Color.Gray))
            }
        }
        Row(modifier=Modifier.width(LocalConfiguration.current.screenWidthDp.dp-20.dp).height(50.dp).clip(shape = RoundedCornerShape(20.dp)).background(color = Color.Gray.copy(alpha = 0.2f)).padding(5.dp).align(Alignment.CenterHorizontally)){
            Box(
                Modifier.weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(20.dp))
                    .background(color = if (select == 0) Color.White else Color.Transparent)
                    .clickable { select = 0 }
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = rememberAsyncImagePainter(R.drawable.collage),
                        contentDescription = "",
                        modifier = Modifier.weight(1f)
                    )
                    if (select == 0) {
                        Spacer(Modifier.height(4.dp))
                        Text("Post", modifier = Modifier.weight(1f))
                    }
                }
            }

            Box(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(20.dp))
                    .background(color = if (select == 1) Color.White else Color.Transparent)
                    .clickable { select = 1 }
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = rememberAsyncImagePainter(R.drawable.bookmark),
                        contentDescription = "",
                        modifier = Modifier.weight(1f)
                    )
                    if (select == 1) {
                        Spacer(Modifier.height(4.dp))
                        Text("Post",modifier = Modifier.weight(1f))
                    }
                }
            }
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(20.dp))
                    .background(color = if (select == 2) Color.White else Color.Transparent)
                    .clickable { select = 2}
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = rememberAsyncImagePainter(R.drawable.custexp),
                        contentDescription = "",
                        modifier = Modifier.weight(1f)
                    )
                    if (select == 2) {
                        Spacer(Modifier.height(4.dp))
                        Text("Post",modifier = Modifier.weight(1f))
                    }
                }
            }

        }


    }
}
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProfileForUsPreview() {
    val navController = rememberNavController()
    profileforus(navController)
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProfileForOtherPreview() {
    val navController = rememberNavController()
    profileforother(navController)
}

