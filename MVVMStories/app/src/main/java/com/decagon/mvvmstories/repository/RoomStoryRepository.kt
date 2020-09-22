package com.decagon.mvvmstories.repository

import androidx.lifecycle.LiveData
import com.decagon.mvvmstories.data.Post
import com.decagon.mvvmstories.data.StoryDao

class RoomStoryRepository(private val storyDao: StoryDao) {
    val readAllPost: LiveData<List<Post>> = storyDao.readAllPosts()

    suspend fun addStory(post: Post) {
        storyDao.addStory(post)
    }
}