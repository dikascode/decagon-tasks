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
                Toast.makeText(this@SinglePokemonActivity, "Unable to retrieve data. Please check your internet connection and try again", Toast.LENGTH_LONG).show()

            }

            override fun onResponse(
                call: Call<PokemonDetials>,
                response: Response<PokemonDetials>
            ) {
                if (response.code() == 200) {
                    //Concatenate moves to a textview

                    pokemon_name.text = response.body()?.name ?: "No name to display"
                    var moveString = ""
                    moveString += response.body()?.moves?.joinToString(", ") {
                        it.move.name
                    } ?: "No moves to display"

                    pokemon_textView.text = moveString

                    //Concatenate Abilities to a textview
                    var abilityString = ""
                    abilityString += response.body()?.abilities?.joinToString(", ") {
                        it.ability.name
                    } ?: "No Abilities to display"

                    pokemon_tv1.text = abilityString

                    //Concatenate stats to a textview
                    var statsString = ""
                    statsString += response.body()?.stats?.joinToString(", ") {
                        "${it.stat.name} : ${it.baseStat} "
                    } ?:  "No Stats to display"

                    pokemon_tv2.text = statsString
                }else{
                    Toast.makeText(this@SinglePokemonActivity,"Oops, something seems to have gone wrong. Please try again", Toast.LENGTH_LONG).show()
                }

            }

        })


        //Set image for each Pokemon
        val imageUrl = intent.getStringExtra("IMAGE_URL")
        Glide.with(this@SinglePokemonActivity).load(imageUrl)
            .into(single_pokemon_iv)

    }
}
