package com.decagon.week7task

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.decagon.week7task.model.ModelContact
import kotlinx.android.synthetic.main.layout_contact.view.*

class RecyclerAdapter(var contactListener: OnContactItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /*
        Declare dataset
        to be displayed in the list
    */
    private var items: List<ModelContact> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StudentHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_contact, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is StudentHolder -> {
                holder.bind(items[position], contactListener)
            }
        }
    }

    //Submit list to RecyclerView Adapter
    fun submitList(contactsList: List<ModelContact>) {
        items = contactsList
    }

    //View Holder Class for Students
    class StudentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Initialize and Refer to IDs inside layout of RecyclerView Items
        private val firstName: TextView = itemView.tv_firstname
        private val phone: TextView = itemView.tv_phone

        /*
            Bind method: responsible for taking individual student object
            and binding it to the views in layout
         */

        fun bind(modelContactCard: ModelContact, action: OnContactItemClickListener) {
            firstName.text = modelContactCard.firstname
            phone.text = modelContactCard.number


            //Listening post for a card clicked
            itemView.setOnClickListener {
                var intent = Intent(it.context, ViewContacts::class.java)
                intent.putExtra("FIRST_NAME", modelContactCard.firstname)
                intent.putExtra("PHONE", modelContactCard.number)
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