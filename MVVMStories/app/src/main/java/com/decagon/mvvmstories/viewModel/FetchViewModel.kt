package com.decagon.mvvmstories.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.decagon.mvvmstories.model.Comments
import com.decagon.mvvmstories.model.Stories
import com.decagon.mvvmstories.repository.FetchRepository

class FetchViewModel(application: Application): AndroidViewModel(application) {

    private val repository = FetchRepository(application)
    val showProgress: LiveData<Boolean>
    val storyList: LiveData<List<Stories>>
    val singleStory: LiveData<Stories>

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
//        repository.getComments(id)
    }


}