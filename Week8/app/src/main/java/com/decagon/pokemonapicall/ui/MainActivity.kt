package com.decagon.pokemonapicall.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagon.pokemonapicall.R
import com.decagon.pokemonapicall.`interface`.RetrofitService
import com.decagon.pokemonapicall.adapter.PokemonListAdapter
import com.decagon.pokemonapicall.common.Common
import com.decagon.pokemonapicall.model.AllPokemon
import kotlinx.android.synthetic.main.activity_pokemon_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var mService: RetrofitService
    lateinit var mAdapter: PokemonListAdapter
    lateinit var recyclerView: RecyclerView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mService = Common.retrofitService
        recyclerView = findViewById<RecyclerView>(R.id.pokemon_recyclerView)
        recyclerView.setHasFixedSize(true)


        //Toolbar menu implementation
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //Check for phone orientation an load appropriate GridLayout span count
        checkOrientationForGridLayoutSpanCount()

        //Get all pokemon function
        getAllPokemon()

        /**
         * Refresh on fail of CALL
         */
        refreshButton.setOnClickListener {
            getAllPokemon()
            refreshButton.visibility = View.INVISIBLE
        }


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId

        when(id) {
            R.id.upload -> {
                //Go to activity showing contacts from firebase onclick of toolbar item
                var intent = Intent(this, UploadImage::class.java)
                startActivity(intent)
            }
            R.id.exit -> finish()

        }


        return true
    }


    private fun checkOrientationForGridLayoutSpanCount() {
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.layoutManager = GridLayoutManager(this, 4)
        } else {
            recyclerView.layoutManager = GridLayoutManager(this, 2)
        }
    }

    /**
     * Call function to get all pokemons
     */
    private fun getAllPokemon() {
        mService.getPokemonList().enqueue(object : Callback<AllPokemon> {

            override fun onFailure(call: Call<AllPokemon>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Unable to retrieve data. Please check your internet connection and try again", Toast.LENGTH_LONG).show()
//                Log.i("TAG", "onFailure: Failed to connect $t")

                //Make refresh button visible
                refreshButton.visibility = View.VISIBLE

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