package com.example.socialize.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.socialize.R

@Composable
fun post(navController: NavController){
    var text by remember{ mutableStateOf("") }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)){
            Image(
                painter = painterResource(R.drawable.back),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.size(10.dp))
                Text("Express your views", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))


        }
        Card(
            modifier = Modifier
                .width(300.dp)
                .height(200.dp)
                .padding(vertical = 12.dp),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()){
                Image(
                    modifier = Modifier.align(Alignment.Center).size(100.dp),
                    painter = painterResource(R.drawable.addimage),
                    contentDescription = "",
                )
            }
        }
        TextField(
            value = text,
            onValueChange = {it-> text=it},
            label = { Text("Write your views...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.White),
        )
    }
}