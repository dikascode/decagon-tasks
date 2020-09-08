package com.decagon.pokemonapicall.common

import com.decagon.pokemonapicall.`interface`.RetrofitService
import com.decagon.pokemonapicall.retrofit.RetrofitClient

object Common {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    val retrofitService: RetrofitService
    get() = RetrofitClient.getClient(BASE_URL).create(RetrofitService::class.java)
}