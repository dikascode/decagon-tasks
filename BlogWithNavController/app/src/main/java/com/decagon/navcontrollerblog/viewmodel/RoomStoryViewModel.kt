package com.decagon.navcontrollerblog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.navcontrollerblog.data.Stories
import com.decagon.navcontrollerblog.data.StoryDao
import com.decagon.navcontrollerblog.repository.BlogRepository
import com.decagon.navcontrollerblog.repository.RoomRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomStoryViewModel(var repositoryImpl: BlogRepository) : ViewModel() {

    val readAllStories: LiveData<List<Stories>> = repositoryImpl.readAllStories()


    /**
     * setup add story and run in the background
     */
    fun addStory(post: Stories) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.addStory(post)
        }
    }

    /**
     * Source stories from model
     */
    fun readStory(id: Int): LiveData<Stories> {
        return repositoryImpl.readStory(id)
    }

    fun displayStories() {
        repositoryImpl.displayStories()
    }

    /**
     * setup filter post
     */
    fun getSearchPosts(search: String): LiveData<List<Stories>> {
        return repositoryImpl.getSearchPost(search)
    }

}