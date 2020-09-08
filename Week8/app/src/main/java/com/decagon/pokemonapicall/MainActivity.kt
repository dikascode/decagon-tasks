package com.decagon.pokemonapicall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagon.pokemonapicall.`interface`.RetrofitService
import com.decagon.pokemonapicall.adapter.PokemonListAdapter
import com.decagon.pokemonapicall.common.Common
import com.decagon.pokemonapicall.model.AllPokemon
import kotlinx.android.synthetic.main.pokemon_list_item.*
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

    private fun getAllPokemon() {
        mService.getPokemonList().enqueue(object : Callback<AllPokemon> {
            override fun onFailure(call: Call<AllPokemon>, t: Throwable) {

                Toast.makeText(this@MainActivity, "$t", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<AllPokemon>, response: Response<AllPokemon>) {
//                rv = findViewById<RecyclerView>(R.id.pokemon_recyclerView)
//                Log.i("TAG", "onFailure: ${response.body()?.results}")
                mAdapter = PokemonListAdapter(baseContext, response.body()?.results!!)
                mAdapter.notifyDataSetChanged()
                recyclerView.adapter = mAdapter
            }
        })

        }
}