package com.decagon.navcontrollerblog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.navcontrollerblog.data.CommentDao
import com.decagon.navcontrollerblog.data.Comments
import com.decagon.navcontrollerblog.data.StoryDao
import com.decagon.navcontrollerblog.repository.BlogRepository
import com.decagon.navcontrollerblog.repository.RoomRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomCommentViewModel(var repositoryImpl: BlogRepository): ViewModel() {

    /**
     * setup add comment
     */
    fun addComment(comment: Comments) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.addComment(comment)
        }
    }

    /**
     * setup display comments from model
     */
    fun getCommentFromRoom(id: Int): LiveData<List<Comments>> {
        return repositoryImpl.getCommentFromRoom(id)
    }

    /**
     * Fetch comment for each post from endpoint n save to room
     */
    fun fetchCommentsFromEndpoint(id: Int) {
        repositoryImpl.fetchCommentsFromEndpoint(id)
    }
}