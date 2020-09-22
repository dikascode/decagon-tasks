package com.decagon.mvvmstories.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Create database
 */
@Database(entities = [Post::class], version = 1, exportSchema = false)
abstract class StoryDatabase: RoomDatabase() {

    abstract fun storyDao(): StoryDao

    companion object{
        @Volatile
        private var INSTANCE: StoryDatabase? = null

        fun getDatabase(context: Context): StoryDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }

            synchronized(this){
                val instance =  Room.databaseBuilder(
                    context.applicationContext,
                    StoryDatabase::class.java,
                    "story_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}