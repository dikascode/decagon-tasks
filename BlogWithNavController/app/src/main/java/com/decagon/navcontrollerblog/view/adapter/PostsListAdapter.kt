package com.decagon.navcontrollerblog.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.decagon.navcontrollerblog.R
import com.decagon.navcontrollerblog.data.Stories
import com.decagon.navcontrollerblog.recycler_interface.RecyclerViewClickInterface
import kotlinx.android.synthetic.main.card_layout_story.view.*

class PostsListAdapter(private val context: Context) :RecyclerView.Adapter<PostsListAdapter.ViewHolder>()
    {
        lateinit var intent: Intent
        lateinit var recyclerViewClickInterface: RecyclerViewClickInterface
        private var mList = ArrayList<Stories>()

        //RecyclerViewClickInterface

        fun setRoomStoryList(list: ArrayList<Stories>, recyclerViewClickInterface: RecyclerViewClickInterface) {
            this.mList = list
            this.recyclerViewClickInterface = recyclerViewClickInterface
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.card_layout_story, parent, false
                )
            )
        }

        /**
         * return count of total posts in list
         */
        override fun getItemCount(): Int {
            return (mList.size)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.title.text = mList[position].title
            holder.body.text = mList[position].body
            var id = mList[position].id

            holder.card_view.setOnClickListener(object: View.OnClickListener {
                override fun onClick(p0: View?) {
                    recyclerViewClickInterface.onItemClicked(position)
//                    Toast.makeText( context, holder.title.text, Toast.LENGTH_SHORT).show()
                }

            })
        }


        class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val title: TextView = v.tv_story_title
            val body: TextView = v.tv_story_body
            val card_view: CardView = itemView.findViewById(R.id.rv_card_layout)

        }


    }