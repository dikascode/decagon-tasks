package com.decagon.mvvmstories.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.decagon.mvvmstories.R
import com.decagon.mvvmstories.adapter.StoryAdapter
import com.decagon.mvvmstories.viewModel.FetchViewModel
import com.decagon.mvvmstories.viewModel.RoomStoryViewModel
import kotlinx.android.synthetic.main.activity_search.*

class StoriesActivity : AppCompatActivity() {
    private lateinit var viewModel: FetchViewModel
    private lateinit var roomViewModel: RoomStoryViewModel
    private lateinit var adapter: StoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        viewModel = ViewModelProvider(this).get(FetchViewModel::class.java)
        roomViewModel = ViewModelProvider(this).get(RoomStoryViewModel::class.java)

        viewModel.displayStories()

        roomViewModel.displayStories()

        iv_search.setOnClickListener {
            viewModel.changeState()
//            if(et_search.text!!.isNotEmpty()) {
//                viewModel.getStory(Integer.parseInt(et_search.text.toString()))
//            }

        }


        /**
         * Add new Story FAB button
         */

        add_fab_button.setOnClickListener {
            val intent = Intent(this, AddStory::class.java)
            startActivity(intent)

        }


        /**
         * Stories list observer
         */

        roomViewModel.readAllStories.observe(this, Observer {
            adapter.setRoomStoryList(it)

        })

        adapter = StoryAdapter(this)
        rv_stories.adapter = adapter

    }

}