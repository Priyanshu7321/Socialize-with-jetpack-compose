package com.example.socialize.module

import android.content.Context
import com.couchbase.lite.*
import com.couchbase.lite.Collection
import com.example.socialize.Constants.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): Database {
        CouchbaseLite.init(context)
        return Database("SocializeMobDB", DatabaseConfiguration())
    }

    @Provides
    @Singleton
    fun provideUserCollection(database: Database): Collection? {

        val scope = database.getScope(AppConstants.UserScopeName)
        if (scope != null) {
            return scope.getCollection(AppConstants.UserCollectionName)
        }else{
            return database.createCollection(AppConstants.UserCollectionName,AppConstants.UserScopeName)
        }
    }

    @Provides
    @Singleton
    fun providePostCollection(database: Database): Collection? {
        val scope = database.getScope("socializeScope")
        if (scope != null) {
            return scope.getCollection("postCollection")
        }else{
            return database.createCollection("postCollection","socializeScope")
        }
    }
}
