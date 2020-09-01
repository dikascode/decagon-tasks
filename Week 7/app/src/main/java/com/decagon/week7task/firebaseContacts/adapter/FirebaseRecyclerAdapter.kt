package com.decagon.week7task.firebaseContacts.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.decagon.week7task.R
import com.decagon.week7task.ViewContact
import com.decagon.week7task.firebaseContacts.Firebase
import com.decagon.week7task.model.ModelContact
import kotlinx.android.synthetic.main.layout_fb_contact.view.*

class FirebaseRecyclerAdapter(val context: Firebase, private val contactsList: List<ModelContact>) : RecyclerView.Adapter<FirebaseRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_fb_contact, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = contactsList[position]
        holder.fullName.text = item.fullName
        holder.phone.text = item.phoneNumber

        holder.bind(item, position)

    }


    //View Holder Class for contacts
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Initialize and Refer to IDs inside layout of RecyclerView Items
        val fullName: TextView = itemView.tv_fb_firstname
        val phone: TextView = itemView.tv_fb_phone

        fun bind(modelContactCard: ModelContact, position: Int) {
            //Listening post for a card clicked
            itemView.setOnClickListener {
                var intent = Intent(it.context, ViewContact::class.java)
                intent.putExtra("FIRST_NAME", modelContactCard.fullName)
                intent.putExtra("PHONE", modelContactCard.phoneNumber)
                intent.putExtra("EMAIL", modelContactCard.email)
                it.context.startActivity(intent)

            }
        }


    }




}
