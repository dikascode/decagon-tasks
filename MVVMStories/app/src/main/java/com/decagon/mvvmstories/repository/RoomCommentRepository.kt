package com.decagon.mvvmstories.repository

import androidx.lifecycle.LiveData
import com.decagon.mvvmstories.data.CommentDao
import com.decagon.mvvmstories.data.Comments
import com.decagon.mvvmstories.network.RetroInstance
import com.decagon.mvvmstories.network.RetroService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class RoomCommentRepository(private val commentDao: CommentDao) {

    //Add comment implementation for ViewModel
    suspend fun addComment(comment: Comments) {
        commentDao.addComment(comment)
    }

    fun getCommentFromRoom(id: Int): LiveData<List<Comments>> {
        return commentDao.readAllComments(id)
    }


    //Retrofit instance of Service
    private val retroInstance: RetroService =
        RetroInstance.getRetroInstance().create(RetroService::class.java)

    //Comments from endpoint call
    fun fetchCommentsFromEndpoint(id: Int) {

        GlobalScope.launch {
            val resultList = retroInstance.getComments(id).body()

            //Save each comment to Room database
            if (resultList != null) {
                for (result in resultList) {
                    addComment(result)
                }
            }

        }

    }

}