package com.example.socialize.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun videoView(navController: NavController){
    Box(modifier= Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Button(modifier = Modifier.height(50.dp).width(100.dp).background(color= Color.Blue), onClick = {navController.navigate("home")}) { }
    }
}