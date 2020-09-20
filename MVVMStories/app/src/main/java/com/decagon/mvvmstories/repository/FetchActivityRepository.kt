package com.decagon.mvvmstories.repository

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.decagon.mvvmstories.model.Comments
import com.decagon.mvvmstories.model.Story
import com.decagon.mvvmstories.network.RetroInstance
import com.decagon.mvvmstories.network.RetroService
import retrofit2.*

class FetchActivityRepository(val application: Application) {

    val showProgress = MutableLiveData<Boolean>()

    val storyList = MutableLiveData<List<Story>>()

    val singleStory = MutableLiveData<Story>()

    val commentsList = MutableLiveData<List<Comments>>()


    //Retrofit instance of Service
    private val retroInstance: RetroService =
        RetroInstance.getRetroInstance().create(RetroService::class.java)

    fun changeState() {
        showProgress.value = !(showProgress.value != null && showProgress.value!!)

    }

    fun displayStories() {
        showProgress.value = true

        val call = retroInstance.getStories()

        call.enqueue(object : Callback<List<Story>> {
            override fun onFailure(call: Call<List<Story>>, t: Throwable) {
                showProgress.value = false
                Toast.makeText(application, "Failed", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "onFailure: $t")
            }

            override fun onResponse(
                call: Call<List<Story>>,
                response: Response<List<Story>>
            ) {
                showProgress.value = false
                storyList.value = response.body()
//                Log.d(TAG, "onResponse: ${response.body()}")
            }

        })

    }

    fun getStory(id: Int) {
        showProgress.value = true
        val call = retroInstance.getStory(id)

        call.enqueue(object : retrofit2.Callback<Story> {
            override fun onFailure(call: Call<Story>, t: Throwable) {
                showProgress.value = false
                Log.d(TAG, "onFailure: $t")
            }

            override fun onResponse(call: Call<Story>, response: Response<Story>) {
                showProgress.value = false
                singleStory.value = response.body()
//                Log.d(TAG, "onResponse search: ${response.body()}")
            }

        })

    }

    fun getComments(id: Int) {
        val call = retroInstance.getComments(id)

        call.enqueue(object: Callback<List<Comments>> {
            override fun onFailure(call: Call<List<Comments>>, t: Throwable) {
                Log.d(TAG, "onFailure Get comments: $t")
            }

            override fun onResponse(call: Call<List<Comments>>, response: Response<List<Comments>>) {
                commentsList.value = response.body()
//                Log.d(TAG, "Success Get comments: ${response.body()}")
            }

        })
    }
}

