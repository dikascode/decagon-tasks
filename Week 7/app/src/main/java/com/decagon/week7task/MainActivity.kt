package com.decagon.week7task

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagon.week7task.DataSet.Companion.createDataSet
import com.decagon.week7task.firebaseContacts.Firebase
import com.decagon.week7task.model.ModelContact
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), RecyclerAdapter.OnContactItemClickListener  {
        /*
         * declare variables globally
         */
        private lateinit var recyclerView: RecyclerView
        private lateinit var recyclerAdapter: RecyclerAdapter

        private lateinit var toolbar: androidx.appcompat.widget.Toolbar

        // Request code for READ_CONTACTS. It can be any number > 0.
        private val CONTACTS_PERMISSION_CODE = 10;

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            //Toolbar menu implementation
            toolbar = findViewById(R.id.toolbar)
            setSupportActionBar(toolbar)

            // Read and show the contacts
            showContacts()

        }

    //Oncrate options for toolbar in main activity layout
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId

       when(id) {
           R.id.search -> Toast.makeText(this, "You clicked Search", Toast.LENGTH_LONG).show()
           R.id.cloud -> {
               var intent = Intent(this, Firebase::class.java)
               startActivity(intent)
           }
           R.id.exit -> finish()

       }


        return true
    }




    /**
     * Show the contacts in the RecyclerView.
     */

    private fun showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS),
                CONTACTS_PERMISSION_CODE
            )
            //Wait for callback in onRequestPermissionsResult(int, String[], int[])

        } else {
            /*
             *Android version is lesser than 6.0 or the permission is already granted.
             */
                //Assign recycler view layout id
                recyclerView = findViewById(R.id.recycler_view)
                recyclerView.layoutManager = LinearLayoutManager(this)

                recyclerAdapter = RecyclerAdapter(this)
                recyclerView?.adapter = recyclerAdapter


            //Submit Data to Adapter
                val data = createDataSet(this)
                recyclerAdapter.submitList(data)

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CONTACTS_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts()
            } else {
                val intent = Intent(this, PermitActivity::class.java)
                startActivity(intent)

            }
        }
    }


    override fun onItemClicked(item: ModelContact, position: Int) {

    }

}