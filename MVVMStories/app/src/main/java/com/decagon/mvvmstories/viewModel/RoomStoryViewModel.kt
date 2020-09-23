package com.decagon.mvvmstories.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.decagon.mvvmstories.data.Stories
import com.decagon.mvvmstories.data.StoryDatabase
import com.decagon.mvvmstories.repository.RoomStoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomStoryViewModel(application: Application) : AndroidViewModel(application) {
    val readAllStories: LiveData<List<Stories>>

    private val repository: RoomStoryRepository

    init {
        val storyDao = StoryDatabase.getDatabase(application).storyDao()
        repository = RoomStoryRepository(storyDao)
        readAllStories = repository.readAllStories

    }

    /**
     * setup add story and run in the background
     */
    fun addStory(post: Stories) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addStory(post)
        }
    }


    fun readStory(id: Int): LiveData<Stories> {
        return repository.readStory(id)
    }

    fun displayStories() {
        repository.displayStories()
    }

}