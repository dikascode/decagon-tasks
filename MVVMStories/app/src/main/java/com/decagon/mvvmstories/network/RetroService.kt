package com.decagon.mvvmstories.network

import com.decagon.mvvmstories.data.Comments
import com.decagon.mvvmstories.data.Stories
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface RetroService {

    @GET("posts")
    fun getStories(): Call<List<Stories>>

    @GET("posts")
    suspend fun getPosts(): Response<List<Stories>>

    @GET("posts/{id}")
    fun getStory(@Path("id") key: Int): Call<Stories>

    @GET("posts/{id}/comments")
    suspend fun getComments(@Path("id") key: Int): Response<List<Comments>>

}