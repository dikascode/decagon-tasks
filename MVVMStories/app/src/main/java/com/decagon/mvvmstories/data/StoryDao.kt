package com.decagon.mvvmstories.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoryDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addStory(story: Post)

    @Query("SELECT * FROM post_table ORDER BY id ASC")
    fun readAllPosts(): LiveData<List<Post>>
}