package com.decagon.pokemonapicall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.decagon.pokemonapicall.`interface`.RetrofitService
import com.decagon.pokemonapicall.adapter.PokemonListAdapter
import com.decagon.pokemonapicall.common.Common
import com.decagon.pokemonapicall.model.AllPokemon
import com.decagon.pokemonapicall.model.PokemonDetials
import kotlinx.android.synthetic.main.activity_pokemon_details.*
import kotlinx.android.synthetic.main.fragment_pokemon.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SinglePokemonActivity : AppCompatActivity() {
    lateinit var mService: RetrofitService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)

        mService = Common.retrofitService

        getSinglePokemon()
    }


    private fun getSinglePokemon() {
        val pokemonId = intent.getStringExtra("POKEMON_ID");
        mService.getSinglePokemon("$pokemonId").enqueue(object : Callback<PokemonDetials> {
            override fun onFailure(call: Call<PokemonDetials>, t: Throwable) {
                Toast.makeText(this@SinglePokemonActivity, "$t", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<PokemonDetials>,
                response: Response<PokemonDetials>
            ) {
                response.body()?.abilities

                Log.i("TAG", "onResponse Single Pokemon:${response.body()?.abilities!!.toList()} ")
                pokemon_tv.text = response.body()?.id.toString()
            }

        })

    }
}
