package com.decagon.week7task.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.decagon.week7task.R

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


class EditFragment : Fragment() {
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

        //obtain fragment arguments from bundle

        val firstName = this.arguments?.getString("FIRST_NAME")
        val phone = this.arguments?.getString("PHONE")
        val email = this.arguments?.getString("EMAIL")

        val etFirstName: EditText = view.findViewById(R.id.edit_firstname)
        val etPhone: EditText = view.findViewById(R.id.edit_phone)
        val etEmail: EditText = view.findViewById(R.id.edit_email)

        //Pass the initial value for each user contact
        etFirstName.setText(firstName)
        etPhone.setText(phone)
        etEmail.setText(email)
        return view

    }

}