package com.decagon.navcontrollerblog.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "post_table")
data class Stories (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
): Serializable