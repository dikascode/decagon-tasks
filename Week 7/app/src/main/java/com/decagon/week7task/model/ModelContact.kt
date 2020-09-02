package com.decagon.week7task.model

import com.google.firebase.database.Exclude

data class ModelContact(
    var addedBy: String = "Dika",
    var fullName: String = "",
    var phoneNumber: String = "",
    var email: String = "",
    var id: String? = null
) {

}