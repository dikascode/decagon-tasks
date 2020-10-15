package com.decagon.navcontrollerblog.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "comment_table")
data class Comments (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String
): Serializable