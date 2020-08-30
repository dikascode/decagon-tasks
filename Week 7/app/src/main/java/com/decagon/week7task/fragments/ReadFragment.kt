package com.decagon.week7task.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.decagon.week7task.R
import kotlinx.android.synthetic.main.fragment_read.*


// the fragment initialization parameters
lateinit var firstName: String
lateinit var phone: String

lateinit var tvFirstName: TextView
lateinit var tvPhone: TextView


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
        firstName = this.arguments?.getString("FIRST_NAME").toString()
        phone = this.arguments?.getString("PHONE").toString()

        tvFirstName = view.findViewById(R.id.read_firstname)
        tvPhone = view.findViewById(R.id.read_phone)

        tvFirstName.text = firstName
        tvPhone.text = phone
        return view

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        /*
         *Instantiate another fragment to edit contact onclick of edit icon
         */
        iv_msg.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("FIRST_NAME", firstName)
            bundle.putString("PHONE", phone)

            val editFrag = EditFragment()
            editFrag.arguments = bundle
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.read_frag, editFrag, "findThisFragment")
                .addToBackStack(null)
                .commit()
        }


    }

}