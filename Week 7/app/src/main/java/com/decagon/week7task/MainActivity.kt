package com.decagon.week7task

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagon.week7task.DataSet.Companion.createDataSet
import com.decagon.week7task.model.ModelContact
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), RecyclerAdapter.OnContactItemClickListener  {
        /*
         * declare variables globally
         */
        private lateinit var recyclerView: RecyclerView
        private lateinit var recyclerAdapter: RecyclerAdapter
        private  lateinit var fabButton : FloatingActionButton

        // Request code for READ_CONTACTS. It can be any number > 0.
        private val readContactsPermissions = 100;

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)


            // Read and show the contacts
            showContacts()


            //Initialize fabButton
            fabButton = findViewById(R.id.fab_btn)

            /*
             * FAB onclick listener
             */
            fabButton.setOnClickListener {

            }

        }





    override fun onItemClicked(item: ModelContact, position: Int) {

    }


    /**
     * Show the contacts in the ListView.
     */

    private fun showContacts(): Unit {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS),
                readContactsPermissions
            )
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.

            //Activate RecyclerView

                //Assign recycler view layout id
                recyclerView = findViewById(R.id.recycler_view)
                recyclerView.layoutManager = LinearLayoutManager(this)

                recyclerAdapter = RecyclerAdapter(this)
                recycler_view?.adapter = recyclerAdapter



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
        if (requestCode == readContactsPermissions) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts()
            } else {
                Toast.makeText(
                    this,
                    "Until you grant the permission, we cannot display the names",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}