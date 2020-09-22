package com.decagon.mvvmstories.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.decagon.mvvmstories.R
import com.decagon.mvvmstories.adapter.StoryAdapter
import com.decagon.mvvmstories.viewModel.FetchViewModel
import kotlinx.android.synthetic.main.activity_search.*

class StoriesActivity : AppCompatActivity() {
    private lateinit var viewModel: FetchViewModel
    private lateinit var adapter: StoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        viewModel = ViewModelProvider(this).get(FetchViewModel::class.java)

        viewModel.displayStories()

        iv_search.setOnClickListener {
            viewModel.changeState()
//            if(et_search.text!!.isNotEmpty()) {
//                viewModel.getStory(Integer.parseInt(et_search.text.toString()))
//            }

        }

        viewModel.showProgress.observe(this, Observer {
            if(it)
                search_progress.visibility = View.VISIBLE
            else
                search_progress.visibility = View.GONE
        })

        /**
         * Observer
         */
        viewModel.storyList.observe(this, Observer {
            adapter.setStoryList(it)

        })

        adapter = StoryAdapter( this)
        rv_stories.adapter = adapter
    }

}