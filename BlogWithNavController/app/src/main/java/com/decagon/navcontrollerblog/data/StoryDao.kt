package com.decagon.navcontrollerblog.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface StoryDao{

    /**
     * Dao for Posts
     */

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addStory(story: Stories)

    @Query("SELECT * FROM post_table ORDER BY id DESC")
    fun readAllPosts(): LiveData<List<Stories>>

    @Query("SELECT * FROM post_table WHERE id = :id")
    fun readSinglePost(id: Int): LiveData<Stories>

    @Query("SELECT * FROM post_table WHERE title LIKE :search ORDER BY id DESC")
    fun getSearchPost(search:String): LiveData<List<Stories>>
}