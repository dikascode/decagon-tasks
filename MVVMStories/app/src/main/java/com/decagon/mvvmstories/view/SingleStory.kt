package com.decagon.mvvmstories.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.decagon.mvvmstories.R
import com.decagon.mvvmstories.viewModel.SearchActivityViewModel
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.card_layout_story.*

class SingleStory : AppCompatActivity() {
    private lateinit var viewModel: SearchActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_story)

        viewModel = ViewModelProvider(this).get(SearchActivityViewModel::class.java)

        val id = intent.getIntExtra("STORY_ID", 1)
        viewModel.searchStory(id)

        viewModel.singleStory.observe(this, Observer {

            tv_story_title.text = it.title
            tv_story_body.text = it.body
        })
    }
}