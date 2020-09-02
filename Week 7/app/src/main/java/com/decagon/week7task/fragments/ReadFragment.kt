package com.decagon.week7task.fragments

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.decagon.week7task.R
import com.decagon.week7task.firebaseContacts.Firebase
import com.decagon.week7task.model.ModelContact
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_read.*


// the fragment initialization parameters
lateinit var fullName: String
lateinit var phoneNumber: String
lateinit var email: String
lateinit var contactId: String

lateinit var tvFirstName: TextView
lateinit var tvPhone: TextView
lateinit var tvEmail: TextView

lateinit var firebaseReference: DatabaseReference
lateinit var deleteIcon: ImageView


class ReadFragment() : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_read, container, false)
        //obtain fragment arguments from bundle
        fullName = this.arguments?.getString("FIRST_NAME").toString()
        phoneNumber = this.arguments?.getString("PHONE").toString()
        email = this.arguments?.getString("EMAIL").toString()
        contactId = this.arguments?.getString("ID").toString()

        Log.i(TAG, "bind: id: $contactId")
        Log.i(TAG, "bind: fullName: $fullName")

        //Initialize variables to view components
        tvFirstName = view.findViewById(R.id.read_firstname)
        tvPhone = view.findViewById(R.id.read_phone)
        tvEmail = view.findViewById(R.id.read_email)
        deleteIcon = view.findViewById(R.id.iv_delete)

        tvFirstName.text = fullName
        tvPhone.text = phoneNumber
        tvEmail.text = email

        //Delete Contact on click of delete icon
        deleteIcon.setOnClickListener {
            deleteContact(contactId)
        }
        return view

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*
         *Instantiate another fragment to edit contact onclick of edit icon
         */
        iv_msg.setOnClickListener {
            //Pass variables using Bundle to next Fragment
            val bundle = Bundle()
            bundle.putString("FIRST_NAME", fullName)
            bundle.putString("PHONE", phoneNumber)
            bundle.putString("EMAIL", email)
            bundle.putString("ID", contactId)

            //remove fragment from stack
//            activity!!.supportFragmentManager.popBackStack()

            //Start new fragment
            val editFrag = EditFragment()
            editFrag.arguments = bundle
            activity!!.supportFragmentManager.beginTransaction()
                .add(R.id.read_frag, editFrag, "editFragment")
                .addToBackStack(null)
                .commit()
        }


    }



    private fun deleteContact(id: String): Boolean {
        firebaseReference = FirebaseDatabase.getInstance().getReference("contacts").child(id)

        if(id != "null"){
            firebaseReference.removeValue()
            Toast.makeText(requireContext(), "Contact Successfully Deleted", Toast.LENGTH_LONG).show()
            //Go to firebase activity after deleting successfully
            var intent = Intent(context, Firebase::class.java)
            startActivity(intent)
            return true
        }

        return false
    }



}