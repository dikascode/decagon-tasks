package com.decagon.mvcstories.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.decagon.mvcstories.data.Comments
import com.decagon.mvcstories.data.Post

/**
 * Create database
 */
@Database(entities = [Post::class, Comments::class], version = 1, exportSchema = false)
abstract class BlogDatabase: RoomDatabase() {

    abstract fun storyDao(): StoryDao

    abstract fun commentDao(): CommentDao

    companion object{
        @Volatile
        private var INSTANCE: BlogDatabase? = null

        fun getDatabase(context: Context): BlogDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }

            synchronized(this){
                val instance =  Room.databaseBuilder(
                    context.applicationContext,
                    BlogDatabase::class.java,
                    "blog_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}