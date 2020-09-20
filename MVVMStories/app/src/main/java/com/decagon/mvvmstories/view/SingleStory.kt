package com.decagon.mvvmstories.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.decagon.mvvmstories.R
import com.decagon.mvvmstories.adapter.CommentsAdapter
import com.decagon.mvvmstories.adapter.StoryAdapter
import com.decagon.mvvmstories.viewModel.FetchViewModel
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_single_story.*
import kotlinx.android.synthetic.main.card_layout_story.*

class SingleStory : AppCompatActivity() {
    private lateinit var viewModel: FetchViewModel
    private lateinit var adapter: CommentsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_story)

        viewModel = ViewModelProvider(this).get(FetchViewModel::class.java)

        val id = intent.getIntExtra("STORY_ID", 1)
        viewModel.getStory(id)

        viewModel.getComments(id)

        viewModel.singleStory.observe(this, Observer {

            tv_story_title.text = it.title
            tv_story_body.text = it.body
        })


//        viewModel.showProgress.observe(this, Observer {
//            if(it)
//                search_progress.visibility = View.VISIBLE
//            else
//                search_progress.visibility = View.GONE
//        })

        viewModel.commentsList.observe(this, Observer {
            adapter.setCommentsList(it)
        })

        adapter = CommentsAdapter( this)

        comments_rv.adapter = adapter
    }
}