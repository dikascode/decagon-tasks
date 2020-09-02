package com.decagon.week7task.firebaseContacts.fb_model

import android.content.Context
import com.decagon.week7task.model.ModelContact
import com.google.firebase.database.*

class FirebaseListClass {

    companion object {
        lateinit var firebaseContacts: DatabaseReference

        //Generate data and add to list
        fun createDataSet(context: Context): ArrayList<ModelContact> {
            firebaseContacts = FirebaseDatabase.getInstance().getReference("contacts")

            val list = ArrayList<ModelContact>()
            var contactList = arrayListOf<ModelContact>()

            firebaseContacts.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(contactSnapShot: DataSnapshot) {
                    if (contactSnapShot.exists()) {
                        for (item in contactSnapShot.children) {
                            val contact = item.getValue(ModelContact::class.java)
                            if (contact != null) {
                                contactList.add(contact)
                            }

                        }
                    }
                }

            })



            list.addAll(contactList)

            return list
        }
    }
}