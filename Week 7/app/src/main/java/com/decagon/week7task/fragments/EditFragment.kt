package com.decagon.week7task.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.decagon.week7task.R
import com.decagon.week7task.model.ModelContact
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


class EditFragment : Fragment() {
    lateinit var firebaseReference: DatabaseReference
    lateinit var buttonUpdate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_add, container, false)

        //obtain fragment arguments from bundle

        val firstName = this.arguments?.getString("FIRST_NAME")
        val phoneNumber = this.arguments?.getString("PHONE")
        val email = this.arguments?.getString("EMAIL")
        val contactId = this.arguments?.getString("ID")

        val etFullName: EditText = view.findViewById(R.id.edit_firstname)
        val etPhone: EditText = view.findViewById(R.id.edit_phone)
        val etEmail: EditText = view.findViewById(R.id.edit_email)
        buttonUpdate = view.findViewById(R.id.update_button)

        //Pass the initial value for each user contact
        etFullName.setText(firstName)
        etPhone.setText(phoneNumber)
        etEmail.setText(email)

        //Update Contact
        buttonUpdate.setOnClickListener {

            updateContact(
                contactId!!,
                etFullName.text.toString(),
                etPhone.text.toString(),
                etEmail.text.toString()
            )

            //Pass variables using Bundle to next Fragment
            val bundle = Bundle()
            bundle.putString("FIRST_NAME", etFullName.text.toString())
            bundle.putString("PHONE", etPhone.text.toString())
            bundle.putString("EMAIL", etEmail.text.toString())
            bundle.putString("ID", contactId)

            //remove fragment from stack
//            fragmentManager?.popBackStack()

            //Start new fragment
            val readFrag = ReadFragment()
            readFrag.arguments = bundle
            activity!!.supportFragmentManager.beginTransaction()
                .add(R.id.read_frag, readFrag, "readFragment")
                .addToBackStack(null)
                .commit()
        }

        return view

    }

    private fun updateContact(
        id: String,
        contactName: String,
        contactNumber: String,
        contactEmail: String
    ): Boolean {
        firebaseReference = FirebaseDatabase.getInstance().getReference("contacts").child(id)
        val contact = ModelContact(
            fullName = contactName,
            phoneNumber = contactNumber,
            email = contactEmail,
            id = id
        )

        if(id != "null"){
            firebaseReference.setValue(contact)
            Toast.makeText(requireContext(), "Contact Successfully Updated $id", Toast.LENGTH_LONG).show()
            return true
        }

        return false
    }

}