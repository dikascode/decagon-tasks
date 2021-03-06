package com.decagon.pokemonapicall.`interface`

import com.decagon.pokemonapicall.model.AllPokemon
import com.decagon.pokemonapicall.model.PokemonDetials
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {
    /**
     * Interface for retrofit REST GET operations
     */
    @GET("pokemon?offset=0&limit=1050")
    fun getPokemonList(): Call<AllPokemon>

    @GET("pokemon/{id}")
    fun getSinglePokemon(@Path("id") key: String): Call<PokemonDetials>
}

