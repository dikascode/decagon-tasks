package com.decagon.apipost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class UserProfile : AppCompatActivity() {
    /*
     * Declare global variables for User activity
     */
    private lateinit var textViewName: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var textViewNumber: TextView
    private lateinit var textViewGender: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        //Display user information method
        displayUserData()


    }

    private fun displayUserData() {
        //get data from intent
        val intent = intent
        val name = intent.getStringExtra("Name")
        val email = intent.getStringExtra("Email")
        val phone = intent.getStringExtra("Phone")
        val gender = intent.getStringExtra("Gender")

        //Assign variable to corresponding View Id's
        textViewName = findViewById(R.id.tv_name)
        textViewEmail = findViewById(R.id.tv_email)
        textViewNumber = findViewById(R.id.tv_number)
        textViewGender = findViewById(R.id.tv_gender)


        //Assign data to TextViews
        textViewName.text = name
        textViewEmail.text = "Email: $email"
        textViewNumber.text = "Phone: $phone"
        textViewGender.text = "Gender: $gender"
    }
}