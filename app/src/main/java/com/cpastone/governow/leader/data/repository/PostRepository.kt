package com.cpastone.governow.leader.data.repository


import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.cpastone.governow.leader.api.ApiService
import com.cpastone.governow.leader.data.model.Post
import com.cpastone.governow.leader.data.paging.Mediator
import com.cpastone.governow.leader.database.StoryDatabase
import javax.inject.Singleton

@Singleton
@OptIn(ExperimentalPagingApi::class)

class PostRepository(private val storyDatabase: StoryDatabase, private val apiService: ApiService) {
    fun getStory(token: String): LiveData<PagingData<Post>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            remoteMediator = Mediator(storyDatabase, apiService, token),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }


    companion object {
        @Volatile
        private var instance: PostRepository? = null
        fun getInstance(
            storyDatabase: StoryDatabase,
            apiService: ApiService
        ): PostRepository =
            instance ?: synchronized(this) {
                instance ?: PostRepository(storyDatabase, apiService)
            }.also { instance = it }
    }
}