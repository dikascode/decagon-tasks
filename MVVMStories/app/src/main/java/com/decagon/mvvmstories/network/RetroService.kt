package com.decagon.mvvmstories.network

import com.decagon.mvvmstories.model.Comment
import com.decagon.mvvmstories.model.Comments
import com.decagon.mvvmstories.model.Stories
import com.decagon.mvvmstories.model.Story
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface RetroService {

    @GET("posts")
    fun getStories(): Call<List<Stories>>

    @GET("posts/{id}")
    fun getStory(@Path("id") key: Int): Call<Stories>

    @GET("posts/{id}/comments")
    fun getComments(@Path("id") key: Int): Call<List<Comments>>

    @POST("posts")
    fun addComment(@Path("id") key: Int, @Body newComment: Comment): Call<Comment>

    @POST("posts")
    fun addStory(@Body newPost: Story): Call<Story>
}