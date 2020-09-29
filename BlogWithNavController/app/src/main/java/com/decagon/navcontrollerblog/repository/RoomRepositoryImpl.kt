package com.decagon.navcontrollerblog.repository

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.decagon.navcontrollerblog.data.CommentDao
import com.decagon.navcontrollerblog.data.Comments
import com.decagon.navcontrollerblog.data.Stories
import com.decagon.navcontrollerblog.data.StoryDao
import com.decagon.navcontrollerblog.network.RetroInstance
import com.decagon.navcontrollerblog.network.RetroService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RoomRepositoryImpl(
    private val storyDao: StoryDao,
    private val commentDao: CommentDao,
    private val retroInstance: RetroService
) : BlogRepository {
    override suspend fun addStory(post: Stories) {
        storyDao.addStory(post)
    }

    override fun readAllStories(): LiveData<List<Stories>> {
        return storyDao.readAllPosts()
    }

    override fun readStory(id: Int): LiveData<Stories> {
        return storyDao.readSinglePost(id)
    }

    override fun getSearchPost(search: String): LiveData<List<Stories>> {
        return storyDao.getSearchPost(search)
    }


    override fun displayStories() {

        GlobalScope.launch {
            val resultList = retroInstance.getPosts().body()

            Log.i("Tag", "onCreate: $resultList")

            if (resultList != null) {
                for (result in resultList) {
                    addStory(result)
                }
            }

        }

    }

    //Add comment implementation for ViewModel
    override suspend fun addComment(comment: Comments) {
        commentDao.addComment(comment)
    }

    override fun getCommentFromRoom(id: Int): LiveData<List<Comments>> {
        return commentDao.readAllComments(id)
    }

    override fun fetchCommentsFromEndpoint(id: Int) {
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