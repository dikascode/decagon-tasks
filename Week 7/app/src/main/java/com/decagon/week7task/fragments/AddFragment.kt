package com.decagon.week7task.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android. view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction
import com.decagon.week7task.R
import com.decagon.week7task.model.ModelContact
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase

class AddFragment : Fragment() {
//    declare global variables
    private lateinit var saveButton: Button
    lateinit var etFirstName: TextInputEditText
    lateinit var etPhone: TextInputEditText
    private lateinit var firebaseDB: FirebaseDatabase
    lateinit var contact: ModelContact
    private lateinit var fragmentTransaction : FragmentTransaction

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

        //Initialize views
        etFirstName = view.findViewById(R.id.edit_firstname)
        etPhone = view.findViewById(R.id.edit_phone)

        saveButton = view.findViewById(R.id.save_button)

        saveButton.setOnClickListener {
            /*
             *Add contact to firebase database
             */
            firebaseDB = FirebaseDatabase.getInstance()
            val dbRef = firebaseDB.getReference("contacts")
            val id = dbRef.push().key
            contact = ModelContact(fullName = etFirstName.text.toString(), phoneNumber = etPhone.text.toString(), email = "dika@gmail.com", id = id)
            if (id != null) {
                dbRef.child(id).setValue(contact)
            }

            activity!!.supportFragmentManager.popBackStack()
        }
        return view
    }

}