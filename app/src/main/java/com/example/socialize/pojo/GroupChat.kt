package com.example.socialize.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "group_chats")
data class GroupChat(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val groupName: String?,
    val creator: String?
)

