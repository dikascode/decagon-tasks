package com.decagon.navcontrollerblog.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CommentDao{

    /**
     * Dao for comments
     */

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addComment(comment: Comments)

    @Query("SELECT * FROM comment_table WHERE postId = :id ORDER BY id DESC")
    fun readAllComments(id: Int): LiveData<List<Comments>>
}