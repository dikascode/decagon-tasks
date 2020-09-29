package com.decagon.navcontrollerblog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.navcontrollerblog.data.CommentDao
import com.decagon.navcontrollerblog.data.Comments
import com.decagon.navcontrollerblog.data.StoryDao
import com.decagon.navcontrollerblog.repository.RoomRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomCommentViewModel(var repositoryImpl: RoomRepositoryImpl): ViewModel() {

    /**
     * setup add user and run in the background
     */
    fun addComment(comment: Comments) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.addComment(comment)
        }
    }

    /**
     * setup add comment
     */
    fun getCommentFromRoom(id: Int): LiveData<List<Comments>> {
        return repositoryImpl.getCommentFromRoom(id)
    }

    /**
     * setup display comments from model
     */
    fun displayComments(id: Int) {
        repositoryImpl.fetchCommentsFromEndpoint(id)
    }
}