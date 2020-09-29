package com.decagon.navcontrollerblog.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Create database
 */
@Database(entities = [Stories::class, Comments::class], version = 1, exportSchema = false)
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