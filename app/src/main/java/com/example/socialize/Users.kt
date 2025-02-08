package com.example.socialize

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun Users(navController:NavController){
    var items = listOf("hello ","hi","skfjskj","jskjkf","jskjien")
    var text by remember { mutableStateOf("") }
    Column(Modifier.systemBarsPadding()){
        Row(Modifier.height(40.dp).fillMaxWidth().padding(start=20.dp, end = 20.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
            Image(painter = painterResource(R.drawable.back), contentDescription = "",Modifier.size(25.dp))
            Card (elevation = CardDefaults.elevatedCardElevation(3.dp)){
                TextField(
                    value = text,
                    onValueChange = {it->text=it},
                    colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.White),
                    label = { Text("Search User, Groups, Influencer") }
                )
            }
            Image(painter = painterResource(R.drawable.menulist), contentDescription = "",Modifier.size(25.dp))
        }
        StaggeredGridCustom(items)

    }
}
@Composable
fun StaggeredGridCustom(items: List<String>) {
    LazyVerticalStaggeredGrid (columns = StaggeredGridCells.Adaptive(minSize = 180.dp)) {
        items(60) {
            index->
            val random: Double = 100 + Math.random() * (400 - 100)
            var itemim= listOf(R.drawable.girla,R.drawable.girlb,R.drawable.girld)
            Box() {
                Card(modifier = Modifier.height(random.dp).padding(10.dp).defaultMinSize(200.dp),) {
                    Image(
                        painter = painterResource(id = R.drawable.girla),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds
                    )
                }
                Row(modifier = Modifier.height(40.dp).fillMaxWidth()
                    .offset(x = 18.dp, y = random.dp - 60.dp)) {
                    ElevatedButton(
                        modifier = Modifier.height(40.dp).width(100.dp),
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent.copy(alpha = 0.4f)
                        )
                    ) {
                        Text("Follow")
                    }
                    Text("50K\nFollower", style = TextStyle(fontSize = 15.sp, color = Color.White, textAlign = TextAlign.Center))
                }
            }

        }
    }
}

@Composable
fun StaggeredItem(imageUrl: String) {
    Text(
        text = "hello",
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(8.dp)).background(color = Color.Gray)
    )
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun showUser(){
    var navController:NavController= rememberNavController()
    Users(navController)
}
