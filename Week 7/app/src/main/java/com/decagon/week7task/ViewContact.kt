package com.decagon.week7task

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.decagon.week7task.fragments.ReadFragment
import kotlinx.android.synthetic.main.fragment_read.*

class ViewContact : AppCompatActivity() {
    //delayed but promising to initialize fragmentTransaction
    private lateinit var fragmentTransaction: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_contacts)

        //Get values from Intent
        val firstName = intent.getStringExtra("FIRST_NAME")
        val phone = intent.getStringExtra("PHONE")
        val email = intent.getStringExtra("EMAIL")
        val contactId = intent.getStringExtra("ID")

        //Setup bundle to pass data to fragment
        val bundle = Bundle()
        bundle.putString("FIRST_NAME", firstName)
        bundle.putString("PHONE", phone)
        bundle.putString("ID", contactId)

        if (email == null) {
            bundle.putString("EMAIL", "")
        } else {
            bundle.putString("EMAIL", email)
        }


        //Initiate fragment to read single data
        var fragment: Fragment
        fragmentTransaction = supportFragmentManager.beginTransaction()
        fragment = ReadFragment()
        // set Fragment class Arguments
        fragment.arguments = bundle
        fragmentTransaction.add(R.id.read_frag, fragment).addToBackStack(null).commit()

    }


}