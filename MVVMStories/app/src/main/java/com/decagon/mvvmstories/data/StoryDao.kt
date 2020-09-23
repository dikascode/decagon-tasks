package com.decagon.mvvmstories.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoryDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addStory(story: Stories)

    @Query("SELECT * FROM post_table ORDER BY id DESC")
    fun readAllPosts(): LiveData<List<Stories>>

    @Query("SELECT * FROM post_table WHERE id = :id")
    fun readSinglePost(id: Int): LiveData<Stories>
}