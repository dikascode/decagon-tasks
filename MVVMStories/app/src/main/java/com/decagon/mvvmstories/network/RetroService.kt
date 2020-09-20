package com.decagon.mvvmstories.network

import com.decagon.mvvmstories.model.Story
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface RetroService {

    @GET("posts")
    fun getStories(): Call<List<Story>>

    @GET("posts/{id}")
    fun getStory(@Path("id") key: Int): Call<Story>
}