package com.decagon.week8livedata.ui

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagon.week8livedata.R
import com.decagon.week8livedata.adapter.RecyclerViewAdapter
import com.decagon.week8livedata.model.AllPokemon
import com.decagon.week8livedata.viewmodel.ActivityViewModel

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById<RecyclerView>(R.id.pokemon_recyclerView)
        recyclerView.setHasFixedSize(true)

        //fetch and pass all pokemon data to adapter
        getAllPokemonDataAndSendToRVAdapter()

        //initializing recycler adapter
        initRecyclerView()


        //Toolbar menu implementation
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId

        when (id) {
            R.id.exit -> finish()
        }

        return true
    }

    private fun initRecyclerView() {

        recyclerView.apply {
            val orientation = resources.configuration.orientation

            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                layoutManager = GridLayoutManager(this@MainActivity, 4)
            } else {
                layoutManager = GridLayoutManager(this@MainActivity, 2)
            }

            recyclerViewAdapter = RecyclerViewAdapter(context)
            adapter = recyclerViewAdapter
        }
    }

    //Get all Pokemon from api and send to recyclerview from LiveData observer
    private fun getAllPokemonDataAndSendToRVAdapter() {
        val viewModel = ViewModelProviders.of(this).get(ActivityViewModel::class.java)
        viewModel.getRecyclerLiveDataObserver().observe(this, Observer<AllPokemon> {

            if (it != null) {
                recyclerViewAdapter.setPokemonData(it.results)
                recyclerViewAdapter.notifyDataSetChanged()
            }
        })

        //Obtaining data from api call
        viewModel.makeAllPokemonApiCall(this)
    }
}