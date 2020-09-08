package com.decagon.pokemonapicall.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.decagon.pokemonapicall.R
import com.decagon.pokemonapicall.`interface`.RetrofitService
import com.decagon.pokemonapicall.common.Common
import com.decagon.pokemonapicall.model.PokemonDetials
import kotlinx.android.synthetic.main.activity_pokemon_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SinglePokemonActivity : AppCompatActivity() {

    lateinit var mService: RetrofitService
    lateinit var pokemon_textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)

        //Initialize retrofit service
        mService = Common.retrofitService
        pokemon_textView = findViewById(R.id.pokemon_tv)

        //Single Pokemon details implementation
        getSinglePokemonDetails()
    }

    /**
     * Call details for a single pokemon
     */
    private fun getSinglePokemonDetails() {
        //Get Unique Id from intent
        val pokemonId = intent.getStringExtra("POKEMON_ID")


        mService.getSinglePokemon("$pokemonId").enqueue(object : Callback<PokemonDetials> {
            override fun onFailure(call: Call<PokemonDetials>, t: Throwable) {
                Toast.makeText(this@SinglePokemonActivity, "$t", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<PokemonDetials>,
                response: Response<PokemonDetials>
            ) {
                response.body()?.abilities

                //Concatenate moves to a textview
                var moveString = ""
                moveString += response.body()?.moves!!.joinToString(", ") {
                    it.move.name
                }

                pokemon_textView.text = "MOVES:\n\n$moveString"

                //Concatenate Abilities to a textview
                var abilityString = ""
                abilityString += response.body()?.abilities!!.joinToString(", ") {
                    it.ability.name
                }

                pokemon_tv1.text = "ABILITIES:\n\n$abilityString"

                //Concatenate stats to a textview
                var statsString = ""
                statsString += response.body()?.stats!!.joinToString(", ") {
                    "${it.stat.name} : ${it.baseStat} "
                }

                pokemon_tv2.text = "STATS:\n\n$statsString"
            }

        })


        //Set image for each Pokemon
        val imageUrl = intent.getStringExtra("IMAGE_URL")
        Glide.with(this@SinglePokemonActivity).load(imageUrl)
            .into(single_pokemon_iv)

    }
}
