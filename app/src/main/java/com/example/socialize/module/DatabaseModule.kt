package com.example.socialize.module

import android.content.Context
import com.example.socialize.database.AppDatabase
import com.example.socialize.dao.UserDao
import com.example.socialize.dao.MessageDao
import com.example.socialize.dao.GroupChatDao
import com.example.socialize.dao.GroupMemberDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideMessageDao(database: AppDatabase): MessageDao {
        return database.messageDao()
    }

    @Provides
    @Singleton
    fun provideGroupChatDao(database: AppDatabase): GroupChatDao {
        return database.groupChatDao()
    }

    @Provides
    @Singleton
    fun provideGroupMemberDao(database: AppDatabase): GroupMemberDao {
        return database.groupMemberDao()
    }
}

