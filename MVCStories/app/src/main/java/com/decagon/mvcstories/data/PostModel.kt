package com.decagon.mvcstories.data

import androidx.lifecycle.LiveData
import com.decagon.mvcstories.database.StoryDao
import com.decagon.mvcstories.network.RetroInstance
import com.decagon.mvcstories.network.RetroService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostModel(private val storyDao: StoryDao) {

    val readAllStories: LiveData<List<Post>> = storyDao.readAllPosts()

    suspend fun addStory(post: Post) {
        storyDao.addStory(post)
    }

    fun readStory(id: Int): LiveData<Post> {
        return storyDao.readSinglePost(id)
    }

    fun getSearchPost(search: String): LiveData<List<Post>> {
        return storyDao.getSearchPost(search)
    }


    //Retrofit instance of Service
    private val retroInstance: RetroService =
        RetroInstance.getRetroInstance().create(RetroService::class.java)


    fun displayStories() {

        GlobalScope.launch {
            val resultList = retroInstance.getPosts().body()

            if (resultList != null) {
                for (result in resultList) {
                    addStory(result)
                }
            }

        }

    }


}