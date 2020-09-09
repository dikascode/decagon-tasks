package com.decagon.apipost.api

import com.decagon.apipost.model.DefaultResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {

    @FormUrlEncoded
    @POST("register")
    fun registerUser(
        @Field("firstname") firstname: String,
        @Field("lastname") lastname: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("gender") gender: String,
        @Field("password") password: String 
        ):Call<DefaultResponse>

}