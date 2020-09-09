package com.decagon.apipost

class ValidateInputs {

    fun validateNumber(editText: String): Boolean {

        /**
         * Validate Nigerian phone number to match
         * starting with +234 or 0 and completed with 10 digits
         * using regex
         */

        val numberInput = editText.trim().replace("\\s+".toRegex(), "")
        val regexPhonePattern = "^(\\+?234|0)\\d{10}\$".toRegex()

        return numberInput.matches(regexPhonePattern)
    }

}