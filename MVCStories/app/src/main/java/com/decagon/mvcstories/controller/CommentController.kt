package com.decagon.mvcstories.controller

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.decagon.mvcstories.data.CommentModel
import com.decagon.mvcstories.data.Comments
import com.decagon.mvcstories.database.BlogDatabase
import com.decagon.mvcstories.view.PostActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class CommentController(var view: PostActivity) {

    private val repository: CommentModel

    init {
        val commentDao = BlogDatabase.getDatabase(view.application).commentDao()
        repository = CommentModel(commentDao)

    }

    /**
     * setup add user and rn in the background
     */
    fun addComment(comment: Comments) {
        MainScope().launch(Dispatchers.Unconfined) {
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