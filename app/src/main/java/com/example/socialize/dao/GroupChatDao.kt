package com.example.socialize.dao

import androidx.room.*
import com.example.socialize.pojo.GroupChat
import com.example.socialize.pojo.GroupMember
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupChatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroupChat(groupChat: GroupChat): Long

    @Query("SELECT * FROM group_chats WHERE id = :id")
    suspend fun getGroupChatById(id: Long): GroupChat?

    @Update
    suspend fun updateGroupChat(groupChat: GroupChat)

    @Delete
    suspend fun deleteGroupChat(groupChat: GroupChat)

    @Query("SELECT * FROM group_chats")
    fun getAllGroupChats(): Flow<List<GroupChat>>

    @Query("SELECT * FROM group_chats WHERE creator = :creatorId")
    fun getGroupChatsByCreator(creatorId: String): Flow<List<GroupChat>>
}

@Dao
interface GroupMemberDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroupMember(groupMember: GroupMember)

    @Delete
    suspend fun deleteGroupMember(groupMember: GroupMember)

    @Query("SELECT userId FROM group_members WHERE groupId = :groupId")
    suspend fun getGroupMembers(groupId: Long): List<String>

    @Query("SELECT groupId FROM group_members WHERE userId = :userId")
    suspend fun getUserGroups(userId: String): List<Long>

    @Query("DELETE FROM group_members WHERE groupId = :groupId")
    suspend fun deleteAllGroupMembers(groupId: Long)
} 