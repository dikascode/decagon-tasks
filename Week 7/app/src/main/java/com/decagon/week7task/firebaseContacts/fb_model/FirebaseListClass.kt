package com.decagon.week7task.firebaseContacts.fb_model

import android.content.Context
import com.decagon.week7task.model.ModelContact

class FirebaseListClass {
    companion object {
        //Generate data and add to list
        fun createDataSet(context: Context): ArrayList<ModelContact> {
            val list = ArrayList<ModelContact>()

            var contactList = arrayListOf<ModelContact>(
                ModelContact("Oliver Juice", "08034567893"),
                ModelContact("Emmanuel Olivia", "09087654321"),
                ModelContact("Grace Temi", "0805098743")
            )

            list.addAll(contactList)

            return list
        }
    }
}