package com.decagon.week7task

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME
import com.decagon.week7task.model.ModelContact
import com.decagon.week7task.model.PhoneModelContact

class DataSet {

    companion object {
        //Declare cursor
        lateinit var cursor: Cursor

        //Generate data and add to list
        fun createDataSet(context: Context): ArrayList<PhoneModelContact> {
            val list = ArrayList<PhoneModelContact>()

            var contactList = arrayListOf<PhoneModelContact>()

            // Get the Cursor of all the contacts
            cursor = context?.contentResolver
                .query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    null,
                    null,
                    ContactsContract.Contacts.DISPLAY_NAME
                )!!

            //Move cursor to first
            cursor.moveToFirst()

            // Iterate through the cursor
            while (cursor.moveToNext()) {
                // Get the contacts name and number
                contactList.add(
                    PhoneModelContact(
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