package com.example.socialize.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val senderId: String?,
    val receiverId: String?,
    val groupId: String?,
    val content: String?,
    val timestamp: LocalDateTime?
)

