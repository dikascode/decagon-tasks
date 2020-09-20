package com.decagon.mvvmstories.viewModel

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.decagon.mvvmstories.repository.SingleStoryRepository

class SingleStoryViewModel(application: Application): AndroidViewModel(application) {

    val repository = SingleStoryRepository(application)
}