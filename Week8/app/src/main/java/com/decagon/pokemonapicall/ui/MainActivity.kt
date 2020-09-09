package com.decagon.pokemonapicall.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagon.pokemonapicall.R
import com.decagon.pokemonapicall.`interface`.RetrofitService
import com.decagon.pokemonapicall.adapter.PokemonListAdapter
import com.decagon.pokemonapicall.common.Common
import com.decagon.pokemonapicall.model.AllPokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var mService: RetrofitService
    lateinit var mAdapter: PokemonListAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mService = Common.retrofitService
        recyclerView = findViewById<RecyclerView>(R.id.pokemon_recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        getAllPokemon()
        

    }

    /**
     * Call function to get all pokemons
     */

    private fun getAllPokemon() {
        mService.getPokemonList().enqueue(object : Callback<AllPokemon> {
            override fun onFailure(call: Call<AllPokemon>, t: Throwable) {

                Toast.makeText(this@MainActivity, "Unable to retrieve data. Please check your internet connection and try again", Toast.LENGTH_LONG).show()
                Log.i("TAG", "onFailure: Failed to connect $t")

            }

            override fun onResponse(call: Call<AllPokemon>, response: Response<AllPokemon>) {
                if(response.code() == 200){
//                    Log.i("TAG", "onResponse code: ${response.code()}")
                    mAdapter = PokemonListAdapter(baseContext, response.body()?.results!!)
                    mAdapter.notifyDataSetChanged()
                    recyclerView.adapter = mAdapter
                } else{
                    Toast.makeText(this@MainActivity,"Oops, something seems to have gone wrong. Please try again", Toast.LENGTH_LONG).show()
                }

            }
        })

        }
}