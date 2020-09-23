package com.decagon.mvcstories.network

import com.decagon.mvcstories.data.Comments
import com.decagon.mvcstories.data.Post
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetroService {

    @GET("posts")
    fun getStories(): Call<List<Post>>

    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>

    @GET("posts/{id}")
    fun getStory(@Path("id") key: Int): Call<Post>

    @GET("posts/{id}/comments")
    suspend fun getComments(@Path("id") key: Int): Response<List<Comments>>
}