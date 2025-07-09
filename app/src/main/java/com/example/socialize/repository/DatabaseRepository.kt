package com.example.socialize.repository

import android.util.Log
import com.example.socialize.dao.UserDao
import com.example.socialize.dao.MessageDao
import com.example.socialize.dao.GroupChatDao
import com.example.socialize.dao.GroupMemberDao
import com.example.socialize.entity.User
import com.example.socialize.pojo.Message
import com.example.socialize.pojo.GroupChat
import com.example.socialize.pojo.GroupMember
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseRepository @Inject constructor(
    private val userDao: UserDao,
    private val messageDao: MessageDao,
    private val groupChatDao: GroupChatDao,
    private val groupMemberDao: GroupMemberDao
) {

    // User operations
    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
        Log.d("Room", "Inserted User with ID: ${user.id}")
    }

    suspend fun getUserById(id: String): User? {
        return userDao.getUserById(id)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
        Log.d("Room", "Updated User with ID: ${user.id}")
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
        Log.d("Room", "Deleted User with ID: ${user.id}")
    }

    fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers()
    }

    suspend fun getAllUsersList(): List<User> {
        return userDao.getAllUsersList()
    }

    suspend fun getUserByCredentials(username: String, password: String): User? {
        return userDao.getUserByCredentials(username, password)
    }

    // Message operations
    suspend fun insertMessage(message: Message) {
        messageDao.insertMessage(message)
    }

    suspend fun getMessageById(id: Long): Message? {
        return messageDao.getMessageById(id)
    }

    suspend fun updateMessage(message: Message) {
        messageDao.updateMessage(message)
    }

    suspend fun deleteMessage(message: Message) {
        messageDao.deleteMessage(message)
    }

    fun getMessagesBetweenUsers(userId1: String, userId2: String): Flow<List<Message>> {
        return messageDao.getMessagesBetweenUsers(userId1, userId2)
    }

    fun getMessagesForGroup(groupId: Long): Flow<List<Message>> {
        return messageDao.getMessagesForGroup(groupId)
    }

    fun getAllMessages(): Flow<List<Message>> {
        return messageDao.getAllMessages()
    }

    // Group operations
    suspend fun insertGroupChat(groupChat: GroupChat): Long {
        return groupChatDao.insertGroupChat(groupChat)
    }

    suspend fun getGroupChatById(id: Long): GroupChat? {
        return groupChatDao.getGroupChatById(id)
    }

    suspend fun updateGroupChat(groupChat: GroupChat) {
        groupChatDao.updateGroupChat(groupChat)
    }

    suspend fun deleteGroupChat(groupChat: GroupChat) {
        groupChatDao.deleteGroupChat(groupChat)
    }

    fun getAllGroupChats(): Flow<List<GroupChat>> {
        return groupChatDao.getAllGroupChats()
    }

    fun getGroupChatsByCreator(creatorId: String): Flow<List<GroupChat>> {
        return groupChatDao.getGroupChatsByCreator(creatorId)
    }

    // Group member operations
    suspend fun addGroupMember(groupId: Long, userId: String) {
        groupMemberDao.insertGroupMember(GroupMember(groupId, userId))
    }

    suspend fun removeGroupMember(groupId: Long, userId: String) {
        groupMemberDao.deleteGroupMember(GroupMember(groupId, userId))
    }

    suspend fun getGroupMembers(groupId: Long): List<String> {
        return groupMemberDao.getGroupMembers(groupId)
    }

    suspend fun getUserGroups(userId: String): List<Long> {
        return groupMemberDao.getUserGroups(userId)
    }
}
