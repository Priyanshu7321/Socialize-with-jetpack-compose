package com.example.socialize.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String,
    val username: String,
    val name: String,
    val email: String,
    val password: String,
    val age: String,
    val mobileNum: String
)

data class UserPassword(
    val email: String,
    val password: String
)