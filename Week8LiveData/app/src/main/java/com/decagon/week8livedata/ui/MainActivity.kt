package com.decagon.week8livedata.ui

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagon.week8livedata.R
import com.decagon.week8livedata.adapter.RecyclerViewAdapter
import com.decagon.week8livedata.model.AllPokemon
import com.decagon.week8livedata.model.Result
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById<RecyclerView>(R.id.pokemon_recyclerView)
        recyclerView.setHasFixedSize(true)

        initRecyclerView()
        allPokemonData()

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


    private fun allPokemonData() {
        val item = ArrayList<Result>()

//        item.add(Result("Dika", "www.hello.com/1"))

        recyclerViewAdapter.setPokemonData(item)
        recyclerViewAdapter.notifyDataSetChanged()
    }
}