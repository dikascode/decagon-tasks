package com.decagon.mvcstories.controller

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.decagon.mvcstories.data.Post
import com.decagon.mvcstories.data.PostModel
import com.decagon.mvcstories.database.BlogDatabase
import com.decagon.mvcstories.view.PostsActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class PostController(view: Activity) {

    val readAllStories: LiveData<List<Post>>

    private val repository: PostModel

    init {
        val storyDao = BlogDatabase.getDatabase(view.applicationContext).storyDao()
        repository = PostModel(storyDao)
        readAllStories = repository.readAllStories

    }


    /**
     * setup add story and run in the background
     */
    fun addStory(model: Post) {
        MainScope().launch(Dispatchers.Unconfined) {
            repository.addStory(model)
        }
    }


    fun readStory(id: Int): LiveData<Post> {
        return repository.readStory(id)
    }

    fun displayStories() {
        repository.displayStories()
    }

    fun getSearchPosts(search: String): LiveData<List<Post>> {
        return repository.getSearchPost(search)
    }

}