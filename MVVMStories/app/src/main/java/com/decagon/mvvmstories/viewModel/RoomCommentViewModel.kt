package com.decagon.mvvmstories.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.decagon.mvvmstories.data.Comments
import com.decagon.mvvmstories.data.CommentsDatabase
import com.decagon.mvvmstories.repository.RoomCommentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomCommentViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RoomCommentRepository

    init {
        val commentDao = CommentsDatabase.getDatabase(application).commentDao()
        repository = RoomCommentRepository(commentDao)

    }

    /**
     * setup add user and rn in the background
     */
    fun addComment(comment: Comments) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addComment(comment)
        }
    }

    fun getCommentFromRoom(id: Int): LiveData<List<Comments>> {
        return repository.getCommentFromRoom(id)
    }

    fun displayComments(id: Int) {
        repository.fetchCommentsFromEndpoint(id)
    }

}