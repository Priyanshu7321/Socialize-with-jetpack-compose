package com.example.socialize.screens

import android.widget.ScrollView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.socialize.R

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController

@Composable
fun videoView(navController: NavController) {
    val interests = listOf(
        "Gaming", "Music", "Travel", "Foodie", "Movies", "Sports", "Reading", "Art",
        "Photography", "Fitness", "Tech", "Coding", "Nature", "Dancing", "Cooking",
        "Anime", "Pets", "Meditation", "Fashion", "Blogging", "Volunteering", "DIY",
        "Astronomy", "Podcasts"
    )

    val selectedInterests = remember { mutableStateListOf("Sports", "Art") }
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(10.dp)
                .padding(bottom = 80.dp), // Extra padding for the button
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .padding(5.dp),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .weight(3f)
                            .fillMaxHeight()
                    ) {
                        Text(
                            text = "Connect Instantly!",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFF6F61)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Find new friends and exciting conversations worldwide.",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                    Image(
                        painter = painterResource(R.drawable.boy),
                        contentDescription = "Boy illustration",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Select Your Interests",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Choose topics that excite you to find better matches for your calls.",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 16.dp),
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                for (row in interests.chunked(2)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(vertical = 6.dp)
                    ) {
                        for (interest in row) {
                            val isSelected = interest in selectedInterests
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .border(
                                        width = 1.dp,
                                        color = if (isSelected) Color(0xFF6200EE) else Color.Gray,
                                        shape = RoundedCornerShape(50)
                                    )
                                    .background(
                                        color = if (isSelected) Color(0x336200EE) else Color.Transparent,
                                        shape = RoundedCornerShape(50)
                                    )
                                    .clickable {
                                        if (isSelected) selectedInterests.remove(interest)
                                        else selectedInterests.add(interest)
                                    }
                                    .padding(horizontal = 24.dp, vertical = 12.dp)
                            ) {
                                Text(
                                    text = interest,
                                    fontSize = 14.sp,
                                    color = if (isSelected) Color(0xFF6200EE) else Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }

        // Button stays fixed at bottom
        Button(
            onClick = { /* Start video call logic */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFFF6F61), Color(0xFF6200EE))
                    ),
                    shape = RoundedCornerShape(25.dp)
                )
        ) {
            Text(
                text = "Start Video Call",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun showVideoView(){
    var navController= rememberNavController()
    videoView(navController)
}
