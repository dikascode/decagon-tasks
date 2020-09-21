package com.decagon.mvvmstories.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.decagon.mvvmstories.model.Comment
import com.decagon.mvvmstories.model.Comments
import com.decagon.mvvmstories.model.Story
import com.decagon.mvvmstories.repository.PostRepository

class PostViewModel(application: Application): AndroidViewModel(application) {

    private val repository = PostRepository(application)
    val comment: LiveData<Comment>

    init {
        this.comment = repository.comment
    }

    fun addComments(id: Int, newComments: Comment) {
        repository.addComment(id, newComments)
    }

    fun addStory(newStory: Story) {
        repository.addStory(newStory)
    }
}