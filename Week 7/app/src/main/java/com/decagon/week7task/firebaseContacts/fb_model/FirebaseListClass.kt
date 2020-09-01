package com.decagon.week7task.firebaseContacts.fb_model

import android.content.Context
import com.decagon.week7task.model.ModelContact

class FirebaseListClass {
    companion object {
        //Generate data and add to list
        fun createDataSet(context: Context): ArrayList<ModelContact> {
            val list = ArrayList<ModelContact>()

            var contactList = arrayListOf<ModelContact>(
                ModelContact("","Oliver Juice", "08034567893", "dika@gmail.com"),
                ModelContact("","Oliver Juice", "08034567893", "dika@gmail.com"),
                ModelContact("","Oliver Juice", "08034567893", "dika@gmail.com")
            )

            list.addAll(contactList)

            return list
        }
    }
}