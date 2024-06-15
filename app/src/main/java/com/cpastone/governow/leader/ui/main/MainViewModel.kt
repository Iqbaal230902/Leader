package com.cpastone.governow.leader.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.cpastone.governow.leader.data.model.Post
import com.cpastone.governow.leader.data.model.User
import com.cpastone.governow.leader.data.repository.PostRepository
import com.cpastone.governow.leader.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (private val storyRepository: PostRepository, private val userRepository: UserRepository) : ViewModel() {
    var scrollPosition = 0
    fun story(token: String): LiveData<PagingData<Post>> {
        return storyRepository.getStory(token).cachedIn(viewModelScope)
    }

    fun getSession(): Flow<User> {
        return userRepository.getSession()
    }


    val logout = {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(Injection.provideStoryRepository(context), Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}