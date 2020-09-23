package com.decagon.mvcstories.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.decagon.mvcstories.R
import com.decagon.mvcstories.data.Comments
import kotlinx.android.synthetic.main.card_layout_comment.view.*

class CommentsAdapter(private val context: Context) :
    RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    private var list: List<Comments> = ArrayList()

    fun setCommentsList(list: List<Comments>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.card_layout_comment, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.username.text = list[position].name
        holder.body.text = list[position].body
        val id = list[position].id
    }


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val username: TextView = v.tv_comment_name
        val body: TextView = v.tv_comment_body

    }

}