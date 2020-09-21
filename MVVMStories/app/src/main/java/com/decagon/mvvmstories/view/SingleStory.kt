package com.decagon.mvvmstories.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.decagon.mvvmstories.R
import com.decagon.mvvmstories.adapter.CommentsAdapter
import com.decagon.mvvmstories.model.Comment
import com.decagon.mvvmstories.model.Comments
import com.decagon.mvvmstories.model.Story
import com.decagon.mvvmstories.viewModel.FetchViewModel
import com.decagon.mvvmstories.viewModel.PostViewModel
import kotlinx.android.synthetic.main.activity_single_story.*
import kotlinx.android.synthetic.main.card_layout_story.*

class SingleStory : AppCompatActivity() {
    private lateinit var fetchViewModel: FetchViewModel
    private lateinit var postViewModel: PostViewModel

    private lateinit var adapter: CommentsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_story)

        fetchViewModel = ViewModelProvider(this).get(FetchViewModel::class.java)

        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)

        val id = intent.getIntExtra("STORY_ID", 1)
        fetchViewModel.getStory(id)

        fetchViewModel.getComments(id)

        fetchViewModel.singleStory.observe(this, Observer {

            tv_story_title.text = it.title
            tv_story_body.text = it.body
        })



//        viewModel.showProgress.observe(this, Observer {
//            if(it)
//                search_progress.visibility = View.VISIBLE
//            else
//                search_progress.visibility = View.GONE
//        })

        fetchViewModel.commentsList.observe(this, Observer {
            adapter.setCommentsList(it)
        })

        adapter = CommentsAdapter( this)
        comments_rv.adapter = adapter




        /**
         * Assign view values and update Post model View
         */

        add_comment_btn.setOnClickListener {
            val username = username_et.editText?.text.toString()
            val email = email_et.editText?.text.toString()
            val comment = comment_et.editText?.text.toString()

            var newStory = Story(1, username, comment)
            postViewModel.addStory(newStory)

            Toast.makeText(this, "$id, $username, $email, $comment", Toast.LENGTH_SHORT).show()

        }
    }



}