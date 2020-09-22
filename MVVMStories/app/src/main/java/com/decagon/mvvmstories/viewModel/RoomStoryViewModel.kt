package com.decagon.mvvmstories.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.decagon.mvvmstories.data.Post
import com.decagon.mvvmstories.data.StoryDatabase
import com.decagon.mvvmstories.repository.RoomStoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomStoryViewModel(application: Application): AndroidViewModel(application) {
    private val readAllPost: LiveData<List<Post>>
    private val repository: RoomStoryRepository

    init {
        val storyDao = StoryDatabase.getDatabase(application).storyDao()
        repository = RoomStoryRepository(storyDao)
        readAllPost = repository.readAllPost
    }

    /**
     * setup add user and rn in the background
     */
    fun addStory(post: Post){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addStory(post)
        }
    }

}