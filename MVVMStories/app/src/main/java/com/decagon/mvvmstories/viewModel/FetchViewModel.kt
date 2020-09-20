package com.decagon.mvvmstories.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.decagon.mvvmstories.model.Comments
import com.decagon.mvvmstories.model.Story
import com.decagon.mvvmstories.repository.FetchActivityRepository

class FetchViewModel(application: Application): AndroidViewModel(application) {

    private val repository = FetchActivityRepository(application)
    val showProgress: LiveData<Boolean>
    val storyList: LiveData<List<Story>>
    val singleStory: LiveData<Story>

    val commentsList: LiveData<List<Comments>>

    init {
        this.storyList = repository.storyList
        this.showProgress = repository.showProgress
        this.singleStory = repository.singleStory
        this.commentsList = repository.commentsList
    }

    fun changeState() {
        repository.changeState()
    }

    fun displayStories() {
        repository.displayStories()
    }

    fun getStory(id: Int) {
        repository.getStory(id)
    }

    fun getComments(id: Int) {
        repository.getComments(id)
    }


}