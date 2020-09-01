package com.decagon.week7task.fragments

import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.decagon.week7task.R
import kotlinx.android.synthetic.main.fragment_read.*


// the fragment initialization parameters
lateinit var fullName: String
lateinit var phone: String
lateinit var email: String

lateinit var tvFirstName: TextView
lateinit var tvPhone: TextView
lateinit var tvEmail: TextView


class ReadFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        phone = this.arguments?.getString("PHONE").toString()
        email = this.arguments?.getString("EMAIL").toString()

        //Initialize variables to view components
        tvFirstName = view.findViewById(R.id.read_firstname)
        tvPhone = view.findViewById(R.id.read_phone)
        tvEmail = view.findViewById(R.id.read_email)

        tvFirstName.text = fullName
        tvPhone.text = phone
        tvEmail.text = email
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
            bundle.putString("PHONE", phone)
            bundle.putString("EMAIL", email)

            //Start new fragment
            val editFrag = EditFragment()
            editFrag.arguments = bundle
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.read_frag, editFrag, "editFragment")
                .addToBackStack(null)
                .commit()
        }


    }


}