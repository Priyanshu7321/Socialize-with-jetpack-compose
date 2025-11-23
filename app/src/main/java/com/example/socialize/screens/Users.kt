package com.example.socialize.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.socialize.R
import kotlin.random.Random
import androidx.compose.foundation.layout.systemBarsPadding

data class UserProfile(val name: String, val avatar: Int, val followers: Int)

@Composable
fun Users(navController: NavController) {
    val avatars = listOf(R.drawable.girla, R.drawable.girlb, R.drawable.girld, R.drawable.boy, R.drawable.boy1)
    val names = listOf("Alice", "Bob", "Cathy", "David", "Eva", "Frank", "Grace", "Helen", "Ivan", "Julia")
    val users = remember {
        List(20) {
            UserProfile(
                name = names.random(),
                avatar = avatars.random(),
                followers = Random.nextInt(1_000, 100_000)
            )
        }
    }
    var text by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.back),
                contentDescription = "Back",
                Modifier
                    .size(25.dp)
                    .clickable { navController.popBackStack() }
            )
            Card(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 48.dp),
                elevation = CardDefaults.elevatedCardElevation(3.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 14.sp),
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            "Search users, groups, influencers",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 12.sp
                        )
                    }
                )
            }
            Image(
                painter = painterResource(R.drawable.menulist),
                contentDescription = "",
                Modifier.size(25.dp)
            )
        }
        Spacer(Modifier.height(8.dp))
        LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Adaptive(minSize = 180.dp), modifier = Modifier.fillMaxSize()) {
            items(users.size) { index ->
                val user = users[index]
                UserCard(user, navController)
            }
        }
    }
}

@Composable
fun UserCard(user: UserProfile, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .defaultMinSize(200.dp)
            .clickable { navController.navigate("profile") },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(8.dp))
            Image(
                painter = painterResource(id = user.avatar),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp).clip(CircleShape)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = user.name,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "${user.followers / 1000}K Followers",
                style = TextStyle(fontSize = 14.sp, color = Color.Gray),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Button(
                onClick = { /* Follow logic */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Text("Follow", color = Color.White)
            }
        }
    }
}
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun showVideoView1(){
    var navController= rememberNavController()
    Users(navController)
}
