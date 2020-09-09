package com.decagon.apipost

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    /*
     * Declare global variables
     */

    lateinit var editTextName: EditText
    lateinit var editTextEmail: EditText
    lateinit var editTextNumber: EditText
    lateinit var spinner: Spinner
    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize variables
        button = findViewById(R.id.button)
        editTextName = findViewById(R.id.et_name)
        editTextEmail = findViewById(R.id.et_email)
        editTextNumber = findViewById(R.id.et_number)
        spinner = findViewById(R.id.spinner)

        //setOnClickListener to handle validation
        button.setOnClickListener {
            validateName(); validateEmail(); validateNumber(); validateSpinner()

            val firstname = editTextName.text.toString()
            val lastname = et_lastname.text.toString()
            val email = editTextEmail.text.toString()
            val phone = editTextNumber.text.toString()
            val gender = spinner.selectedItem.toString()

            /*
             * If validation is true
             * Progress with Intent to next activity
             */
            if(validateName() && validateEmail() && validateNumber() && validateSpinner()){

                RetrofitClient.instance
//                val intent = Intent(this, UserProfile::class.java)
//                intent.putExtra("Name", firstname)
//                intent.putExtra("Email", email)
//                intent.putExtra("Phone", phone)
//                intent.putExtra("Gender", gender)
//                startActivity(intent)
//                finish()
            }

        }

    }



    /*
     * Validate name field
     */
    private fun validateName(): Boolean {
        /*
         * Initialize Name EditText field
         */
        editTextName = findViewById(R.id.et_name)
        var nameInput = editTextName.text.trim()
        var lastnameInput = et_lastname.text?.trim()

        /*
         * Replace white spaces between string with single space
         */
        nameInput = nameInput.replace("\\s+".toRegex(), " ")
        lastnameInput = lastnameInput?.replace("\\s+".toRegex(), " ")

        return if (nameInput.isEmpty()) {
            Toast.makeText(this, "Firstname field cannot be empty", Toast.LENGTH_SHORT).show()
            editTextName.error = "Name Field can't be empty"
            editTextName.requestFocus()
            false
        } else if(lastnameInput!!.isEmpty() ){
            et_lastname.error = "Lastname Field can't be empty"
            et_lastname.requestFocus()
            false
        }else if(et_password.text!!.isEmpty()){
            et_password.error = "Passwword Field can't be empty"
            et_password.requestFocus()
            false
        }else {
            editTextName.error = null
            et_lastname.error = null
            et_password.error = null
            true
        }

    }


    /*
     * Validate Email input
     */

    private fun validateEmail(): Boolean {
        /*
        * Initialize Email EditText field
        */
        editTextEmail = findViewById(R.id.et_email)
        val emailInput = editTextEmail.text.trim()

        return if (emailInput.isEmpty()) {
            Toast.makeText(this, "Email field cannot be empty", Toast.LENGTH_SHORT).show()
            editTextEmail.error = "Email Field cannot be empty"
            false

            //Check if the email input is valid using regular expressions
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            editTextEmail.error = "Please enter a valid email address"
            false
        } else {
            editTextEmail.error = null
            true
        }
    }

    private fun validateSpinner(): Boolean {
        val selectedId = spinner.selectedItemId.toString()

        //Spinner validate condition
        return if (selectedId == "0") {
            Toast.makeText(this, "Please select a gender to proceed", Toast.LENGTH_SHORT).show()
            false
        }else{
            true
        }
    }

    private fun validateNumber() : Boolean {
        //Assign view id
        editTextNumber = findViewById(R.id.et_number)

        //Instantiate ValidateInputs class
        var validationObj = ValidateInputs()
        val result = validationObj.validateNumber(editTextNumber.text.toString())

        return if(editTextNumber.text.isEmpty()){
            editTextNumber.error = "Phone field cannot be empty"
            false
        }else if (!result){
            editTextNumber.error = "Please input a correct Nigerian number"
            false
        } else {
            result
        }


    }


}