package com.decagon.mvvmstories.repository

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.decagon.mvvmstories.model.Comment
import com.decagon.mvvmstories.model.Story
import com.decagon.mvvmstories.network.RetroInstance
import com.decagon.mvvmstories.network.RetroService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PostRepository(val application: Application) {

    val comment = MutableLiveData<Comment>()

    //Retrofit instance of Service
    private val retroInstance: RetroService =
        RetroInstance.getRetroInstance().create(RetroService::class.java)

    fun addComment(id: Int, newComment: Comment) {
        val call = retroInstance.addComment(id, newComment)

        call.enqueue(object: Callback<Comment>{
            override fun onFailure(call: Call<Comment>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
                Toast.makeText(application, "Failed", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                if(response.isSuccessful){
                    Log.d(TAG, "onResponse success: ${response.body()}")
                }
            }

        })
    }


    fun addStory(newPost: Story) {
        val call = retroInstance.addStory(newPost)

        call.enqueue(object: Callback<Story>{
            override fun onFailure(call: Call<Story>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
                Toast.makeText(application, "Failed", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Story>, response: Response<Story>) {
                if(response.isSuccessful){
                    Toast.makeText(application, "${response.code()}", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "onResponse success: ${response.code()}")
                }else{
                    Log.d(TAG, "onResponse: ${response.code()} ")
                }
            }

        })
    }
}