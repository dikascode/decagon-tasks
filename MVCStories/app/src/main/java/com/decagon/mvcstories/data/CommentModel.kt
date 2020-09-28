package com.decagon.mvcstories.data

import androidx.lifecycle.LiveData
import com.decagon.mvcstories.database.CommentDao
import com.decagon.mvcstories.network.RetroInstance
import com.decagon.mvcstories.network.RetroService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class CommentModel(private val commentDao: CommentDao) {

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

        MainScope().launch(Dispatchers.IO)  {
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