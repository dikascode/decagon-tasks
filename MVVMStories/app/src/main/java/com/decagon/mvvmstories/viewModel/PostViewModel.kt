package com.decagon.mvvmstories.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.decagon.mvvmstories.repository.PostRepository

class PostViewModel(application: Application): AndroidViewModel(application) {

    val repository = PostRepository(application)
}