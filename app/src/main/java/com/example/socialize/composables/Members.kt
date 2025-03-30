package com.example.socialize.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.socialize.R
import kotlin.random.Random

@Composable
fun members(navController: NavController){
    var textval by remember { mutableStateOf("") }
    Column (modifier = Modifier.fillMaxSize().padding(WindowInsets.systemBars.asPaddingValues())){
        Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 15.dp, end = 15.dp), verticalAlignment = Alignment.CenterVertically){

            Text(text = "Chat", style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.Black))
            Spacer(modifier = Modifier.weight(1f))
            Card (modifier = Modifier){
                Row (modifier = Modifier.padding(5.dp)){
                    Image(
                        painter = painterResource(R.drawable.camera),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp),
                        colorFilter = ColorFilter.tint(Color.Red)
                    )
                }
            }
            Spacer(Modifier.width(8.dp))
            Image(
                        painter = painterResource(R.drawable.boy),
                        contentDescription = "",
                        modifier = Modifier.size(40.dp)
            )

        }
        Spacer(Modifier.height(18.dp))
        Card(modifier = Modifier.padding(start = 10.dp, end = 10.dp), shape = RoundedCornerShape(30.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.elevatedCardElevation(7.dp)) {
            Row (modifier = Modifier.fillMaxWidth().height(60.dp).padding(start = 10.dp, end = 10.dp), verticalAlignment = Alignment.CenterVertically){
                Image(imageVector = Icons.Filled.Search, contentDescription = "", modifier = Modifier.size(35.dp))
                TextField(
                    value =textval,
                    onValueChange = {it->textval=it},
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(fontSize = 19.sp),
                    placeholder = { Text(text = "Search") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White,
                    ),
                )
            }
        }
        Spacer(Modifier.height(18.dp))
        Text(text = "All messages", style = TextStyle(fontSize = 19.sp, color = Color.Gray), modifier = Modifier.padding(start = 10.dp))
        Spacer(Modifier.height(8.dp))
        Column(modifier=Modifier.weight(1f)){
            chatMembers()
        }
    }
}
data class member(var name:String,var msg:String,var time:String)

fun generateRandomMembers(count: Int): List<member> {
    val names = listOf("Alice", "Bob", "Charlie", "David", "Eve")
    val messages = listOf("Hello!", "Good morning", "How are you?", "See you soon", "Take care!")

    return List(count) {
        member(
            name = names.random(),
            msg = messages.random(),
            time = "${Random.nextInt(1, 12)}:${Random.nextInt(10, 59)} ${if (Random.nextBoolean()) "AM" else "PM"}"
        )
    }
}
var memberList= generateRandomMembers(17)
@Composable
fun chatMembers(){
    LazyColumn(modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp)) {
        items(memberList){
            member->
            Row (modifier = Modifier.height(60.dp), verticalAlignment = Alignment.CenterVertically){
                Image(painter = painterResource(R.drawable.boy), contentDescription = "", modifier = Modifier.size(55.dp))
                Spacer(Modifier.width(9.dp))
                Column (modifier = Modifier.padding(top = 5.dp)){
                    Text(text = member.name, style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold))
                    Text(text = member.msg, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Gray))
                }
                Spacer(Modifier.weight(1f))
                Text(text = member.time, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Gray))

            }
            Spacer(Modifier.height(10.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi =true)
@Composable
fun memberPreview(){
    var navController= rememberNavController()
    members(navController)
}