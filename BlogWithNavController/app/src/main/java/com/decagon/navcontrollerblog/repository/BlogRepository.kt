package com.decagon.navcontrollerblog.repository

import androidx.lifecycle.LiveData
import com.decagon.navcontrollerblog.data.Comments
import com.decagon.navcontrollerblog.data.Stories

interface BlogRepository {
    /**
     * interface methods for post and comments to be implemented
     */
    suspend fun addStory(post: Stories)

    fun readAllStories(): LiveData<List<Stories>>

    fun readStory(id: Int): LiveData<Stories>

    fun getSearchPost(search: String): LiveData<List<Stories>>

    fun displayStories()

    suspend fun addComment(comment: Comments)

    fun getCommentFromRoom(id: Int): LiveData<List<Comments>>

    fun fetchCommentsFromEndpoint(id: Int)
}