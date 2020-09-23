package com.decagon.mvcstories.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decagon.mvcstories.data.Post

@Dao
interface StoryDao{

    /**
     * Dao for Posts
     */

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addStory(story: Post)

    @Query("SELECT * FROM post_table ORDER BY id DESC")
    fun readAllPosts(): LiveData<List<Post>>

    @Query("SELECT * FROM post_table WHERE id = :id")
    fun readSinglePost(id: Int): LiveData<Post>

    @Query("SELECT * FROM post_table WHERE title LIKE :search ORDER BY id DESC")
    fun getSearchPost(search:String): LiveData<List<Post>>
}