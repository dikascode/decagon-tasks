package com.decagon.mvvmstories.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.decagon.mvvmstories.data.Stories
import com.decagon.mvvmstories.data.StoryDao
import com.decagon.mvvmstories.network.RetroInstance
import com.decagon.mvvmstories.network.RetroService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RoomStoryRepository(private val storyDao: StoryDao) {
    val readAllStories: LiveData<List<Stories>> = storyDao.readAllPosts()

    suspend fun addStory(post: Stories) {
        storyDao.addStory(post)
    }

    fun readStory(id: Int): LiveData<Stories> {
        return storyDao.readSinglePost(id)
    }

    fun getSearchPost(search: String): LiveData<List<Stories>> {
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