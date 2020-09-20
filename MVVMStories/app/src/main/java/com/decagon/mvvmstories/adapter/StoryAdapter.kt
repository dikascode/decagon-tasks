package com.decagon.mvvmstories.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.decagon.mvvmstories.R
import com.decagon.mvvmstories.model.SingleStoryModel
import com.decagon.mvvmstories.model.Story
import com.decagon.mvvmstories.view.SingleStory
import kotlinx.android.synthetic.main.card_layout_story.view.*

class StoryAdapter(private val context: Context) :
    RecyclerView.Adapter<StoryAdapter.ViewHolder>() {
    lateinit var intent: Intent

    private var list: List<Story> = ArrayList()

    fun setStoryList(list: List<Story>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.card_layout_story, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = list[position].title
        holder.body.text = list[position].body.substring(0, 50)
        val id = list[position].id

        holder.card_view.setOnClickListener {
            intent = Intent(context, SingleStory::class.java)
            intent.putExtra("STORY_ID", id)
            context.startActivity(intent)
        }
    }


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView = v.tv_story_title
        val body: TextView = v.tv_story_body
        val card_view: CardView = itemView.findViewById(R.id.rv_card_layout)

    }

}