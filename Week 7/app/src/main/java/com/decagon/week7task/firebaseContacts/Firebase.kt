package com.decagon.week7task.firebaseContacts

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
import com.decagon.week7task.R
import com.decagon.week7task.firebaseContacts.adapter.FirebaseRecyclerAdapter
import com.decagon.week7task.fragments.AddFragment
import com.decagon.week7task.model.ModelContact
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class Firebase : AppCompatActivity() {
    /*
     * declare variables globally
     */

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: FirebaseRecyclerAdapter
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var fabButton: FloatingActionButton
    private lateinit var fragmentTransaction: FragmentTransaction
    lateinit var firebaseReference: DatabaseReference

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
            addReadFragment()
        }
    }

    private fun addReadFragment() {
        var fragment: Fragment
        fragmentTransaction = supportFragmentManager.beginTransaction()
        fragment = AddFragment()
        fragmentTransaction.add(R.id.firebase_frag, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        //Hide FAB button
        fabButton.visibility = View.GONE
    }


    //Toolbar setup
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.firebase_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId

        when (id) {
            R.id.exit -> finish()
            R.id.add -> {
                addReadFragment()
            }

        }
        return true
    }


    /**
     * Show the contacts in the RecyclerView.
     */

    private fun showContacts() {
        val list = ArrayList<ModelContact>()
        var contactList = arrayListOf<ModelContact>()
        //Assign recycler view layout id
        recyclerView = findViewById(R.id.rv_firebase)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        /*
         * Instantiate firebase Database reference
         */
        firebaseReference = FirebaseDatabase.getInstance().getReference("contacts")

        firebaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(contactSnapShot: DataSnapshot) {
                if (contactSnapShot.exists()) {
                    for (item in contactSnapShot.children) {
                        val contact = item.getValue(ModelContact::class.java)
                        if (contact != null) {
                            contactList.add(contact)
                        }
                    }

                    list.addAll(contactList)
                    recyclerAdapter = FirebaseRecyclerAdapter(this@Firebase, list)
                    recyclerView?.adapter = recyclerAdapter


                }
            }

        })

    }

}
