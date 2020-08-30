package com.decagon.week7task

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME
import com.decagon.week7task.model.ModelContact

class DataSet {

    companion object {
        lateinit var cursor: Cursor

        //Generate data and add to list
        fun createDataSet(context: Context): ArrayList<ModelContact> {
            val list = ArrayList<ModelContact>()

            var contactList = arrayListOf<ModelContact>(
                ModelContact(
                    "Idefun",
                    "9908"
                ),

                ModelContact(
                    "Deepak",
                    "88745"
                ),

                ModelContact(
                    "Sylvia",
                    "756383"

                ),

                ModelContact(
                    "Stone Ebuka Andy",
                    "99843"
                ),
                ModelContact(
                    "Deepak",
                    "88745"
                ),

                ModelContact(
                    "Sylvia",
                    "756383"

                ),

                ModelContact(
                    "Stone Ebuka Andy",
                    "99843"
                ),

                ModelContact(
                    "Deepak",
                    "88745"
                ),

                ModelContact(
                    "Sylvia",
                    "756383"

                ),

                ModelContact(
                    "Stone Ebuka Andy",
                    "99843"
                )

            )

            cursor = context?.contentResolver
                .query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    null,
                    null,
                    ContactsContract.Contacts.DISPLAY_NAME
                )!!

            cursor.moveToFirst()

            while(cursor.moveToNext()) {
                contactList. add(
                    ModelContact(
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    )
                )
            }
            list.addAll(contactList)

            return list
        }
    }
}