package com.example.socialize.dao

import androidx.room.*
import com.example.socialize.pojo.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: Message)

    @Query("SELECT * FROM messages WHERE id = :id")
    suspend fun getMessageById(id: Long): Message?

    @Update
    suspend fun updateMessage(message: Message)

    @Delete
    suspend fun deleteMessage(message: Message)

    @Query("SELECT * FROM messages WHERE (senderId = :userId1 AND receiverId = :userId2) OR (senderId = :userId2 AND receiverId = :userId1) ORDER BY timestamp DESC")
    fun getMessagesBetweenUsers(userId1: String, userId2: String): Flow<List<Message>>

    @Query("SELECT * FROM messages WHERE groupId = :groupId ORDER BY timestamp DESC")
    fun getMessagesForGroup(groupId: Long): Flow<List<Message>>

    @Query("SELECT * FROM messages")
    fun getAllMessages(): Flow<List<Message>>
} 