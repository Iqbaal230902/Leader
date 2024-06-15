package com.cpastone.governow.leader

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cpastone.governow.leader.data.repository.PostRepository
import com.cpastone.governow.leader.data.repository.UserRepository
import com.cpastone.governow.leader.di.Injection
import com.cpastone.governow.leader.ui.login.LoginViewModel
import com.cpastone.governow.leader.ui.main.MainViewModel
import com.cpastone.governow.leader.ui.post.AddPostViewModel
import com.cpastone.governow.leader.ui.regis.RegisViewModel


class ViewModelFactory (private val repository: UserRepository, private val storyRepository: PostRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress ("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java)-> {
                MainViewModel(storyRepository, repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RegisViewModel::class.java) -> {
                RegisViewModel() as T
            }

            modelClass.isAssignableFrom(AddPostViewModel::class.java) -> {
                AddPostViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class : "+modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context) : ViewModelFactory {
            if (INSTANCE == null){
                synchronized(ViewModelFactory::class.java){
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context), Injection.provideStoryRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}