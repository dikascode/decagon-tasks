package com.decagon.mvvmstories.repository

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.decagon.mvvmstories.model.Comments
import com.decagon.mvvmstories.model.Stories
import com.decagon.mvvmstories.network.RetroInstance
import com.decagon.mvvmstories.network.RetroService
import retrofit2.*

class FetchRepository(val application: Application) {

    val showProgress = MutableLiveData<Boolean>()

    val storyList = MutableLiveData<List<Stories>>()

    val singleStory = MutableLiveData<Stories>()

    val commentsList = MutableLiveData<List<Comments>>()



    //Retrofit instance of Service
    private val retroInstance: RetroService =
        RetroInstance.getRetroInstance().create(RetroService::class.java)

    fun changeState() {
        showProgress.value = !(showProgress.value != null && showProgress.value!!)

    }

    /**
     * Get all stories/post from end point call to update ViewModel
     */
    fun displayStories() {
        showProgress.value = true

        val call = retroInstance.getStories()
//
//        call.enqueue(object : Callback<List<Stories>> {
//            override fun onFailure(call: Call<List<Stories>>, t: Throwable) {
//                showProgress.value = false
//                Toast.makeText(application, "Failed", Toast.LENGTH_SHORT).show()
//                Log.d(TAG, "onFailure: $t")
//            }
//
//            override fun onResponse(
//                call: Call<List<Stories>>,
//                response: Response<List<Stories>>
//            ) {
//                showProgress.value = false
//
//                val list = response.body()
//
//                if (list != null) {
//                    for (i in list) {
//
//                    }
//                }
//                storyList.value = response.body()
////                Log.d(TAG, "onResponse: ${response.body()}")
//            }
//
//        })

    }


    /**
     * Get a single story from end point call to update ViewModel
     */
    fun getStory(id: Int) {
        showProgress.value = true
        val call = retroInstance.getStory(id)

//        call.enqueue(object : retrofit2.Callback<Stories> {
//            override fun onFailure(call: Call<Stories>, t: Throwable) {
//                showProgress.value = false
//                Log.d(TAG, "onFailure: $t")
//            }

//            override fun onResponse(call: Call<Stories>, response: Response<Stories>) {
//                showProgress.value = false
//                singleStory.value = response.body()
////                Log.d(TAG, "onResponse search: ${response.body()}")
//            }
//
//        })

    }


    /**
     * Get comments for a post from end point call to update ViewModel
     */
//    suspend fun getComments(id: Int) {
//        val call = retroInstance.getComments(id)
//
//        call.enqueue(object : Callback<List<Comments>> {
//            override fun onFailure(call: Call<List<Comments>>, t: Throwable) {
//                Log.d(TAG, "onFailure Get comments: $t")
//            }
//
//            override fun onResponse(
//                call: Call<List<Comments>>,
//                response: Response<List<Comments>>
//            ) {
//                commentsList.value = response.body()
////                Log.d(TAG, "Success Get comments: ${response.body()}")
//            }
//
//        })
//    }
}

