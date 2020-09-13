package com.decagon.pokemonapicall.ui

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagon.pokemonapicall.R
import com.decagon.pokemonapicall.`interface`.RetrofitService
import com.decagon.pokemonapicall.adapter.PokemonListAdapter
import com.decagon.pokemonapicall.common.Common
import com.decagon.pokemonapicall.model.AllPokemon
import com.decagon.pokemonapicall.networking.NetworkStatusChecker
import kotlinx.android.synthetic.main.activity_main.*
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

        //Check for status for connectivity and display data
        checkConnectivityAndDisplayData()


        /**
         * Refresh on fail of CALL
         */
//        refreshButton.setOnClickListener {
//            checkConnectivity()
//            getAllPokemon()
//        }


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId

        when (id) {
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
        progressBar.visibility = View.VISIBLE
        textView.visibility = View.VISIBLE

        mService.getPokemonList().enqueue(object : Callback<AllPokemon> {
            override fun onFailure(call: Call<AllPokemon>, t: Throwable) {
//                Log.i("TAG", "onFailure: Failed to connect $t")
            }

            override fun onResponse(call: Call<AllPokemon>, response: Response<AllPokemon>) {

                if (response.code() == 200) {
                    //Make progress bar and poor network message views to GONE
                    progressBar.visibility = View.GONE
                    textView.visibility = View.GONE
                    
//                   Log.i("TAG", "onResponse code: ${response.code()}")
                    mAdapter = PokemonListAdapter(baseContext, response.body()?.results!!)
                    mAdapter.notifyDataSetChanged()
                    recyclerView.adapter = mAdapter
                }
            }
        })
    }


    private fun checkConnectivityAndDisplayData() {
//        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val activeNetwork = cm.activeNetworkInfo
//        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting

        val networkConnection = NetworkStatusChecker(this)

        networkConnection.observe(this, Observer { isConnected ->
            if (isConnected) {
                noInternetIv.visibility = View.GONE

                //Get all pokemon function
                getAllPokemon()

            } else {
                Toast.makeText(
                    this,
                    "Oops. Please check your internet connection and try again",
                    Toast.LENGTH_LONG
                ).show()

                //Sad Pokemon image visible
                noInternetIv.visibility = View.VISIBLE

                //Set appropriate views to GONE or VISIBLE
                progressBar.visibility = View.GONE
                textView.visibility = View.GONE
            }
        })

    }
}