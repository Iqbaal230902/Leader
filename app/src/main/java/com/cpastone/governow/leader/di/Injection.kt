package com.cpastone.governow.leader.di

import android.content.Context
import com.cpastone.governow.leader.api.ApiConfig
import com.cpastone.governow.leader.data.pref.UserPreference
import com.cpastone.governow.leader.data.pref.dataStore
import com.cpastone.governow.leader.data.repository.PostRepository
import com.cpastone.governow.leader.data.repository.UserRepository
import com.cpastone.governow.leader.database.StoryDatabase
import com.cpastone.governow.leader.database.room.RemoteKeysDao
import com.cpastone.governow.leader.database.room.StoryDao
import dagger.Provides

object Injection {
    fun provideRepository(context: Context) : UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }

    fun provideStoryRepository(context: Context) : PostRepository {
        val apiInstance = ApiConfig.apiInstance
        val storyDatabase = StoryDatabase.getDatabase(context)
        return PostRepository.getInstance(storyDatabase, apiInstance)
    }

    @Provides
    fun provideStoryDao(storyDatabase: StoryDatabase): StoryDao = storyDatabase.storyDao()

    @Provides
    fun provideRemoteKeysDao(storyDatabase: StoryDatabase): RemoteKeysDao = storyDatabase.remoteKeyDao()
}