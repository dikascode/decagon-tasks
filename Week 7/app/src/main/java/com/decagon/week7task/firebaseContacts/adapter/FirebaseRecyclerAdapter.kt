package com.decagon.week7task.firebaseContacts.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.decagon.week7task.R
import com.decagon.week7task.ViewContacts
import com.decagon.week7task.model.ModelContact
import kotlinx.android.synthetic.main.layout_fb_contact.view.*

class FirebaseRecyclerAdapter(var contactListener: OnContactItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /*
     Items as Model Contact type
 */
    private var items: List<ModelContact> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContactHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_fb_contact, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ContactHolder -> {
                holder.bind(items[position], contactListener)
            }
        }
    }

    //Submit list to RecyclerView Adapter
    fun submitList(contactsList: List<ModelContact>) {
        items = contactsList
    }

    //View Holder Class for contacts
    class ContactHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Initialize and Refer to IDs inside layout of RecyclerView Items
        private val firstName: TextView = itemView.tv_fb_firstname
        private val phone: TextView = itemView.tv_fb_phone

        /*
            Bind method: responsible for taking individual contact object
            and binding it to the views in layout
         */

        fun bind(modelContactCard: ModelContact, action: OnContactItemClickListener) {
            firstName.text = modelContactCard.fullName
            phone.text = modelContactCard.phoneNumber


            //Listening post for a card clicked
            itemView.setOnClickListener {
                var intent = Intent(it.context, ViewContacts::class.java)
                intent.putExtra("FIRST_NAME", modelContactCard.fullName)
                intent.putExtra("PHONE", modelContactCard.phoneNumber)
                it.context.startActivity(intent)

                action.onItemClicked(modelContactCard, adapterPosition)
            }
        }


    }

    //Interface for clickable item
    interface OnContactItemClickListener {
        fun onItemClicked(item: ModelContact, position: Int)
    }

}