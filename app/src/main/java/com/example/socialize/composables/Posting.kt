package com.example.socialize.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
    Column (modifier = Modifier.fillMaxSize()){
        Box{
            Row(modifier = Modifier.fillMaxWidth().height(70.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                Text("Express your views", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
            }
            Image(painter = painterResource(R.drawable.back), contentDescription = "",Modifier.size(20.dp).align(AbsoluteAlignment.CenterLeft).offset(x=20.dp))

        }
        Card(modifier = Modifier.width(300.dp).height(200.dp).align(Alignment.CenterHorizontally), elevation = CardDefaults.cardElevation(5.dp)) {
            Box(modifier = Modifier.fillMaxSize()){
                Image(
                    modifier = Modifier.align(Alignment.Center).size(100.dp),
                    painter = painterResource(R.drawable.addimage),
                    contentDescription = "",
                )
            }
        }
        Column {
            TextField(
                value = text,
                onValueChange = {it-> text=it},
                label = { Text("Write yout views") },
                modifier = Modifier.fillMaxSize(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.White),
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun showmain(){
    var navController:NavController= rememberNavController()
    post(navController)
}