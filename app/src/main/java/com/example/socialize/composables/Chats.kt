package com.example.socialize.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.socialize.R

@Composable
fun chats(navController: NavController) {
    Box(
        modifier = Modifier
            .background(color = Color.Gray)
            .fillMaxSize()
    )  {

        Column(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxSize()
                .padding(top = 50.dp, bottom = 10.dp)
        ) {
            Card(
                modifier = Modifier
                    .height(70.dp)
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.White,
                                Color.White.copy(alpha = 0.5f)
                            )
                        )
                    ),
                shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp, topStart = 0.dp, topEnd = 0.dp),
                colors = CardDefaults.cardColors(Color.Transparent),

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowLeft,
                            contentDescription = "",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    Image(
                        painter = painterResource(R.drawable.boy),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(top = 5.dp, bottom = 5.dp)
                            .size(55.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Alice ",
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        )
                        Text(
                            text = "Alice is typing....",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color.Gray
                            )
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    Image(painter = painterResource(R.drawable.video), contentDescription = "", modifier = Modifier.size(30.dp))
                    Spacer(Modifier.width(10.dp))
                    Image(painter = painterResource(R.drawable.telephone), contentDescription = "", modifier = Modifier.size(25.dp))

                }

            }
            Column (modifier = Modifier.weight(1f)){
                mainChatSection()
            }
            Box(){
                messageSendSection()
            }
        }

    }

}

@Composable
fun mainChatSection(){
    LazyColumn (modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
        .padding(horizontal = 5.dp)){
        items(20){
            it->
            if(it%2==0){
                Row (modifier = Modifier
                    .padding(start = 50.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.Top){
                    Column (horizontalAlignment = Alignment.End){
                        Text(
                            text = "You",
                            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold,)
                        )
                        Card(
                            modifier = Modifier.padding(5.dp),
                            shape = RoundedCornerShape(
                                topEnd = 0.dp,
                                topStart = 20.dp,
                                bottomEnd = 20.dp,
                                bottomStart = 20.dp
                            ),
                            colors = CardDefaults.cardColors(containerColor = Color.Green)
                        ) {
                            Text(
                                text = "Hi Alice , How are you",
                                style = TextStyle(fontSize = 15.sp),
                                modifier = Modifier.padding(15.dp)
                            )
                        }
                    }
                    Spacer(Modifier.width(4.dp))
                    Image(painter = painterResource(R.drawable.boy), contentDescription = "", modifier = Modifier.size(30.dp))
                }
            }else{
                Row (modifier = Modifier
                    .padding(end = 50.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.Top){
                    Image(painter = painterResource(R.drawable.boy), contentDescription = "", modifier = Modifier.size(30.dp))
                    Spacer(Modifier.width(4.dp))
                    Column (horizontalAlignment = Alignment.Start) {
                        Text(
                            text = "Alice",
                            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold,)
                        )
                        Card(
                            modifier = Modifier.padding(5.dp),
                            shape = RoundedCornerShape(
                                topEnd = 20.dp,
                                topStart = 0.dp,
                                bottomEnd = 20.dp,
                                bottomStart = 20.dp
                            ),
                            colors=CardDefaults.cardColors(containerColor = Color.Yellow)
                        ) {
                            Text(
                                text = "I am fine",
                                style = TextStyle(fontSize = 15.sp),
                                modifier = Modifier.padding(15.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun messageSendSection(){
    var textval by remember { mutableStateOf("") }
    Card (modifier = Modifier
        .fillMaxWidth()
        .heightIn(min = 65.dp,max=100.dp)
        .padding(start = 10.dp, end = 10.dp),
        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp, bottomStart = 40.dp, bottomEnd = 40.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ){
        Row (modifier = Modifier.fillMaxSize().padding(start = 10.dp, end = 10.dp), verticalAlignment = Alignment.CenterVertically){
            Image(painter = painterResource(R.drawable.audio), contentDescription = "", modifier = Modifier.size(25.dp).background(color=Color.White))
            TextField(
                value =textval ,
                onValueChange = {it -> textval= it},
                textStyle = TextStyle(fontSize = 15.sp),
                modifier = Modifier.weight(1f).background(color = Color.Transparent),
                placeholder = { Text(text = "Send text message") },
                colors = TextFieldDefaults.colors(
                    unfocusedLabelColor = Color.Gray,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedIndicatorColor = Color.White, // Removes underline
                    focusedIndicatorColor = Color.White // Removes underline
                ) )
            Image(painter = painterResource(R.drawable.camera), contentDescription = "", modifier = Modifier.size(30.dp).background(color=Color.White))
            Spacer(Modifier.width(8.dp))
            Image(painter = painterResource(R.drawable.sendmessage), contentDescription = "", modifier = Modifier.size(25.dp).background(color=Color.White))

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun chatPreview(){
    //chats()
}