package com.decagon.mvvmstories.repository

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.decagon.mvvmstories.viewModel.RoomStoryViewModel

class RoomStoryFactory(val application: Application): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RoomStoryViewModel(application) as T
    }
}