package com.decagon.week7task.firebaseContacts

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagon.week7task.DataSet
import com.decagon.week7task.R
import com.decagon.week7task.RecyclerAdapter
import com.decagon.week7task.firebaseContacts.adapter.FirebaseRecyclerAdapter
import com.decagon.week7task.firebaseContacts.fb_model.FirebaseListClass
import com.decagon.week7task.firebaseContacts.fb_model.FirebaseListClass.Companion.createDataSet
import com.decagon.week7task.fragments.AddFragment
import com.decagon.week7task.fragments.ReadFragment
import com.decagon.week7task.model.ModelContact
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_firebase.*

class Firebase : AppCompatActivity(), FirebaseRecyclerAdapter.OnContactItemClickListener {
    /*
     * declare variables globally
     */

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: FirebaseRecyclerAdapter
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var fabButton: FloatingActionButton
    private lateinit var fragmentTransaction : FragmentTransaction
    private lateinit var databaseContacts: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase)

        //Show contacts from firebase
        showContacts()

        //Toolbar menu implementation for firebase activity
        toolbar = findViewById(R.id.fb_toolbar)
        setSupportActionBar(toolbar)

        //Initialize fabButton
        fabButton = findViewById(R.id.fab_btn)


        /*
         * FAB onclick listener
         */
        fabButton.setOnClickListener {
            //Create Add contact fragment
            var fragment: Fragment
            fragmentTransaction = supportFragmentManager.beginTransaction()
            fragment = AddFragment()
            fragmentTransaction.add(R.id.firebase_frag, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

            //Hide FAB button
            fabButton.visibility = View.GONE

        }


    }

    override fun onStart() {
        super.onStart()

        if(fabButton.visibility == View.GONE) {
            fabButton.visibility = View.VISIBLE
        }
    }

    override fun onRestart() {
        super.onRestart()
        //Show FAB button
        if(fabButton.visibility == View.GONE) {
            fabButton.visibility = View.VISIBLE
        }
    }


    //Toolbar setup
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.firebase_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId

        when (id) {
            R.id.search -> Toast.makeText(this, "You clicked Search", Toast.LENGTH_LONG).show()
            R.id.share -> Toast.makeText(this, "You clicked Share", Toast.LENGTH_LONG).show()
            R.id.exit -> finish()

        }
        return true
    }


    /**
     * Show the contacts in the RecyclerView.
     */

    private fun showContacts() {


        //Assign recycler view layout id
        recyclerView = findViewById(R.id.rv_firebase)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerAdapter = FirebaseRecyclerAdapter(this)
        recyclerView?.adapter = recyclerAdapter


        //Submit Data to Adapter
        val data = createDataSet(this)
        recyclerAdapter.submitList(data)


        //Database reference
        databaseContacts = FirebaseDatabase.getInstance().getReference("contacts")


    }

    override fun onItemClicked(item: ModelContact, position: Int) {
    }
}
