package com.example.socialize.pojo

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "group_members",
    primaryKeys = ["groupId", "userId"],
    foreignKeys = [
        ForeignKey(
            entity = GroupChat::class,
            parentColumns = ["id"],
            childColumns = ["groupId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class GroupMember(
    val groupId: Long,
    val userId: String
) 