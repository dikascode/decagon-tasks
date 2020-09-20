package com.decagon.mvvmstories.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.decagon.mvvmstories.model.Story
import com.decagon.mvvmstories.repository.SearchActivityRepository

class SearchActivityViewModel(application: Application): AndroidViewModel(application) {

    private val repository = SearchActivityRepository(application)
    val showProgress: LiveData<Boolean>
    val storyList: LiveData<List<Story>>
    val singleStory: LiveData<Story>

    init {
        this.storyList = repository.storyList
        this.showProgress = repository.showProgress
        this.singleStory = repository.singleStory
    }

    fun changeState() {
        repository.changeState()
    }

    fun displayStories() {
        repository.displayStories()
    }

    fun searchStory(id: Int) {
        repository.searchStory(id)
    }


}