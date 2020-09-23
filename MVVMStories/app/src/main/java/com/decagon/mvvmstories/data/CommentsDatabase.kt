package com.decagon.mvvmstories.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Create database
 */
@Database(entities = [Comments::class], version = 1, exportSchema = false)
abstract class CommentsDatabase: RoomDatabase() {

    abstract fun commentDao(): CommentDao

    companion object{
        @Volatile
        private var INSTANCE: CommentsDatabase? = null

        fun getDatabase(context: Context): CommentsDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }

            synchronized(this){
                val instance =  Room.databaseBuilder(
                    context.applicationContext,
                    CommentsDatabase::class.java,
                    "comments_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}