package com.decagon.week7task.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.decagon.week7task.R
import com.decagon.week7task.model.ModelContact
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase

class AddFragment : Fragment() {
    //    declare global variables
    private lateinit var saveButton: Button
    lateinit var etFullName: TextInputEditText
    lateinit var etPhone: TextInputEditText
    lateinit var etEmail: TextInputEditText
    private lateinit var firebaseDB: FirebaseDatabase
    lateinit var contact: ModelContact
    private lateinit var fragmentTransaction: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_edit, container, false)

        //Initialize edit text views
        etFullName = view.findViewById(R.id.edit_firstname)
        etPhone = view.findViewById(R.id.edit_phone)
        etEmail = view.findViewById(R.id.edit_email)

        saveButton = view.findViewById(R.id.save_button)

        /**
         * Instantiate database instance
         */
        firebaseDB = FirebaseDatabase.getInstance()
        val dbRef = firebaseDB.getReference("contacts")

        saveButton.setOnClickListener {
            val fullName = etFullName.text.toString()
            val phoneNumber = etPhone.text.toString()
            val email = etEmail.text.toString()

            //Get unique key for each upload
            val id = dbRef.push().key
            /**
             *Add contact to firebase database
             */
            contact = ModelContact(
                fullName = fullName,
                phoneNumber = phoneNumber,
                email = email,
                id = id
            )
            if (id != null) {
                dbRef.child(id).setValue(contact)
            }

            //Display Toast if successful
            Toast.makeText(requireContext(), "Contact Successfully Added", Toast.LENGTH_LONG).show()

            //Pass variables using Bundle to next Fragment
            val bundle = Bundle()
            bundle.putString("FIRST_NAME", fullName)
            bundle.putString("PHONE", phoneNumber)
            bundle.putString("EMAIL", email)
            bundle.putString("ID", id)

//            Log.i(ContentValues.TAG, "bind: ID $id")
//            Log.i(ContentValues.TAG, "bind: fullname: $fullName")

           //remove fragment from stack
            fragmentManager?.popBackStack()


           //Start new fragment
            val fragment = ReadFragment()
            fragment.arguments = bundle
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.firebase_frag, fragment, "readFragment")
                .addToBackStack(null)
                .commit()
        }
        return view
    }

}