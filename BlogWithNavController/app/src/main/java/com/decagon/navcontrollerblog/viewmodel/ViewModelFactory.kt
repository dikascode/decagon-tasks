package com.decagon.navcontrollerblog.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.decagon.navcontrollerblog.data.StoryDao
import com.decagon.navcontrollerblog.repository.BlogRepository
import com.decagon.navcontrollerblog.repository.RoomRepositoryImpl
import java.lang.IllegalArgumentException

class ViewModelFactory(private val repositoryImpl: BlogRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(BlogRepository::class.java).newInstance(repositoryImpl)

    }
}