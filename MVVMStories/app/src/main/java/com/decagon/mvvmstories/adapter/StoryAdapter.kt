package com.decagon.mvvmstories.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.decagon.mvvmstories.R
import com.decagon.mvvmstories.data.Stories
import com.decagon.mvvmstories.view.SingleStory
import kotlinx.android.synthetic.main.card_layout_story.view.*

class StoryAdapter(private val context: Context) :
    RecyclerView.Adapter<StoryAdapter.ViewHolder>(), Filterable {
    lateinit var intent: Intent

    private var mList = ArrayList<Stories>()
    private var fullList = ArrayList<Stories>()


    fun setRoomStoryList(list: ArrayList<Stories>) {
        this.mList = list
        this.fullList = list
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
        return (mList.size)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.title.text = mList[position].title
//        holder.body.text = mList[position].body

        holder.title.text = mList[position].title
        holder.body.text = mList[position].body
        var id = mList[position].id

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

    /**
     * Filter implementation
     */
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val filteredList = ArrayList<Stories>()
                if (p0.toString().isEmpty()) {
                    filteredList.addAll(fullList)
                } else {
                    for (story in fullList) {
                        if (story.body.toLowerCase()
                                .contains(p0.toString().toLowerCase()) || story.title.toLowerCase()
                                .contains(p0.toString().toLowerCase())
                        ) {
                            filteredList.add(story)
                        }
                    }
                }

                val filteredResults = FilterResults()
                filteredResults.values = filteredList
                return filteredResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                mList.clear()
                if (p1 != null) {
                    mList.addAll(p1.values as Collection<Stories>)
                }

                notifyDataSetChanged()

            }

        }
    }


}