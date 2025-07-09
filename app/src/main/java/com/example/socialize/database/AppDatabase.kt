package com.example.socialize.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.example.socialize.dao.GroupChatDao
import com.example.socialize.dao.GroupMemberDao
import com.example.socialize.dao.MessageDao
import com.example.socialize.dao.UserDao
import com.example.socialize.entity.User
import com.example.socialize.pojo.GroupChat
import com.example.socialize.pojo.GroupMember
import com.example.socialize.pojo.Message
import com.example.socialize.util.Converters

@Database(
    entities = [
        User::class,
        Message::class,
        GroupChat::class,
        GroupMember::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao
    abstract fun groupChatDao(): GroupChatDao
    abstract fun groupMemberDao(): GroupMemberDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "socialize_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
} 